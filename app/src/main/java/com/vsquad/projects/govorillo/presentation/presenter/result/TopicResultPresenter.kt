package com.vsquad.projects.govorillo.presentation.presenter.result

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.PrefConst
import com.vsquad.projects.govorillo.model.analyser.TextAnalysisResult
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultView
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import android.R.attr.name
import android.widget.Toast
import com.vsquad.projects.govorillo.model.api.AnalizerApi
import com.vsquad.projects.govorillo.model.api.ToStringConverterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*


@InjectViewState
class TopicResultPresenter : BaseResultPresenter<TopicResultView>() {

    override var isOpen: Boolean = true

    var speechRes: TextAnalysisResult = TextAnalysisResult()
        get() = field
        set(value) {
            field = value
            viewState.setSpeechResult(value)
        }

    init {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (!speechRes.isRandomTopic) {
            val adapter = Retrofit.Builder()
                    .baseUrl("http://206.81.1.244:4357/")
                    .build()

            val api = adapter.create(AnalizerApi::class.java)

            api.getSubject(speechRes.text).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    viewState.setSpeechSubject("Нет соединения", "...")
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    var res = response.body()?.string() ?: "Нет соединения|0"
                    var subj = res.split("|")[0]
                    var prob = res.split("|")[1]
                    viewState.setSpeechSubject(subj,
                            if(response.body() != null) "$prob %" else "...")
                }
            })
        }
    }
}
