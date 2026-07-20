package uk.dylanrit.scrollhistory

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import uk.dylanrit.scrollhistory.data.remote.NetworkModule

class ScrollHistoryApplication : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .okHttpClient(NetworkModule.okHttpClient)
            .build()
}
