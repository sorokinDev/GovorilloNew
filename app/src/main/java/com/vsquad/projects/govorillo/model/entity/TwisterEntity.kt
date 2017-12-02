package com.vsquad.projects.govorillo.model.entity

/**
 * Created by Vova on 20.11.2017.
 */
class TwisterEntity (val text: String, val level: String = TwisterEntity.LEVEL_NORMAL){
    companion object {
        val LEVEL_EASY = "easy"
        val LEVEL_NORMAL = "normal"
        val LEVEL_HARD = "hard"
    }
}