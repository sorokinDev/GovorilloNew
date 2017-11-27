package com.vsquad.projects.govorillo.model.repository

import android.util.Log
import com.opencsv.CSVReader
import com.vsquad.projects.govorillo.GovorilloApplication
import com.vsquad.projects.govorillo.common.getRandom
import com.vsquad.projects.govorillo.model.entity.TopicEntity
import com.vsquad.projects.govorillo.model.entity.TwisterEntity
import java.io.InputStreamReader

/**
 * Created by Vova on 15.11.2017.
 */
class TopicRepository {
    val topics = arrayOf(
        TopicEntity("Расскажи о себе"),
        TopicEntity("Как тебе погода"),
        TopicEntity("Расскажи о своем последнем путешествии"),
        TopicEntity("Любишь ли ты животных? У тебя есть домашние животные? Расскажи о них"),
        TopicEntity("Кем ты работаешь или мечтаешь стать?"),
        TopicEntity("Самый счастливый момент в вашей жизни?")
    )
    var topicsFromCsv: Array<TopicEntity?>
    init {
        val reader = CSVReader(InputStreamReader(GovorilloApplication.INSTANCE.assets.open("topics.csv")))

        var allRows = reader.readAll()
        topicsFromCsv = arrayOfNulls(allRows.size)
        Log.d("TWISTER", ""+allRows.size)

        for ((i, r) in allRows.withIndex()) {
            Log.d("TWISTER", r[0])
            topicsFromCsv[i] = TopicEntity(r[0])
        }
        /*val file = File("twisters.csv")

        val csvReader = CsvReader()

        val csv = csvReader.read(file, Charset.defaultCharset())
        twistersFromCsv = arrayOfNulls<TwisterEntity>(csv.rowCount)
        for ((i, row) in csv.rows.withIndex()) {
            twistersFromCsv[i] = TwisterEntity(row.getField(0))
        }*/
    }

    fun getRandomTopic(): TopicEntity{
        return topicsFromCsv.getRandom()!!
    }
}