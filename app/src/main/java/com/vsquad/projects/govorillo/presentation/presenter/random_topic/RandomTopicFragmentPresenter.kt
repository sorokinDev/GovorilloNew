package com.vsquad.projects.govorillo.presentation.presenter.random_topic

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.model.repository.TopicRepository
import com.vsquad.projects.govorillo.presentation.view.random_topic.RandomTopicFragmentView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RandomTopicFragmentPresenter : MvpPresenter<RandomTopicFragmentView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var repository: TopicRepository

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

    var speakingState:SpeakingState = SpeakingState.STOPPED

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setRandomTopic()
    }

    fun setRandomTopic(){
        viewState.setTopic(repository.getRandomTopic())
    }

    fun switchSpeakingState(){
        if(speakingState == SpeakingState.STOPPED){
            speakingState = SpeakingState.STARTING
            viewState.setSpeakingState(speakingState)

            speakingState = SpeakingState.STARTED
            viewState.setSpeakingState(speakingState)
        }else if (speakingState == SpeakingState.STARTED){
            speakingState = SpeakingState.FINISHING
            viewState.setSpeakingState(speakingState)
            Thread.sleep(1000)
            speakingState = SpeakingState.FINISHED
            viewState.setSpeakingState(speakingState)
            router.navigateTo(Screens.RANDOM_TOPIC_RESULT_SCREEN)
        }

    }




}
