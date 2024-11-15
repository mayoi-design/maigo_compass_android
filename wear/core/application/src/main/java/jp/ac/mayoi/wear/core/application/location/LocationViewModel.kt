package jp.ac.mayoi.wear.core.application.location

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

class LocationViewModel(
    private val sensorManager: SensorManager,

): ViewModel(), SensorEventListener {
    var currentLocation = doubleToLocation(0.0, 0.0)
        private set
    var currentLatitude by mutableDoubleStateOf(0.0)
        private set
    var currentLongitude by mutableDoubleStateOf(0.0)
        private set

    var testValue by mutableStateOf("")
        private set

    private var azimuthMemo: List<Double> = List(7) { 0.0 }
    var azimuthInDegrees by mutableDoubleStateOf(0.0)
        private set


    private var rotationVector: Sensor? = null
    private var rotation: FloatArray?  = null

    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null
    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null


    // メジアンフィルタは奇数で運用する。
    // nが偶数のとき、メジアンは中央の二要素の平均になるが、
    // 今回のユースケースでは平均の計算がバグにつながることがわかった
    var azimuth by mutableDoubleStateOf(0.0)
        private set

    init {

        rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.also {
            sensorManager.registerListener(
                this,
                rotationVector,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

//        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
//            sensorManager.registerListener(
//                this,
//                accelerometer,
//                SensorManager.SENSOR_DELAY_NORMAL,
//            )
//        }
//        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also {
//            // なんかポーリングを1秒くらいにするとローパスかけるより安定するみたいな話があった
//            sensorManager.registerListener(
//                this,
//                magnetometer,
//                SensorManager.SENSOR_DELAY_NORMAL
//            )
//        }

        Log.d("Location","sensorresult:${magnetometer.toString()},${accelerometer.toString()}")

    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ROTATION_VECTOR -> {
                rotation = event.values
            }
        }
               if (rotation != null) {
            // これデバイスによってめちゃくちゃ誤差出る。
            // 少なくともYoureinのP20 Liteは変な角度で持たないと正しい角度で測定してくれなかった

            val rotationMatrix = FloatArray(9)
            val eventVector = FloatArray(4)
            eventVector[0] = event.values[0]
            eventVector[1] = event.values[1]
            eventVector[2] = event.values[2]
            eventVector[3] = event.values[3]
            SensorManager.getRotationMatrixFromVector(rotationMatrix, eventVector)

            //Log.d("Location","${event.values[4]}")
            testValue = Math.toDegrees(event.values[0].toDouble()).toString()

            val orientationValues = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientationValues)

            azimuth = Math.toDegrees(orientationValues[0].toDouble())  // 方位角 (度)
        }

//        when (event.sensor.type) {
//            Sensor.TYPE_ACCELEROMETER -> {
//                gravity = event.values
//
//            }
//            Sensor.TYPE_MAGNETIC_FIELD -> {
//                geomagnetic = event.values
//            }
//        }
//
//        if (gravity != null && geomagnetic != null) {
//            // これデバイスによってめちゃくちゃ誤差出る。
//            // 少なくともYoureinのP20 Liteは変な角度で持たないと正しい角度で測定してくれなかった
//
//            val R = FloatArray(9)
//            val success = SensorManager.getRotationMatrix(R, null, gravity, geomagnetic)
//            if (success) {
//                val orientation = FloatArray(3)
//                SensorManager.getOrientation(R, orientation)
//                Log.d("onSensorChanged", "orientation: ${orientation[0]}, ${orientation[1]}, ${orientation[2]}")
//                val _azimuth = orientation[0]
//
//                azimuthMemo = azimuthMemo.drop(1) + listOf(_azimuth.toDouble())
//                val median = azimuthMemo.sorted()[3]
//
//                azimuthInDegrees = Math.toDegrees(median)
//                if (azimuthInDegrees < 0) {
//                    azimuthInDegrees += 360
//                }
//
//                azimuth = azimuthInDegrees
//
//                // Log.d("Azimuth", "方位角: $azimuthInDegrees 度")
//            }
//        }

    }

    fun setCurrentLocation(latitude: Double, longitude: Double) {
        currentLatitude = latitude
        currentLongitude = longitude
        currentLocation.latitude = latitude
        currentLocation.longitude = longitude
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("Location","$sensor,$accuracy")
    }

    fun registerSensorListener() {
        sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_UI)
//        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    fun unregisterSensorListener() {
        sensorManager.unregisterListener(this,rotationVector)
//        sensorManager.unregisterListener(this,magnetometer)
//        sensorManager.unregisterListener(this,accelerometer)
    }

}