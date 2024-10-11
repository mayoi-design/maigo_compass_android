package jp.ac.mayoi.core.navigation

import kotlinx.serialization.Serializable

sealed class Screen{

    //おもいで画面
    @Serializable
    data object MemoryNavigation:Screen()

    //オンボーディング
    @Serializable
    data object OnboardingNavigation:Screen()

    //ランキング
    @Serializable
    data object RankingNavigation:Screen()

    //シェア
    @Serializable
    data object ShareNavigation:Screen()

    //歩いてる時
    @Serializable
    data object TravelingNavigation:Screen()
}
