package com.vsquad.projects.govorillo.model.repository

import android.util.Log
import com.vsquad.projects.govorillo.common.getRandom
import com.vsquad.projects.govorillo.model.entity.TwisterEntity
import java.io.File

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import com.opencsv.CSVReader
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.R
import java.io.FileReader
import java.io.InputStreamReader
import java.io.Reader


/**
 * Created by Vova on 20.11.2017.
 */
class TwisterRepository {
    companion object {

    }
    val TWISTERS = arrayOf(TwisterEntity("Шла Саша по шоссе и сосала сушку", TwisterEntity.LEVEL_EASY),
            TwisterEntity("Вашему пономарю нашего пономаря не перепономарить стать. Наш пономарь вашего пономаря перепономарит, перевыпономарит.", TwisterEntity.LEVEL_NORMAL),
            TwisterEntity("Сорока за строчкою строчка строчит сорочатам сорочки.", TwisterEntity.LEVEL_HARD))
    var twistersFromCsv: Array<TwisterEntity?>
    init {
        val reader = CSVReader(InputStreamReader(GovorilloApplication.INSTANCE.assets.open("twisters.csv")))

        var allRows = reader.readAll()
        twistersFromCsv = arrayOfNulls(allRows.size)
        Log.d("TWISTER", ""+allRows.size)

        for ((i, r) in allRows.withIndex()) {
            Log.d("TWISTER", r[0])
            twistersFromCsv[i] = TwisterEntity(r[0])
        }
        /*val file = File("twisters.csv")

        val csvReader = CsvReader()

        val csv = csvReader.read(file, Charset.defaultCharset())
        twistersFromCsv = arrayOfNulls<TwisterEntity>(csv.rowCount)
        for ((i, row) in csv.rows.withIndex()) {
            twistersFromCsv[i] = TwisterEntity(row.getField(0))
        }*/
    }

    fun getRandomTwiser(level: String = TwisterEntity.LEVEL_NORMAL): TwisterEntity{
        /*val filteredByLevel = TWISTERS.filter { it.level == level }
        return filteredByLevel[0]*/
        return twistersFromCsv.getRandom()!!
    }
}