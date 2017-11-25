package com.vsquad.projects.govorillo.presentation.view.result

import com.arellomobile.mvp.MvpView
import com.vsquad.projects.govorillo.model.analyser.TwisterAnalysisResult

interface TwisterResultView : MvpView {
    fun setSpeechResult(res: TwisterAnalysisResult)

}
