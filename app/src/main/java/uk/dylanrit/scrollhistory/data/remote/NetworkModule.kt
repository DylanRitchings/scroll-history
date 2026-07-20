package uk.dylanrit.scrollhistory.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    // Wikimedia rejects requests with a generic/default client User-Agent
    // (e.g. OkHttp's own) with 403s; a descriptive UA is required by their
    // API etiquette policy: https://meta.wikimedia.org/wiki/User-Agent_policy
    private const val USER_AGENT = "ScrollHistoryApp/1.0 (Android; contact: accounts@dylanrit.uk)"

    val wikipediaApi: WikipediaApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val userAgentInterceptor = okhttp3.Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", USER_AGENT)
                .build()
            chain.proceed(request)
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(userAgentInterceptor)
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikipediaApi::class.java)
    }
}
