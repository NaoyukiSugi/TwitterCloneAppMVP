package com.example.twittercloneappmvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twittercloneappmvp.home.contract.HomeContract
import com.example.twittercloneappmvp.home.presenter.HomePresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        presenter.getHomeTimeline()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
