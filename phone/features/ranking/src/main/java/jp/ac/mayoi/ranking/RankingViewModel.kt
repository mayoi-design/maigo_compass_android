package jp.ac.mayoi.ranking

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ac.mayoi.core.util.LoadState
import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.repository.interfaces.RankingRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

class RankingViewModel(
    private val rankingRepository: RankingRepository,
) : ViewModel() {
    var rankingAreaState: LoadState<ImmutableList<RemoteRankingArea>> by mutableStateOf(
        LoadState.Loading(null)
    )
        private set
    var rankingState: LoadState<ImmutableList<LocalSpot>> by mutableStateOf(
        LoadState.Loading(null)
    )
        private set
    var selectingArea: RemoteRankingArea? by mutableStateOf(null)

    fun loadRankingArea() {
        viewModelScope.launch {
            rankingAreaState = LoadState.Loading(rankingAreaState.value)
            try {
                val rankingArea =
                    rankingRepository.getAvailArea().toImmutableList()
                rankingAreaState = LoadState.Success(rankingArea)

                if (selectingArea == null) {
                    selectingArea = rankingArea[0]
                }
            } catch (e: Exception) {
                Log.d("RankingViewModel", "${e.message}")
                rankingAreaState = LoadState.Error(rankingAreaState.value, e)
            }
        }
    }

    fun loadRanking(areaId: String) {
        viewModelScope.launch {
            rankingState = LoadState.Loading(rankingState.value)
            try {
                val ranking =
                    rankingRepository.getRanking(areaId).toImmutableList()
                rankingState = LoadState.Success(ranking)
            } catch (e: Exception) {
                Log.d("RankingViewModel", "${e.message}")
                rankingState = LoadState.Error(rankingState.value, e)
            }
        }
    }
}