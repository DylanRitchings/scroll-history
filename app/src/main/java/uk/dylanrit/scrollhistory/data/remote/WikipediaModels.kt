package uk.dylanrit.scrollhistory.data.remote

import com.google.gson.annotations.SerializedName

/** Response shape for action=query&generator=categorymembers requests. */
data class WikiQueryResponse(
    @SerializedName("continue") val continueToken: WikiContinue?,
    @SerializedName("query") val query: WikiQuery?
)

data class WikiContinue(
    @SerializedName("gcmcontinue") val gcmContinue: String?,
    @SerializedName("continue") val continueValue: String?
)

data class WikiQuery(
    @SerializedName("pages") val pages: List<WikiPage>?
)

data class WikiPage(
    @SerializedName("pageid") val pageId: Int,
    @SerializedName("ns") val namespace: Int,
    @SerializedName("title") val title: String,
    @SerializedName("missing") val missing: Boolean = false,
    @SerializedName("extract") val extract: String?,
    @SerializedName("thumbnail") val thumbnail: WikiThumbnail?,
    @SerializedName("original") val original: WikiThumbnail?,
    @SerializedName("pageprops") val pageProps: Map<String, String>?,
    @SerializedName("fullurl") val fullUrl: String?
)

data class WikiThumbnail(
    @SerializedName("source") val source: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)
