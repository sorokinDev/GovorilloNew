package com.vsquad.projects.govorillo.presentation.presenter.twister

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.presentation.view.twister.TwisterFragmentView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class TwisterFragmentPresenter: MvpPresenter<TwisterFragmentView>() {
    @Inject lateinit var router: Router

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

}
