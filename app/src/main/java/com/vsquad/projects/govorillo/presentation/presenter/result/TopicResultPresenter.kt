package com.vsquad.projects.govorillo.presentation.presenter.result

import com.arellomobile.mvp.InjectViewState
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.model.analyser.BaseTextAnalyser
import com.vsquad.projects.govorillo.model.analyser.TextAnalysisResult
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultView

@InjectViewState
class TopicResultPresenter : BaseResultPresenter<TopicResultView>() {

    override var isOpen: Boolean = true
    var speechRes: TextAnalysisResult = TextAnalysisResult()
        get() = field
        set(value) {
            field = value
            viewState.setSpeechResult(value)
        }


    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

}
