package jp.ac.mayoi.wear.repository.interfaces

interface CompassRepository {
    fun getCurrentAzimuth(rotationVector: FloatArray): Double
}