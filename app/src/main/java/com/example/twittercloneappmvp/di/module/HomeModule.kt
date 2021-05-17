package com.example.twittercloneappmvp.di.module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.twittercloneappmvp.BuildConfig
import com.example.twittercloneappmvp.home.contract.HomeContract
import com.example.twittercloneappmvp.home.presenter.HomePresenter
import com.example.twittercloneappmvp.home.repository.HomeRepository
import com.example.twittercloneappmvp.home.view.HomeViewProxy
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
interface HomeModule {

    @Binds
    fun bindActivity(activity: AppCompatActivity): Activity

    @Binds
    fun bindLifecycleOwner(activity: AppCompatActivity): LifecycleOwner

    @Binds
    @Reusable
    fun bindHomeRepository(repository: HomeRepository): HomeContract.Repository

    @Binds
    @ActivityScoped
    fun bindViewHomeViewProxy(viewProxy: HomeViewProxy): HomeContract.ViewProxy

    @Binds
    @ActivityScoped
    fun bindPresenter(presenter: HomePresenter): HomeContract.Presenter
}
