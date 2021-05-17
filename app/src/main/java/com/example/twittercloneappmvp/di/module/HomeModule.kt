package com.example.twittercloneappmvp.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.twittercloneappmvp.HomeFragment
import com.example.twittercloneappmvp.home.contract.HomeContract
import com.example.twittercloneappmvp.home.presenter.HomePresenter
import com.example.twittercloneappmvp.home.repository.HomeRepository
import com.example.twittercloneappmvp.home.view.HomeViewProxy
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(FragmentComponent::class)
interface HomeModule {

    @Binds
    fun bindFragment(fragment: Fragment): HomeFragment

    @Binds
    fun bindLifecycleOwner(fragment: HomeFragment): LifecycleOwner

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
