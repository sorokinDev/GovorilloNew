package com.vsquad.projects.govorillo.presentation.presenter.twister

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.Screens
import com.vsquad.projects.govorillo.common.MyTimer
import com.vsquad.projects.govorillo.model.analyser.TwisterAnalyser
import com.vsquad.projects.govorillo.model.analyser.TwisterSpeechResult
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@InjectViewState
class TwisterPresenter : BaseFragmentPresenter<TwisterView>() {

    var isLastFlag: Boolean = false
    @Inject lateinit var repository: TwisterRepository

    var recognizers: ArrayList<Recognizer> = ArrayList()
    var preparingTimer : MyTimer? = null

    var results = ArrayList<TwisterSpeechResult>()

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
        }else{
            results.clear()
            recognizers.clear()
        }
    }

    private fun setNewTwister() {
        val twister = repository.getRandomTwiser(TwisterEntity.LEVEL_NORMAL)
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
            var startTime : Long = 0
            var endTime: Long = 0
            val twister1 = twister

            override fun onSoundDataRecorded(p0: Recognizer?, p1: ByteArray?) {

            }

            override fun onPowerUpdated(p0: Recognizer?, p1: Float) {

            }

            override fun onPartialResults(p0: Recognizer?, p1: Recognition?, p2: Boolean) {

            }

            override fun onRecordingBegin(p0: Recognizer?) {
                Log.d("Listener", "onRecBeg")
                viewState.setStatusText("Говорите")
                viewState.pulse()
                startTime = System.currentTimeMillis()

            }
            override fun onRecordingDone(p0: Recognizer?) {
                endTime = System.currentTimeMillis()
            }

            override fun onSpeechEnds(p0: Recognizer?) {

            }

            override fun onSpeechDetected(p0: Recognizer?) {

            }

            override fun onRecognitionDone(p0: Recognizer?, p1: Recognition?) {
                results.add(TwisterSpeechResult(twister1, p1!!.bestResultText, (endTime - startTime - 50).toInt()))
                if(isLastFlag){
                    isLastFlag = false
                    router.showSystemMessage("Finish")
                    var analysisRes = TwisterAnalyser.analyse(results)
                    router.navigateTo(Screens.TWISTER_RESULT_SCREEN, analysisRes)
                    changeMode(TwisterView.MODE_PREPARING)
                }
            }

            override fun onError(p0: Recognizer?, p1: Error?) {

            }

        }, true))
        recognizers.last().start()
    }

    fun nextTwister() {
        if(!recognizers.isEmpty()) recognizers.last().finishRecording()
        setNewTwister()
    }

    fun finishTwistering(){
        isLastFlag = true
        preparingTimer?.stop()
        if(!recognizers.isEmpty()) recognizers.last().finishRecording()
        viewState.setStatusText("Подождите. Идет анализ.")
    }


}
