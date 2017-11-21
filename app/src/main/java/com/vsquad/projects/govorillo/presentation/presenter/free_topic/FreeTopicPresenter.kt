package com.vsquad.projects.govorillo.presentation.presenter.free_topic

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.presenter.topic.BaseTopicPresenter
import com.vsquad.projects.govorillo.presentation.view.free_topic.FreeTopicView
import ru.yandex.speechkit.*

@InjectViewState
class FreeTopicPresenter : BaseTopicPresenter<FreeTopicView>() {
    override var isOpen: Boolean = false

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

}
