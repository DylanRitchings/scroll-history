package uk.dylanrit.scrollhistory.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.dylanrit.scrollhistory.ui.detail.DetailScreen
import uk.dylanrit.scrollhistory.ui.feed.FeedScreen
import java.net.URLDecoder
import java.net.URLEncoder

private object Routes {
    const val FEED = "feed"
    const val DETAIL = "detail/{title}?summary={summary}&image={image}"

    fun detail(title: String, summary: String, imageUrl: String?): String {
        val t = URLEncoder.encode(title, "UTF-8")
        val s = URLEncoder.encode(summary, "UTF-8")
        val i = URLEncoder.encode(imageUrl.orEmpty(), "UTF-8")
        return "detail/$t?summary=$s&image=$i"
    }
}

@Composable
fun ScrollHistoryApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.FEED) {
        composable(Routes.FEED) {
            FeedScreen(
                onItemClick = { item ->
                    navController.navigate(Routes.detail(item.title, item.summary, item.thumbnailUrl))
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("summary") { type = NavType.StringType; defaultValue = "" },
                navArgument("image") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val title = URLDecoder.decode(backStackEntry.arguments?.getString("title").orEmpty(), "UTF-8")
            val summary = URLDecoder.decode(backStackEntry.arguments?.getString("summary").orEmpty(), "UTF-8")
            val image = URLDecoder.decode(backStackEntry.arguments?.getString("image").orEmpty(), "UTF-8")

            DetailScreen(
                title = title,
                fallbackSummary = summary,
                fallbackImageUrl = image.ifBlank { null },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
