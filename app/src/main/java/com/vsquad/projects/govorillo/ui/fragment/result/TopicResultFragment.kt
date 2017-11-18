package com.vsquad.projects.govorillo.ui.fragment.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultView
import com.vsquad.projects.govorillo.presentation.presenter.result.TopicResultPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.ui.fragment.base.BaseFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TopicResultFragment : BaseFragment(), TopicResultView {
    override var fragmentTitle: String = "Результат"

    companion object {
        const val TAG = "TopicResultFragment"

        fun newInstance(): TopicResultFragment {
            val fragment: TopicResultFragment = TopicResultFragment()
            val args: Bundle = Bundle()
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
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topic_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
