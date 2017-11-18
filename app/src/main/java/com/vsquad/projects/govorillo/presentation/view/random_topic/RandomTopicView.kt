package com.vsquad.projects.govorillo.presentation.view.random_topic

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.model.entity.TopicEntity

interface RandomTopicView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setTopic(topic: TopicEntity)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSpeakingState(speakingState: SpeakingState)
}
