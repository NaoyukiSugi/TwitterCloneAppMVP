package com.example.twittercloneappmvp.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.common_api.home_timeline.HomeTimelineApi
import com.example.twittercloneappmvp.HomeFragment
import com.example.twittercloneappmvp.home.contract.HomeContract
import com.example.twittercloneappmvp.home.presenter.HomePresenter
import com.example.twittercloneappmvp.home.repository.HomeRepository
import com.example.twittercloneappmvp.home.view.HomeViewProxy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class HomeModule {

    @Provides
    fun provideHomeFragment(fragment: Fragment): HomeFragment = fragment as HomeFragment

    @Provides
    fun provideLifecycleOwner(fragment: Fragment): LifecycleOwner = fragment.viewLifecycleOwner

    @Provides
    fun provideHomeRepository(api: HomeTimelineApi): HomeContract.Repository = HomeRepository(api)

    @Provides
    fun provideHomeViewProxy(fragment: Fragment): HomeContract.ViewProxy = HomeViewProxy(fragment)

    @Provides
    fun providePresenter(
        viewProxy: HomeContract.ViewProxy,
        repository: HomeContract.Repository,
        lifecycleOwner: LifecycleOwner
    ): HomeContract.Presenter = HomePresenter(viewProxy, repository, lifecycleOwner)
}
