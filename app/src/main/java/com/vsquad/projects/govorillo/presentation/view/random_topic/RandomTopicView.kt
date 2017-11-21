package com.vsquad.projects.govorillo.presentation.view.random_topic

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vsquad.projects.govorillo.model.entity.TopicEntity
import com.vsquad.projects.govorillo.presentation.view.topic.BaseTopicView

interface RandomTopicView : BaseTopicView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setTopic(topic: TopicEntity)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setPreparingTime(time: Int)
}
