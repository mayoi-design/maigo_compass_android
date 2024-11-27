package jp.ac.mayoi.wear.features.traveling

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import jp.ac.mayoi.wear.core.resource.MaigoButton
import jp.ac.mayoi.wear.core.resource.spacingSingle
import jp.ac.mayoi.wear.core.resource.spacingTriple
import jp.ac.mayoi.wear.core.resource.textStyleBody


@Composable
fun TravelingRoot(
    viewModel: TravelingViewModel
) {
    val context = LocalContext.current
    var permitted by remember { mutableStateOf(checkTravelingPermission(context)) }
    LifecycleResumeEffect(permitted) {
        if (permitted) {
            viewModel.startSensor(context)
        }

        onPauseOrDispose {
            if (permitted) {
                viewModel.stopSensor(context)
            }
        }
    }

    if (permitted) {
        // todo: あとでrykaの作ってる画面が入る
        TravelingScreen(
            isHeadingDestination = true,
            travelingViewModel = viewModel
        )
    } else {
        PermissionLackingScreen(
            onPermitted = { permitted = true }
        )
    }
}

@Composable
private fun PermissionLackingScreen(
    onPermitted: () -> Unit,
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(ACCESS_FINE_LOCATION, false)
                    && permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) -> {
                onPermitted()
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "利用には位置情報への\nアクセスが必要です",
            textAlign = TextAlign.Center,
            style = textStyleBody,
        )

        Spacer(modifier = Modifier.size(spacingSingle))

        MaigoButton(
            onClick = {
                permissionLauncher.launch(
                    listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION).toTypedArray()
                )
            },
            modifier = Modifier
                .padding(horizontal = spacingTriple)
                .fillMaxWidth()
        ) {
            Text(
                text = "アクセスを許可",
                fontSize = 14.sp
            )
        }
    }
}

private fun checkTravelingPermission(context: Context): Boolean {
    val isGrantedFineLocation =
        context.checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    val isGrantedCoarseLocation =
        context.checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

    return isGrantedCoarseLocation && isGrantedFineLocation
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun PermissionLackingScreenPreview() {
    MaterialTheme {
        PermissionLackingScreen(
            onPermitted = {}
        )
    }
}