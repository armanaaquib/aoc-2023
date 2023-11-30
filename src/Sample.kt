fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }
    check(part1(readInputAsLines("sample_test")) == 2)

    fun part2(input: String): Int {
        return input.length
    }
    part2(readInputAsText("sample_test")).println()
}
