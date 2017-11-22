package com.vsquad.projects.govorillo.presentation.presenter.topic

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.common.MyTimer
import com.vsquad.projects.govorillo.common.SpeakingState
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.view.topic.BaseTopicView
import kotlinx.android.synthetic.main.fragment_free_topic.*
import ru.yandex.speechkit.*

/**
 * Created by Vova on 18.11.2017.
 */

abstract class BaseTopicPresenter<T : BaseTopicView> : BaseFragmentPresenter<T>(), RecognizerListener {
    lateinit var tmrSpeech: MyTimer
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
        speakingState = SpeakingState.STOPPED
        viewState.setSpeakingState(speakingState)
        Log.d("text", p1!!.bestResultText)
    }

    override fun onError(p0: Recognizer?, p1: Error?) {

    }

    lateinit var recognizer: Recognizer


    init {
        SpeechKit.getInstance().configure(GovorilloApplication.INSTANCE.applicationContext,
                "ff1fb365-dd6d-4670-adf1-9073ba9d297d")
    }

    var speakingState: SpeakingState = SpeakingState.STOPPED

    @SuppressLint("MissingPermission")
    open fun switchSpeakingState(){
        if(speakingState == SpeakingState.STOPPED || speakingState == SpeakingState.FINISHED){
            speakingState = SpeakingState.STARTING
            viewState.setSpeakingState(speakingState)
            recognizer = Recognizer.create("ru-RU", Recognizer.Model.NOTES, this, true)
            recognizer.start()
            tmrSpeech = MyTimer(1, 0, Int.MAX_VALUE){
                viewState.setSpeakingTime(it)
            }
            tmrSpeech.start()
        }else if (speakingState == SpeakingState.STARTED){
            speakingState = SpeakingState.FINISHING
            viewState.setSpeakingState(speakingState)
            recognizer.finishRecording()
            tmrSpeech.stop()
        }

    }

}
