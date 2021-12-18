data class SplitResult(val denomination: Int, val amount: Int, val rest: MutableList<SplitResult>) {
	companion object {
		private val DENOMINATIONS = intArrayOf(
			5,
			2,
			1
		)

		// INTENSIVE
		fun splitAmount(total: Int): MutableList<SplitResult> {
			val splits = mutableListOf<SplitResult>()
			for (denomination in DENOMINATIONS) {
				for (amount in 1..total / denomination) {
					splits.add(SplitResult(denomination, amount, splitAmount(total - denomination * amount)))
				}
			}
			return splits
		}

		private fun transformShortHand(list: MutableList<Pair<Int, Int>>): String {
			var output = ""
			for (pair in list) {
				output += "${pair.first}p x ${pair.second} and "
			}
			if (output.isEmpty()) {
				println(list)
			}
			return output.substring(0, output.length - 5)
		}

		// SUPER-INTENSIVE
		fun splitResultsToString(splitResults: MutableList<SplitResult>): String {
			val stringList = mutableListOf<MutableList<Pair<Int, Int>>>()
			for (splitResult in splitResults) {
				getNumberPairs(splitResult).forEach {
					val reducedIt = reduceList(it)
					var isPresent = false
					for (string in stringList) {
						if (reducedIt.containsAll(string) && string.containsAll(reducedIt)) {
							isPresent = true
						}
					}
					if (!isPresent) stringList.add(reducedIt)
				}
			}
			var output = ""
			for (string in stringList) {
				if (string.isNotEmpty()) output += "${transformShortHand(string)}\n"
			}
			return output
		}

		private fun reduceList(list: MutableList<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {
			val reducedIt = mutableListOf<Pair<Int, Int>>()
			for (pair in list) {
				var isPresent = false
				reducedIt.forEach { x ->
					if (x.first == pair.first) {
						isPresent = true
						reducedIt[reducedIt.indexOf(x)] = Pair(x.first, x.second + pair.second)
					}
				}
				if (!isPresent) {
					reducedIt.add(pair)
				}
			}
			return reducedIt
		}

		// INTENSIVE
		private fun getNumberPairs(splitResult: SplitResult): MutableList<MutableList<Pair<Int, Int>>> {
			val output: MutableList<MutableList<Pair<Int, Int>>> =
				MutableList(numberOfPaths(splitResult)) { mutableListOf(Pair(splitResult.denomination, splitResult.amount)) }
			val otherResults: MutableList<MutableList<MutableList<Pair<Int, Int>>>> = mutableListOf()
			for (result in splitResult.rest) {
				otherResults.add(getNumberPairs(result))
			}
			var i = 0
			for (resultLists in otherResults) {
				for (list in resultLists) {
					output[i++] += list
				}
			}
			return output
		}

		private fun numberOfPaths(splitResult: SplitResult): Int {
			return if (splitResult.rest.size > 0) {
				var total = 0
				for (result in splitResult.rest) {
					total += numberOfPaths(result)
				}
				total
			} else {
				1
			}
		}
	}

	override fun toString(): String {
		return "result:\n\tdenom: ${denomination}p\n\tamount: x$amount\n\trest: $rest\n"
	}
}

fun main() {
	val splitResults = SplitResult.splitAmount(10)
	println(SplitResult.splitResultsToString(splitResults))
}
