package jp.ac.mayoi.phone.model

data class PieChartModel(
    val entries: List<PieChartEntry>,
    val n: Int,
) {
    constructor(entries: List<PieChartEntry> = listOf()) : this(
        entries = entries,
        n = entries.sumOf { it.count },
    )

    fun getRatioForEach(): List<Float> = entries.map {
        it.count.toFloat() / this.n
    }
}
