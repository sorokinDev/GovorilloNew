package com.vsquad.projects.govorillo.presentation.view.topic

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vsquad.projects.govorillo.common.SpeakingState

/**
 * Created by Vova on 18.11.2017.
 */
interface BaseTopicView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSpeakingState(speakingState: SpeakingState)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSpeakingTime(speakingTime: Int)
}