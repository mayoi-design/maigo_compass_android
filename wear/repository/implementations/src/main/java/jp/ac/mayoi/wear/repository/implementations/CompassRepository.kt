package jp.ac.mayoi.wear.repository.implementations

import android.hardware.SensorManager
import android.util.Log
import jp.ac.mayoi.wear.repository.interfaces.CompassRepository

class CompassRepositoryImpl : CompassRepository {
    private var azimuthMemo: List<Double> = List(7) { 0.0 }

    override fun getCurrentAzimuth(rotationVector: FloatArray): Double {
        val rotationMatrix = FloatArray(9)
        SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector)

        val orientationValues = FloatArray(3)
        SensorManager.getOrientation(rotationMatrix, orientationValues)

        azimuthMemo = azimuthMemo.drop(1) + listOf(orientationValues[0].toDouble())
        val median = azimuthMemo.sorted()[3]

        val azimuth = (Math.toDegrees(median) + 360) % 360  // 方位角 (度)
        Log.d("CompassRepository", "Calculated azimuth: $azimuth")
        return azimuth
    }
}