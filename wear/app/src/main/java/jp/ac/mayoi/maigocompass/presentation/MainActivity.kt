/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package jp.ac.mayoi.maigocompass.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import jp.ac.mayoi.wear.core.application.location.LocationService
import jp.ac.mayoi.wear.core.application.location.LocationViewModel
import jp.ac.mayoi.wear.core.resource.StringR
import jp.ac.mayoi.wear.core.resource.locationIntentAction
import jp.ac.mayoi.wear.core.resource.locationIntentLatitude
import jp.ac.mayoi.wear.core.resource.locationIntentLongitude

class MainActivity : ComponentActivity() {

    private lateinit var mainActivityViewModel: LocationViewModel

    private val intentFilter: IntentFilter = IntentFilter()
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctx: Context, intent: Intent) {
            val bundle = intent.extras ?: return

            val currentLatitude = bundle.getDouble(locationIntentLatitude, -1.0)
            val currentLongitude = bundle.getDouble(locationIntentLongitude, -1.0)

            mainActivityViewModel.setCurrentLocation(currentLatitude, currentLongitude)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        mainActivityViewModel = LocationViewModel(getSystemService(SENSOR_SERVICE) as SensorManager)

        val locationServiceIntent = Intent(
            this,
            LocationService::class.java
        )
        val ctx: Context = this

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            LaunchedEffect(Unit) {
                if (ActivityCompat.checkSelfPermission(ctx, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ctx, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    startService(locationServiceIntent)
                    registerLocationBroadcastReceiver()
                }
            }

            var permitted by remember { mutableStateOf(
                ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(ACCESS_FINE_LOCATION, false)
                            && permissions.getOrDefault(ACCESS_COARSE_LOCATION, false)
                        -> {
                        permitted = true
                        startService(locationServiceIntent)
                        registerLocationBroadcastReceiver()
                    }
                }
            }

            if (!permitted) {
                PermissionRequestView(
                    permissionRequest = launcher,
                )
            }
            else {
                WearTest(mainActivityViewModel)
            }
        }
    }

    private fun registerLocationBroadcastReceiver() {
        intentFilter.addAction(locationIntentAction)

        @SuppressLint("UnspecifiedRegisterReceiverFlag")
        if (Build.VERSION.SDK_INT < 33) {
            registerReceiver(
                broadcastReceiver,
                intentFilter
            )
        }
        else {
            registerReceiver(
                broadcastReceiver,
                intentFilter,
                RECEIVER_EXPORTED
            )
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.registerSensorListener()
    }

    override fun onPause() {
        super.onPause()
        mainActivityViewModel.unregisterSensorListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, LocationService::class.java))
    }
}

@Composable
fun WearApp(greetingName: String) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            Greeting(greetingName = greetingName)
        }
    }
}

@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(StringR.hello_world, greetingName)
    )
}

@Composable
fun PermissionRequestView(
    permissionRequest: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Please allow the App to access your location",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            permissionRequest.launch(
                listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION).toTypedArray()
            )
        }) {
            Text("Tap to Allow")
        }
    }
}

@Composable
fun WearTest(viewModel: LocationViewModel){

    val azimuth = viewModel.azimuth
    val longitude = viewModel.currentLongitude
    val latitude = viewModel.currentLatitude
    val acc = viewModel.testValue

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text("azi: $azimuth")
        Text("Longitude: $longitude")
        Text("Latitude: $latitude")
        Text("acc: $acc")
        Spacer(modifier = Modifier.weight(1f))
    }
}





//@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
//@Composable
//fun DefaultPreview() {
//    WearApp("Preview Android")
//}