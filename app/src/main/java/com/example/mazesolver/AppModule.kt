package com.example.mazesolver

import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import com.example.mazesolver.core.data.network.HttpClientFactory
import com.example.mazesolver.core.data.parser.GsonFactory
import com.example.mazesolver.list.data.network.MazesApiFactory
import com.example.mazesolver.list.data.network.MazesRemoteDataSource
import com.example.mazesolver.list.data.network.RetrofitMazesDataSource
import com.example.mazesolver.list.data.repository.MazeListRepositoryImpl
import com.example.mazesolver.list.domain.MazeListRepository
import com.example.mazesolver.list.presentation.viewmodel.MazeListViewModel
import com.example.mazesolver.solvedmaze.domain.MazeSolverRepository
import com.example.mazesolver.solvedmaze.presentation.viewmodel.SolvedMazeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    singleOf(::HttpClientFactory)

    singleOf(::MazeListRepositoryImpl).bind<MazeListRepository>()

    singleOf(::RetrofitMazesDataSource).bind<MazesRemoteDataSource>()

    // The AsyncImage in the views will use this to load images as it's a global ImageLoader
    single {
        ImageLoader.Builder(androidContext())
            .crossfade(true)
            .components {
                add(
                    if (SDK_INT >= 28) {
                        ImageDecoderDecoder.Factory()
                    } else {
                        GifDecoder.Factory()
                    }
                )
            }
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    singleOf(::MazesApiFactory)

    singleOf(::GsonFactory)

    single { MazeSolverRepository(get(), get()) }

    viewModelOf(::MazeListViewModel)

    viewModel { (mazeName: String,
                    mazeUrl: String) ->
        SolvedMazeViewModel(mazeName, mazeUrl, get())
    }
}