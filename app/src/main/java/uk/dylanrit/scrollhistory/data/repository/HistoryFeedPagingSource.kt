package uk.dylanrit.scrollhistory.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uk.dylanrit.scrollhistory.data.EuropeanHistoryCategories
import uk.dylanrit.scrollhistory.data.HistoryItem
import uk.dylanrit.scrollhistory.data.remote.WikipediaApi
import java.util.Collections

/**
 * Endless, randomized feed: each page load jumps into a random category at a
 * random alphabetical offset instead of walking a fixed list, so the scroll
 * never "runs out" and rarely repeats within a session.
 */
class HistoryFeedPagingSource(
    private val api: WikipediaApi
) : PagingSource<Int, HistoryItem>() {

    private val seenTitles = Collections.synchronizedSet(mutableSetOf<String>())

    override fun getRefreshKey(state: PagingState<Int, HistoryItem>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HistoryItem> {
        val pageIndex = params.key ?: 0
        val target = params.loadSize.coerceIn(10, 25)
        val collected = mutableListOf<HistoryItem>()
        var lastError: Throwable? = null
        var attempts = 0

        while (collected.size < target && attempts < 8) {
            attempts++
            try {
                val response = api.getCategoryMembers(
                    categoryTitle = EuropeanHistoryCategories.random(),
                    startSortKeyPrefix = EuropeanHistoryCategories.randomSortKeyPrefix(),
                    limit = 20
                )
                val pages = response.query?.pages.orEmpty()
                for (page in pages) {
                    if (page.missing) continue
                    if (page.namespace != 0) continue
                    if (page.pageProps?.containsKey("disambiguation") == true) continue
                    val extract = page.extract?.trim().orEmpty()
                    if (extract.length < 40) continue
                    if (!seenTitles.add(page.title)) continue

                    collected += HistoryItem(
                        pageId = page.pageId,
                        title = page.title,
                        summary = extract,
                        thumbnailUrl = page.thumbnail?.source
                    )
                }
            } catch (t: Throwable) {
                lastError = t
            }
        }

        if (collected.isEmpty() && lastError != null) {
            return LoadResult.Error(lastError)
        }

        return LoadResult.Page(
            data = collected,
            prevKey = null,
            nextKey = pageIndex + 1
        )
    }
}
