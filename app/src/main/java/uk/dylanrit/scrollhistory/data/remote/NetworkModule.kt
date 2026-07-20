package uk.dylanrit.scrollhistory.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    // Wikimedia rejects requests with a generic/default client User-Agent
    // (e.g. OkHttp's own) with 403s on both the API and the image CDN; a
    // descriptive UA is required by their API etiquette policy:
    // https://meta.wikimedia.org/wiki/User-Agent_policy
    private const val USER_AGENT = "ScrollHistoryApp/1.0 (Android; contact: accounts@dylanrit.uk)"

    /** Shared client so Retrofit *and* Coil (image loading) both send the required UA. */
    val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val userAgentInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", USER_AGENT)
                .build()
            chain.proceed(request)
        }
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(userAgentInterceptor)
            .addInterceptor(logging)
            .build()
    }

    val wikipediaApi: WikipediaApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikipediaApi::class.java)
    }
}
