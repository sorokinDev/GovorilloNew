package com.vsquad.projects.govorillo.presentation.presenter.random_topic

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.MyTimer
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.model.analyser.BaseTextAnalyser
import com.vsquad.projects.govorillo.model.entity.TopicEntity
import com.vsquad.projects.govorillo.model.repository.TopicRepository
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.presenter.topic.BaseTopicPresenter
import com.vsquad.projects.govorillo.presentation.view.random_topic.RandomTopicView
import ru.yandex.speechkit.Error
import ru.yandex.speechkit.Recognition
import ru.yandex.speechkit.Recognizer
import ru.yandex.speechkit.RecognizerListener
import ru.yandex.speechkit.SpeechKit
import javax.inject.Inject

@InjectViewState
class RandomTopicPresenter : BaseTopicPresenter<RandomTopicView>() {
    lateinit var topic: TopicEntity
    override fun switchSpeakingState() {
        super.switchSpeakingState()
        if(speakingState == SpeakingState.STARTED){
            tmrPrep!!.stop()
        }
    }

    var tmrPrep: MyTimer? = null

    override fun onRecognitionDone(p0: Recognizer?, p1: Recognition?) {
        super.onRecognitionDone(p0, p1)
        var res = BaseTextAnalyser.analyse(p1!!.bestResultText, tmrSpeech.curT, -tmrPrep!!.curT+topic.preparingTime, topic)
        router.navigateTo(Screens.TOPIC_RESULT_SCREEN, res)
    }

    override var isOpen: Boolean = false

    @Inject lateinit var repository: TopicRepository

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setRandomTopic()
        viewState.setSpeakingState(speakingState)
    }

    fun setRandomTopic(){
        if(tmrPrep != null) tmrPrep!!.stop()
        topic = repository.getRandomTopic()
        tmrPrep = MyTimer(-1, 0, topic.preparingTime){
            viewState.setPreparingTime(it)
        }
        viewState.setTopic(topic)

        tmrPrep!!.start()
    }







}
