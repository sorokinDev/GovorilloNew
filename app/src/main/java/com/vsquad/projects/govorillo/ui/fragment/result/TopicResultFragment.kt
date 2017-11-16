package com.vsquad.projects.govorillo.ui.fragment.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.presentation.view.result.TopicResultFragmentView
import com.vsquad.projects.govorillo.presentation.presenter.result.TopicResultFragmentPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TopicResultFragment : MvpAppCompatFragment(), TopicResultFragmentView {
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
    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mRandomTopicResultFragmentPresenter: TopicResultFragmentPresenter
    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topic_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
