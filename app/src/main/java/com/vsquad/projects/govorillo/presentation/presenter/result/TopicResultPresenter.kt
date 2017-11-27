package com.vsquad.projects.govorillo.presentation.presenter.result

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.model.analyser.TextAnalysisResult
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

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
