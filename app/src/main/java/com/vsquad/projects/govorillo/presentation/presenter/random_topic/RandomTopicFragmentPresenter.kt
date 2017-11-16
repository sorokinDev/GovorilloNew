package com.vsquad.projects.govorillo.presentation.presenter.random_topic

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.model.repository.TopicRepository
import com.vsquad.projects.govorillo.presentation.view.random_topic.RandomTopicFragmentView
import ru.terrakok.cicerone.Router
import ru.yandex.speechkit.Error
import ru.yandex.speechkit.Recognition
import ru.yandex.speechkit.Recognizer
import ru.yandex.speechkit.RecognizerListener
import ru.yandex.speechkit.SpeechKit
import javax.inject.Inject

@InjectViewState
class RandomTopicFragmentPresenter : MvpPresenter<RandomTopicFragmentView>() {

    @Inject lateinit var router: Router
    @Inject lateinit var repository: TopicRepository
    var recognizerListener = object : RecognizerListener {
        override fun onRecordingDone(p0: Recognizer?) {

        }

        override fun onSoundDataRecorded(p0: Recognizer?, p1: ByteArray?) {

        }

        override fun onPowerUpdated(p0: Recognizer?, p1: Float) {

        }

        override fun onPartialResults(p0: Recognizer?, p1: Recognition?, p2: Boolean) {

        }

        override fun onRecordingBegin(p0: Recognizer?) {
            speakingState = SpeakingState.STARTED
            viewState.setSpeakingState(speakingState)
        }

        override fun onSpeechEnds(p0: Recognizer?) {

        }

        override fun onSpeechDetected(p0: Recognizer?) {

        }

        override fun onRecognitionDone(p0: Recognizer?, p1: Recognition?) {
            speakingState = SpeakingState.FINISHED
            viewState.setSpeakingState(speakingState)
            router.navigateTo(Screens.TOPIC_RESULT_SCREEN)
        }

        override fun onError(p0: Recognizer?, p1: Error?) {

        }

    }
    lateinit var recognizer: Recognizer


    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        SpeechKit.getInstance().configure(GovorilloApplication.INSTANCE.applicationContext,
                "ff1fb365-dd6d-4670-adf1-9073ba9d297d")

    }

    var speakingState:SpeakingState = SpeakingState.STOPPED

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setRandomTopic()
    }

    fun setRandomTopic(){
        viewState.setSpeakingState(speakingState)
        viewState.setTopic(repository.getRandomTopic())
    }

    @SuppressLint("MissingPermission")
    fun switchSpeakingState(){
        if(speakingState == SpeakingState.STOPPED || speakingState == SpeakingState.FINISHED){
            speakingState = SpeakingState.STARTING
            viewState.setSpeakingState(speakingState)
            recognizer = Recognizer.create("ru-RU", Recognizer.Model.NOTES, recognizerListener, true)
            recognizer.start()
        }else if (speakingState == SpeakingState.STARTED){
            speakingState = SpeakingState.FINISHING
            viewState.setSpeakingState(speakingState)
            recognizer.finishRecording()
        }

    }
    //endregion




}
