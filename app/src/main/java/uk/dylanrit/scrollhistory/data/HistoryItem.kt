package uk.dylanrit.scrollhistory.data

/** A single European history article surfaced in the feed. */
data class HistoryItem(
    val pageId: Int,
    val title: String,
    val summary: String,
    val thumbnailUrl: String?,
    val sourceUrl: String = "https://en.wikipedia.org/wiki/${title.replace(' ', '_')}"
)

/** Full detail loaded lazily when the user taps into an item. */
data class HistoryDetail(
    val title: String,
    val fullText: String,
    val imageUrl: String?,
    val sourceUrl: String
)
