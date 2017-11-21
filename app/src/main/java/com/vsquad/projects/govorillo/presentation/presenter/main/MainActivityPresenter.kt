package com.vsquad.projects.govorillo.presentation.presenter.main

import android.support.annotation.IdRes
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.model.repository.TopicRepository
import com.vsquad.projects.govorillo.presentation.view.main.MainActivityView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setDefaultDrawerItem()
    }

    @Inject lateinit var router: Router

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

    fun onDrawerMenuClick(@IdRes itemId: Int){
        var scr = when(itemId){
            R.id.nav_main -> Screens.FREE_TOPIC_SCREEN
            R.id.nav_random_topic -> Screens.RANDOM_TOPIC_SCREEN
            R.id.nav_twister -> Screens.TWISTER_SCREEN
            else -> Screens.FREE_TOPIC_SCREEN
        }
        router.replaceScreen(scr)
    }

    fun onBackPressed(){
        router.exit()
    }

}
