package uk.dylanrit.scrollhistory.data

/**
 * Curated Wikipedia categories used to seed the random feed. Kept broad
 * (era + region + theme) so repeated random draws stay varied rather than
 * converging on the same few hundred articles.
 */
object EuropeanHistoryCategories {
    val ALL = listOf(
        "Category:History of Europe",
        "Category:Ancient Rome",
        "Category:Ancient Greece",
        "Category:Byzantine Empire",
        "Category:Medieval history of Europe",
        "Category:Middle Ages",
        "Category:Renaissance",
        "Category:Napoleonic Wars",
        "Category:World War I",
        "Category:World War II",
        "Category:Cold War",
        "Category:History of France",
        "Category:History of the United Kingdom",
        "Category:History of Germany",
        "Category:History of Italy",
        "Category:History of Spain",
        "Category:History of Russia",
        "Category:History of Poland",
        "Category:History of Portugal",
        "Category:History of Greece",
        "Category:History of the Netherlands",
        "Category:History of Austria",
        "Category:History of Sweden",
        "Category:History of Switzerland",
        "Category:Kingdoms of Europe",
        "Category:European monarchs",
        "Category:European royalty",
        "Category:Wars involving European countries",
        "Category:Treaties of the Holy Roman Empire",
        "Category:Battles involving Europe",
        "Category:Viking Age",
        "Category:Crusades",
        "Category:European integration",
        "Category:Reformation",
        "Category:Age of Enlightenment",
        "Category:European colonisation"
    )

    fun random(): String = ALL.random()

    /** A random single-letter jump point so category listings don't always start at "A". */
    fun randomSortKeyPrefix(): String = ('A'..'Z').random().toString()
}
