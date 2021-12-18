import java.util.*

data class SplitResult(val denomination: Int, val amount: Int, val rest: MutableList<SplitResult>) {
	override fun toString(): String {
		return "result:\n\tdenom: ${denomination}p\n\tamount: x$amount\n\trest: $rest\n"
	}
}

val denominations = intArrayOf(
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

	println(splitResult)
	println()

	println(splitResultToString(splitResult))
}

fun transformShortHand(inputString: String): String {
	val input = inputString.substring(0, inputString.length - 1).replace("\\{".toRegex(), "").split("}")
	var output = ""
	var i = 0
	while (i < input.size) {
		output += "${input[i]}p x ${input[++i]}${if (++i + 1 < input.size) " + " else ""}"
	}
	return output
}

fun splitResultToString(splitResults: MutableList<SplitResult>): String {
	val stringList = mutableListOf<String>()
	for (splitResult in splitResults) {
		getStrings(splitResult).forEach {
			for (string in stringList) {
				if (isAnagram(it, string)) return@forEach
			}
			stringList.add(it)
		}
	}
	var output = ""
	for (string in stringList) {
		output += "${transformShortHand(string)}\n"
	}
	return output
}

fun isAnagram(a: String, b: String): Boolean {
	return Arrays.equals(a.chars().sorted().toArray(), b.chars().sorted().toArray())
}

fun splitAmount(total: Int): MutableList<SplitResult> {
	val splits = mutableListOf<SplitResult>()
	for (denomination in denominations) {
		for (amount in 1..total / denomination) {
			splits.add(SplitResult(denomination, amount, splitAmount(total - denomination * amount)))
		}
	}
	return splits
}

fun getStrings(splitResult: SplitResult): MutableList<String> {
//	val output: MutableList<String> = MutableList(numberOfPaths(splitResult)) { "${splitResult.denomination}p x ${splitResult.amount}" }
	val output: MutableList<String> = MutableList(numberOfPaths(splitResult)) { "{${splitResult.denomination}}{${splitResult.amount}}" }
	val otherResults: MutableList<MutableList<String>> = mutableListOf()
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

fun numberOfPaths(splitResult: MutableList<SplitResult>): Int {
	return numberOfPaths(SplitResult(0, 0, splitResult))
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
