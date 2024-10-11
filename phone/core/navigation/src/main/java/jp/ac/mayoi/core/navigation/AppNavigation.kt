package jp.ac.mayoi.core.navigation

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

sealed class Screen{

    //インプレッションを見る
    @Serializable
    data object ImpressionsNavigation:Screen()

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
