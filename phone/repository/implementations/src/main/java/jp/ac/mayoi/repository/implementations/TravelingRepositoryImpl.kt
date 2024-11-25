package jp.ac.mayoi.repository.implementations

import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import jp.ac.mayoi.service.interfaces.SpotService

class TravelingRepositoryImpl(
    private val spotService: SpotService,
) : TravelingRepository {
    override suspend fun getNearSpots(lat: Double, lng: Double): List<LocalSpot> =
        spotService.getSpotByLatLng(lat, lng).map { spot ->
            LocalSpot(spot)
        }
}
