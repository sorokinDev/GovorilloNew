package com.vsquad.projects.govorillo.presentation.view.free_topic

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vsquad.projects.govorillo.common.SpeakingState

interface FreeTopicView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSpeakingState(speakingState: SpeakingState)
}
