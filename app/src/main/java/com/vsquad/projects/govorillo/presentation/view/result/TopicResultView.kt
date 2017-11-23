package com.vsquad.projects.govorillo.presentation.view.result


import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.vsquad.projects.govorillo.model.analyser.BaseTextAnalyser
import com.vsquad.projects.govorillo.model.analyser.TextAnalysisResult

interface TopicResultView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSpeechResult(res: TextAnalysisResult)

}
