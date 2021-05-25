package com.example.twittercloneappmvp.di.module

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.common_api.search_result.SearchResultTimelineApi
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.feature.search_result.fragment.SearchResultFragment
import com.example.twittercloneappmvp.feature.search_result.presenter.SearchResultPresenter
import com.example.twittercloneappmvp.feature.search_result.repository.SearchResultRepository
import com.example.twittercloneappmvp.feature.search_result.view.SearchResultViewProxy
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class SearchResultModule {

    @Provides
    fun provideSearchResultFragment(fragment: Fragment): SearchResultFragment =
        fragment as SearchResultFragment

//    @Provides
//    fun provideLifecycleOwner(fragment: Fragment): LifecycleOwner = fragment

    @Provides
    @Reusable
    fun provideSearchResultRepository(api: SearchResultTimelineApi): SearchResultContract.Repository =
        SearchResultRepository(api)

    @Provides
    @FragmentScoped
    fun provideSearchResultViewProxy(fragment: Fragment): SearchResultContract.ViewProxy =
        SearchResultViewProxy(fragment)

    @Provides
    @FragmentScoped
    fun provideSearchResultPresenter(
        viewProxy: SearchResultContract.ViewProxy,
        repository: SearchResultContract.Repository,
        lifecycleOwner: LifecycleOwner
    ): SearchResultContract.Presenter = SearchResultPresenter(viewProxy, repository, lifecycleOwner)
}
