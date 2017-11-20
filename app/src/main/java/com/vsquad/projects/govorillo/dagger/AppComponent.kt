package com.vsquad.projects.govorillo.dagger

import com.vsquad.projects.govorillo.dagger.module.NavigationModule
import com.vsquad.projects.govorillo.dagger.module.RepositoryModule
import com.vsquad.projects.govorillo.presentation.presenter.auth.AuthPresenter
import com.vsquad.projects.govorillo.presentation.presenter.free_topic.FreeTopicPresenter
import com.vsquad.projects.govorillo.presentation.presenter.main.MainActivityPresenter
import com.vsquad.projects.govorillo.presentation.presenter.profile.ProfilePresenter
import com.vsquad.projects.govorillo.presentation.presenter.random_topic.RandomTopicPresenter
import com.vsquad.projects.govorillo.presentation.presenter.result.TopicResultPresenter
import com.vsquad.projects.govorillo.presentation.presenter.twister.TwisterPresenter
import com.vsquad.projects.govorillo.ui.activity.main.MainActivity
import com.vsquad.projects.govorillo.ui.fragment.auth.AuthFragment
import com.vsquad.projects.govorillo.ui.fragment.free_topic.FreeTopicFragment
import com.vsquad.projects.govorillo.ui.fragment.profile.ProfileFragment
import com.vsquad.projects.govorillo.ui.fragment.random_topic.RandomTopicFragment
import com.vsquad.projects.govorillo.ui.fragment.result.TopicResultFragment
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
    fun inject(fragment: TopicResultFragment)
    fun inject(fragment: FreeTopicFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: AuthFragment)

    fun inject(presenter: MainActivityPresenter)

    fun inject(presenter: TwisterPresenter)
    fun inject(presenter: RandomTopicPresenter)
    fun inject(presenter: TopicResultPresenter)
    fun inject(fragment: FreeTopicPresenter)
    fun inject(fragment: ProfilePresenter)
    fun inject(fragment: AuthPresenter)
}