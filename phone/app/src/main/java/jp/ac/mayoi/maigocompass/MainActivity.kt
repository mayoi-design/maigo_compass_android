package jp.ac.mayoi.maigocompass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.wearable.Wearable
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.traveling.ConnectionListener

class MainActivity : ComponentActivity() {


    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val connectionListener = ConnectionListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaigoCompassTheme {
                RootScreen(navController = rememberNavController(),connectionListener)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        messageClient.addListener(connectionListener)
    }

    override fun onPause() {
        super.onPause()
        messageClient.removeListener(connectionListener)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    MaigoCompassTheme {
        Greeting("Android")
    }
}