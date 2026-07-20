package uk.dylanrit.scrollhistory.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Thin wrapper around the public MediaWiki Action API (en.wikipedia.org).
 * No API key required; see https://www.mediawiki.org/wiki/API:Main_page
 */
interface WikipediaApi {

    /**
     * Pulls a batch of member pages from a single category, jumping into the
     * alphabetically-sorted member list at [startSortKeyPrefix] to get a
     * pseudo-random slice, and enriches each page with a short extract + thumbnail
     * in the same round trip.
     */
    @GET("w/api.php")
    suspend fun getCategoryMembers(
        @Query("action") action: String = "query",
        @Query("generator") generator: String = "categorymembers",
        @Query("gcmtitle") categoryTitle: String,
        @Query("gcmtype") type: String = "page",
        @Query("gcmnamespace") namespace: Int = 0,
        @Query("gcmlimit") limit: Int = 25,
        @Query("gcmstartsortkeyprefix") startSortKeyPrefix: String? = null,
        @Query("gcmcontinue") continueToken: String? = null,
        @Query("prop") props: String = "extracts|pageimages|pageprops",
        @Query("exintro") introOnly: Int = 1,
        @Query("explaintext") plainText: Int = 1,
        @Query("exsentences") sentences: Int = 3,
        @Query("piprop") imageProps: String = "thumbnail",
        @Query("pithumbsize") thumbSize: Int = 640,
        @Query("format") format: String = "json",
        @Query("formatversion") formatVersion: Int = 2
    ): WikiQueryResponse

    /** Full detail for a single article, used by the detail screen. */
    @GET("w/api.php")
    suspend fun getPageDetail(
        @Query("titles") title: String,
        @Query("action") action: String = "query",
        @Query("prop") props: String = "extracts|pageimages|info",
        @Query("explaintext") plainText: Int = 1,
        @Query("piprop") imageProps: String = "original",
        @Query("inprop") infoProps: String = "url",
        @Query("format") format: String = "json",
        @Query("formatversion") formatVersion: Int = 2
    ): WikiQueryResponse
}
