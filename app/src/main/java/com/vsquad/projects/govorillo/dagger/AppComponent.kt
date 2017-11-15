package com.vsquad.projects.govorillo.dagger

import android.app.Application
import android.app.DialogFragment
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.dagger.module.NavigationModule
import com.vsquad.projects.govorillo.dagger.module.RepositoryModule
import com.vsquad.projects.govorillo.presentation.presenter.main.MainActivityPresenter
import com.vsquad.projects.govorillo.presentation.presenter.random_topic.RandomTopicFragmentPresenter
import com.vsquad.projects.govorillo.presentation.presenter.twister.TwisterFragmentPresenter
import com.vsquad.projects.govorillo.ui.activity.main.MainActivity
import com.vsquad.projects.govorillo.ui.fragment.random_topic.RandomTopicFragment
import com.vsquad.projects.govorillo.ui.fragment.twister.TwisterFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Vova on 13.11.2017.
 */
@Singleton
@Component(modules = arrayOf(NavigationModule::class, RepositoryModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: TwisterFragment)
    fun inject(fragment: RandomTopicFragment)

    fun inject(presenter: MainActivityPresenter)
    fun inject(presenter: RandomTopicFragmentPresenter)
    fun inject(presenter: TwisterFragmentPresenter)
}