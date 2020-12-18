package com.example.cleanarchitecturemovieapi.presentation

import android.app.Application
import com.example.cleanarchitecturemovieapi.presentation.ui.home.HomeViewModelFactory
import com.example.cleanarchitecturemovieapi.presentation.ui.movie.MovieViewModelFactory
import com.example.data.network.MovieApi
import com.example.data.repository.MovieRepository
import com.example.domain.MovieImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class BaseApplication : Application(),KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication))

        bind() from singleton { MovieApi() }
        bind() from singleton { MovieRepository(instance()) }
        bind() from singleton { MovieImpl(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { MovieViewModelFactory (instance()) }
    }
}