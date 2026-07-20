package uk.dylanrit.scrollhistory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import uk.dylanrit.scrollhistory.ui.ScrollHistoryApp
import uk.dylanrit.scrollhistory.ui.theme.ScrollHistoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScrollHistoryTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ScrollHistoryApp()
                }
            }
        }
    }
}
