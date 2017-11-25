package com.vsquad.projects.govorillo.ui.fragment.result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultView
import com.vsquad.projects.govorillo.presentation.presenter.result.TopicResultPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.mixpanel.android.mpmetrics.MixpanelAPI
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.getRandom
import com.vsquad.projects.govorillo.model.analyser.BaseTextAnalyser
import com.vsquad.projects.govorillo.model.analyser.TextAnalysisResult
import com.vsquad.projects.govorillo.model.analyser.TwisterAnalysisResult
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_topic_result.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TopicResultFragment : BaseFragment(), TopicResultView {
    override fun setSpeechResult(res: TextAnalysisResult) {
        Log.d("RESULT", res.text)

        //region SPEED
        speedView.speedTo(20f, 0)
        speedView.speedTo(res.speed.toFloat(), 1000)
        //Handler().postDelayed(Runnable { speedView.speedTo(200f, 1000) }, 50)
        //Handler().postDelayed(Runnable { speedView.speedTo(res.speed.toFloat(), 500) }, 1050)

        val tooSlow = arrayOf("Вам стоит говорить намного быстрее. Тренируйтесь в скорооворках")
        val bitSlow = arrayOf("Вам немного не достает в скорости речи. Старайтесь уменьшать паузы между словами")
        val tooFast = arrayOf("Вы говорите слишком быстро. Возможно людям будет трудно вас понимать.")
        val bitFast = arrayOf("Ваш темп речи немного выше идеального. Увеличьте дыхательные паузы и вы придете к оптимальному темпу")
        val normalSpeed = arrayOf("Ваш темп речи на отличном уровне. Сохраняйте его.")
        tv_speed_advice.text =  if(res.speed < 70){
            tooSlow.getRandom()
        }else if(res.speed < 92){
            bitSlow.getRandom()
        }else if(res.speed > 140){
            tooFast.getRandom()
        }else if(res.speed > 122){
            bitFast.getRandom()
        }else{
            normalSpeed.getRandom()
        }
        //endregion

        //region WATERNESS
        tv_needless_words.text = resources.getString(R.string.percent_template, (res.waterness*100).toInt())
        tv_needless_words_advice.text = if (res.waterness > 0.3){
            "Вам следует уменьшить количество лишних слов, не несущих смысла."
        }else{
            "Вводные и прочие слова присутствуют в меру."
        }
        //endregion

        //region HADRNESS
        val SMOGCategories = arrayOf(
                "1 - 3-й класс (возраст примерно: 6-8 лет)",
                "1 - 3-й класс (возраст примерно: 6-8 лет)",
                "1 - 3-й класс (возраст примерно: 6-8 лет)",
                "4 - 6-й класс (возраст примерно: 9-11 лет)",
                "4 - 6-й класс (возраст примерно: 9-11 лет)",
                "4 - 6-й класс (возраст примерно: 9-11 лет)",
                "7 - 9-й класс (возраст примерно: 12-14 лет)",
                "7 - 9-й класс (возраст примерно: 12-14 лет)",
                "7 - 9-й класс (возраст примерно: 12-14 лет)",
                "10 - 11-й класс (возраст примерно: 15-16 лет)",
                "10 - 11-й класс (возраст примерно: 15-16 лет)",
                "1 - 3 курсы ВУЗа (возраст примерно: 17-19 лет)",
                "1 - 3 курсы ВУЗа (возраст примерно: 17-19 лет)",
                "1 - 3 курсы ВУЗа (возраст примерно: 17-19 лет)",
                "4 - 6 курсы ВУЗа (возраст примерно: 20-22 лет)",
                "4 - 6 курсы ВУЗа (возраст примерно: 20-22 лет)",
                "4 - 6 курсы ВУЗа (возраст примерно: 20-22 лет)"
        )
        val smogForArray: Int = minOf(Math.round(res.SMOG_index), 16).toInt()
        tv_hardness.text = "Текст будет понятен людям с образованием: " + SMOGCategories[smogForArray]
        tv_hardness_advice.text = if(smogForArray > 12){
            "Постарайтесь упростить текст для увеличения понимания."
        }else{
            "Ваш текст будет легко воспринят аудиторией."
        }
        //endregion

        if(res.isRandomTopic){
            ll_random_topic_only.visibility = View.VISIBLE
            if(res.preparingTimeDiff > 0.2){
                tv_prep_time_diff.text = resources.getString(R.string.preparing_time_diff_template, "превышено на " +
                        (res.preparingTimeDiff*100).toInt() + " %. Больше тренируйтесь говорить на заданные темы.")
            }else{
                tv_prep_time_diff.text = resources.getString(R.string.preparing_time_diff_template, "в пределах нормы. Так держать!")
            }
            if(res.speechTimeDiff > 0.2){
                tv_speech_time_diff.text = resources.getString(R.string.speech_time_diff_template, "превышено на " +
                        (res.speechTimeDiff*100).toInt() + " %. Старайтесь не растягивать свои мысли.")
            }else if(res.speechTimeDiff < -0.2){
                tv_speech_time_diff.text = resources.getString(R.string.speech_time_diff_template, "меньше желаемого на " +
                        (-res.speechTimeDiff*100).toInt() + " %. Развивайте способность рассуждать на разные темы. Это поможет вам продумывать выступления и отвечать на вопросы.")
            }else{
                tv_speech_time_diff.text = resources.getString(R.string.speech_time_diff_template, "в пределах нормы. Отлично!")
            }
        }else{
            ll_random_topic_only.visibility = View.GONE
        }
    }

    override var fragmentTitle: String = "Результат"
    lateinit var stringRes: String
    lateinit var mixpanel : MixpanelAPI

    companion object {
        const val TAG = "TopicResultFragment"

        fun newInstance(res : TextAnalysisResult): TopicResultFragment {
            val fragment: TopicResultFragment = TopicResultFragment()

            val args: Bundle = Bundle()
            args.putString("RESULT", res.toString())
            fragment.arguments = args
            return fragment
        }
    }

    //region INJECTIONS
    @InjectPresenter
    lateinit var mRandomTopicResultPresenter: TopicResultPresenter
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        GovorilloApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        if(arguments != null){
            stringRes = arguments.getString("RESULT")
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mixpanel = MixpanelAPI.getInstance(context, resources.getString(R.string.mixpanel_token))
        return inflater.inflate(R.layout.fragment_topic_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
