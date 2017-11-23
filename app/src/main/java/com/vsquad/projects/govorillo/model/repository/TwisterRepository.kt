package com.vsquad.projects.govorillo.model.repository

import com.vsquad.projects.govorillo.model.entity.TwisterEntity

/**
 * Created by Vova on 20.11.2017.
 */
class TwisterRepository {
    companion object {

    }
    val TWISTERS = arrayOf(TwisterEntity("Шла Саша по шоссе и сосала сушку", TwisterEntity.LEVEL_EASY),
            TwisterEntity("Вашему пономарю нашего пономаря не перепономарить стать. Наш пономарь вашего пономаря перепономарит, перевыпономарит.", TwisterEntity.LEVEL_NORMAL),
            TwisterEntity("Сорока за строчкою строчка строчит сорочатам сорочки.", TwisterEntity.LEVEL_HARD))

    fun getRandomTwiser(level: String): TwisterEntity{
        var filteredByLevel = TWISTERS.filter { it.level == level }
        return filteredByLevel[(Math.random()*filteredByLevel.size).toInt()]
    }
}