package com.vsquad.projects.govorillo.common

/**
 * Created by Vova on 15.11.2017.
 */
enum class SpeakingState {
    STOPPED, STARTING, STARTED, FINISHING, FINISHED
}

class PrefConst{
    companion object {
        val APP_PREFERENCES = "govorilloPref"
        val TIMES_RESULT_SHOWED = "timesResultShowed"
        val USER_AUTHORIZED = "userAuthorized"
    }
}