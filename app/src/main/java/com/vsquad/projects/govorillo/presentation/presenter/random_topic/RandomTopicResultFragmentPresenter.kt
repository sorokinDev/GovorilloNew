package com.vsquad.projects.govorillo.presentation.presenter.random_topic

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.model.repository.TopicRepository
import com.vsquad.projects.govorillo.presentation.view.random_topic.RandomTopicResultFragmentView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RandomTopicResultFragmentPresenter : MvpPresenter<RandomTopicResultFragmentView>() {
    @Inject lateinit var router: Router
    @Inject lateinit var repository: TopicRepository

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }
}
