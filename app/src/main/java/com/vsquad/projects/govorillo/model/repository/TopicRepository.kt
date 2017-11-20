package com.vsquad.projects.govorillo.model.repository

import com.vsquad.projects.govorillo.model.entity.TopicEntity

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

    fun getRandomTopic(): TopicEntity{
        return topics[(Math.random()*topics.size).toInt()]
    }
}