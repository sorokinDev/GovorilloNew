package com.vsquad.projects.govorillo.presentation.presenter.twister

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.MyTimer
import com.vsquad.projects.govorillo.model.entity.TwisterEntity
import com.vsquad.projects.govorillo.model.repository.TopicRepository
import com.vsquad.projects.govorillo.model.repository.TwisterRepository
import com.vsquad.projects.govorillo.presentation.presenter.base.BaseFragmentPresenter
import com.vsquad.projects.govorillo.presentation.view.twister.TwisterView
import ru.yandex.speechkit.Error
import ru.yandex.speechkit.Recognition
import ru.yandex.speechkit.Recognizer
import ru.yandex.speechkit.RecognizerListener
import ru.yandex.speechkit.SpeechKit
import javax.inject.Inject

@InjectViewState
class TwisterPresenter : BaseFragmentPresenter<TwisterView>() {

    var isLastFlag: Boolean = false
    @Inject lateinit var repository: TwisterRepository

    var recognizers: ArrayList<Recognizer> = ArrayList()
    var preparingTimer : MyTimer? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setMode(TwisterView.MODE_PREPARING)
    }

    override var isOpen: Boolean = false

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        SpeechKit.getInstance().configure(GovorilloApplication.INSTANCE.applicationContext,
                "ff1fb365-dd6d-4670-adf1-9073ba9d297d")
    }

    fun changeMode(mode: Int) {
        viewState.setMode(mode)
        if(mode == TwisterView.MODE_TWISTERING){
            setNewTwister()
        }
    }

    private fun setNewTwister() {
        var twister = repository.getRandomTwiser(TwisterEntity.LEVEL_NORMAL)
        viewState.setNewTwister(twister)
        preparingTimer?.stop()
        preparingTimer = MyTimer(-1, 0, 7, actForTimer = { viewState.setStatusText("Время для подготовки: $it сек") },
                finishAtEnd = true, actAtEnd = { startNewRecording(twister) })
        preparingTimer!!.start()
    }

    @SuppressLint("MissingPermission")
    private fun startNewRecording(twister: TwisterEntity) {
        viewState.setStatusText("Подождите...")
        recognizers.add(Recognizer.create("ru-RU", Recognizer.Model.NOTES, object : RecognizerListener{
            override fun onRecordingDone(p0: Recognizer?) {
                if(!isLastFlag) {
                    setNewTwister()
                }
            }

            override fun onSoundDataRecorded(p0: Recognizer?, p1: ByteArray?) {

            }

            override fun onPowerUpdated(p0: Recognizer?, p1: Float) {

            }

            override fun onPartialResults(p0: Recognizer?, p1: Recognition?, p2: Boolean) {

            }

            override fun onRecordingBegin(p0: Recognizer?) {
                viewState.setStatusText("Говорите")
            }

            override fun onSpeechEnds(p0: Recognizer?) {

            }

            override fun onSpeechDetected(p0: Recognizer?) {

            }

            override fun onRecognitionDone(p0: Recognizer?, p1: Recognition?) {
                if(isLastFlag){
                    router.showSystemMessage("Finish")
                    viewState.setMode(TwisterView.MODE_PREPARING)
                }
            }

            override fun onError(p0: Recognizer?, p1: Error?) {

            }

        }, true).apply { start() })
    }

    fun nextTwister() {
        if(!recognizers.isEmpty()) recognizers.last().finishRecording()
        setNewTwister()
    }

    fun finishTwistering(){
        isLastFlag = true
        if(!recognizers.isEmpty()) recognizers.last().finishRecording()
        viewState.setStatusText("Подождите. Идет анализ.")
    }


}
