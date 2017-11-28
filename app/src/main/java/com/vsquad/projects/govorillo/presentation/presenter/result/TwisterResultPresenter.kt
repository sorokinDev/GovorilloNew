package com.vsquad.projects.govorillo.presentation.presenter.result

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.model.analyser.TwisterAnalysisResult
import com.vsquad.projects.govorillo.presentation.view.result.TwisterResultView

@InjectViewState
class TwisterResultPresenter : BaseResultPresenter<TwisterResultView>() {
    override var isOpen: Boolean = false
    var speechRes: TwisterAnalysisResult = TwisterAnalysisResult()
        get() = field
        set(value) {
            field = value
            viewState.setSpeechResult(value)
        }
}
