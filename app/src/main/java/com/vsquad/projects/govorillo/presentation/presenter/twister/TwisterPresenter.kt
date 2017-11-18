package com.vsquad.projects.govorillo.presentation.presenter.twister

import com.arellomobile.mvp.InjectViewState
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.view.twister.TwisterView

@InjectViewState
class TwisterPresenter : BaseFragmentPresenter<TwisterView>() {
    override var isOpen: Boolean = false

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

}
