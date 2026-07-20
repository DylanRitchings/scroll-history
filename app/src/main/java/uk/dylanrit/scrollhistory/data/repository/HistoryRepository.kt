package uk.dylanrit.scrollhistory.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uk.dylanrit.scrollhistory.data.HistoryDetail
import uk.dylanrit.scrollhistory.data.remote.NetworkModule
import uk.dylanrit.scrollhistory.data.remote.WikipediaApi

class HistoryRepository(
    private val api: WikipediaApi = NetworkModule.wikipediaApi
) {

    fun feedPager(): Flow<PagingData<uk.dylanrit.scrollhistory.data.HistoryItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { HistoryFeedPagingSource(api) }
        ).flow

    suspend fun getDetail(title: String): HistoryDetail {
        val response = api.getPageDetail(title = title)
        val page = response.query?.pages.orEmpty().firstOrNull()
            ?: return HistoryDetail(
                title = title,
                fullText = "Couldn't load this article. Pull to try again.",
                imageUrl = null,
                sourceUrl = "https://en.wikipedia.org/wiki/${title.replace(' ', '_')}"
            )

        return HistoryDetail(
            title = page.title,
            fullText = page.extract?.trim().orEmpty().ifBlank { "No extract available for this article." },
            imageUrl = page.original?.source,
            sourceUrl = page.fullUrl ?: "https://en.wikipedia.org/wiki/${title.replace(' ', '_')}"
        )
    }
}
