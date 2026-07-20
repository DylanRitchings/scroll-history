package uk.dylanrit.scrollhistory.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import uk.dylanrit.scrollhistory.data.HistoryItem
import uk.dylanrit.scrollhistory.data.repository.HistoryRepository

class HistoryFeedViewModel(
    private val repository: HistoryRepository = HistoryRepository()
) : ViewModel() {

    val feed: Flow<PagingData<HistoryItem>> = repository.feedPager().cachedIn(viewModelScope)
}
