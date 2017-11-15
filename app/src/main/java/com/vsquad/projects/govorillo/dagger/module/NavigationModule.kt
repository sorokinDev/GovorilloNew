package com.vsquad.projects.govorillo.dagger.module

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * Created by Vova on 13.11.2017.
 */

@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides @Singleton fun provideRouter(): Router = cicerone.router

    @Provides @Singleton fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder

}
