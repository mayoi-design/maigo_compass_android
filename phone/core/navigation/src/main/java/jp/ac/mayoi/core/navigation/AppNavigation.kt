package jp.ac.mayoi.core.navigation

import kotlinx.serialization.Serializable

    //おもいで画面
    @Serializable
    data object MemoryNavigation

    //オンボーディング
    @Serializable
    data object OnboardingNavigation

    //ランキング
    @Serializable
    data object RankingNavigation

    //シェア
    @Serializable
    data object ShareNavigation

    //歩いてる時
    @Serializable
    data object TravelingNavigation
