data class SplitResult(val denomination: Int, val amount: Int, val rest: MutableList<SplitResult>) {
	override fun toString(): String {
		return "result:\n\tdenom: ${denomination}p\n\tamount: x$amount\n\trest: $rest\n"
	}
}

val DENOMINATIONS = intArrayOf(
	50_00,
	20_00,
	10_00,
	5_00,
	2_00,
	1_00,
	50,
	20,
	10,
	5,
	2,
	1
)

fun main() {
	val splitResult = splitAmount(4)
	println(splitResultToString(splitResult))
}

fun transformShortHand(list: MutableList<Pair<Int, Int>>): String {
	var output = ""
	for (pair in list) {
		output += "${pair.first}p x ${pair.second} and "
	}
	if (output.isEmpty()) {
		println(list)
	}
	return output.substring(0, output.length - 5)
}

fun splitResultToString(splitResults: MutableList<SplitResult>): String {
	val stringList = mutableListOf<MutableList<Pair<Int, Int>>>()
	for (splitResult in splitResults) {
		getStrings(splitResult).forEach {
			val reducedIt = reduceList(it)
			var isPresent = false
			for (string in stringList) {
				if (isAnagram(reducedIt, string)) {
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

fun reduceList(list: MutableList<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {
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

fun isAnagram(a: MutableList<Pair<Int, Int>>, b: MutableList<Pair<Int, Int>>): Boolean {
	if (a.size != b.size) return false
	val bCopy = b.toMutableList()
	for (pair in a) {
		bCopy.remove(pair)
	}
	return bCopy.size == 0
}

fun splitAmount(total: Int): MutableList<SplitResult> {
	val splits = mutableListOf<SplitResult>()
	for (denomination in DENOMINATIONS) {
		for (amount in 1..total / denomination) {
			splits.add(SplitResult(denomination, amount, splitAmount(total - denomination * amount)))
		}
	}
	return splits
}

fun getStrings(splitResult: SplitResult): MutableList<MutableList<Pair<Int, Int>>> {
//	val output: MutableList<String> = MutableList(numberOfPaths(splitResult)) { "${splitResult.denomination}p x ${splitResult.amount}" }
	val output: MutableList<MutableList<Pair<Int, Int>>> =
		MutableList(numberOfPaths(splitResult)) { mutableListOf(Pair(splitResult.denomination, splitResult.amount)) }
	val otherResults: MutableList<MutableList<MutableList<Pair<Int, Int>>>> = mutableListOf()
	for (result in splitResult.rest) {
		otherResults.add(getStrings(result))
	}
	var i = 0
	for (resultStrings in otherResults) {
		for (string in resultStrings) {
//			output[i++] += " + $string"
			output[i++] += string
		}
	}
	return output
}

fun numberOfPaths(splitResult: SplitResult): Int {
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
