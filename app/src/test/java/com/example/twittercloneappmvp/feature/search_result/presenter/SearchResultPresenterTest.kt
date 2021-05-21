package com.example.twittercloneappmvp.feature.search_result.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.twittercloneappmvp.feature.search_result.contract.SearchResultContract
import com.example.twittercloneappmvp.feature.search_result.viewmodel.SearchResultViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import kotlin.test.assertFalse

internal class SearchResultPresenterTest {

    private lateinit var presenter: SearchResultPresenter
    private val viewProxy: SearchResultContract.ViewProxy = mock()
    private val repository: SearchResultContract.Repository = mock()
    private val lifecycle: Lifecycle = mock()
    private val viewModel: SearchResultViewModel = mock()
    private val lifecycleOwner: LifecycleOwner = mock { on { lifecycle } doReturn lifecycle }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        presenter = spy(SearchResultPresenter(viewProxy, repository, viewModel, lifecycleOwner))
    }

    // region onLifecycleEventOnStart
    @Test
    fun `onLifecycleEventOnStart should call initAdapter`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).initAdapter()
    }

    @Test
    fun `onLifecycleEventOnStart should call setOnIconClickListener`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).setOnIconClickListener(presenter)
    }

    @Test
    fun `onLifecycleEventOnStart should call setOnRefreshListener`() {
        presenter.onLifecycleEventOnStart()

        verify(viewProxy).setOnRefreshListener(presenter)
    }
    // endregion

    @Test
    fun `onLifecycleEventOnDestroy should call cancel`() {
        presenter.onLifecycleEventOnDestroy()

        assertFalse(presenter.isActive)
    }
}
