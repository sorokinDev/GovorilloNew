package com.vsquad.projects.govorillo.dagger.module

import com.vsquad.projects.govorillo.model.repository.TopicRepository
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * Created by Vova on 15.11.2017.
 */
@Module
class RepositoryModule {
    private val topicRepository: TopicRepository = TopicRepository()

    @Provides @Singleton fun provideTopicRepository(): TopicRepository = topicRepository
}