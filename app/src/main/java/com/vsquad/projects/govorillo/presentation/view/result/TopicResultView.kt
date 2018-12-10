package com.vsquad.projects.govorillo.presentation.view.result

import com.arellomobile.mvp.MvpView
import com.vsquad.projects.govorillo.model.analyser.TextAnalysisResult


interface TopicResultView : MvpView {
    fun setSpeechResult(res: TextAnalysisResult)
    fun setSpeechSubject(subj: String, prob: String)
}
