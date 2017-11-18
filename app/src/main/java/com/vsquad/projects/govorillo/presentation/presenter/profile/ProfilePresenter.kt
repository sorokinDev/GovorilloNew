package com.vsquad.projects.govorillo.presentation.presenter.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.common.SharedPrefUtils
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.view.profile.ProfileView
import ru.yandex.speechkit.SpeechKit

@InjectViewState
class ProfilePresenter : BaseFragmentPresenter<ProfileView>() {
    override var isOpen: Boolean = true

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

    fun exitUser(){
        SharedPrefUtils.edit().remove(PrefConst.USER_AUTHORIZED).remove(PrefConst.USER_PASS).remove(PrefConst.USER_EMAIL).apply()
        router.newRootScreen(Screens.MAIN_SCREEN)
    }

}
