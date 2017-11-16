package com.vsquad.projects.govorillo.presentation.presenter.result

import android.content.Context
import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.model.repository.TopicRepository
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultFragmentView
import ru.terrakok.cicerone.Router
import java.util.prefs.Preferences
import javax.inject.Inject

@InjectViewState
class TopicResultFragmentPresenter : MvpPresenter<TopicResultFragmentView>() {
    @Inject lateinit var router: Router

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)

    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val sPref = GovorilloApplication.INSTANCE.getSharedPreferences(PrefConst.APP_PREFERENCES, Context.MODE_PRIVATE)
        val isUserAuth = sPref.getBoolean(PrefConst.USER_AUTHORIZED, false)
        var timesResShowed = sPref.getInt(PrefConst.TIMES_RESULT_SHOWED, 0)
        if(!isUserAuth){
            timesResShowed += 1
            sPref.edit().putInt(PrefConst.TIMES_RESULT_SHOWED, timesResShowed).apply()

        }
    }
}
