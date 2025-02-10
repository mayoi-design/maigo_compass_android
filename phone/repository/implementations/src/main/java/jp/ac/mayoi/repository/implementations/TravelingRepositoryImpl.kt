package jp.ac.mayoi.repository.implementations

import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.phone.model.enums.ServiceAreaAdapter
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import jp.ac.mayoi.service.interfaces.SpotService
import jp.ac.mayoi.service.interfaces.TravelService

class TravelingRepositoryImpl(
    private val spotService: SpotService,
    private val travelService: TravelService,
) : TravelingRepository {
    override suspend fun getNearSpots(lat: Double, lng: Double): List<LocalSpot> =
        spotService.getSpotByLatLng(lat, lng).map { spot ->
            LocalSpot(spot)
        }

    override suspend fun getAreaByCoordinate(
        lat: Double,
        lng: Double,
    ): ServiceAreaAdapter.ServiceArea {
        val remote = travelService.getAreaByCoordinate(lat, lng)
        return ServiceAreaAdapter.fromRemote(remote.areaId)
    }
}
