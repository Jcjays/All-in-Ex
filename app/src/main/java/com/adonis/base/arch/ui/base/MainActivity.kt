package com.adonis.base.arch.ui.base

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.adonis.base.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }

    private val navController by lazy { navHostFragment.navController }

    private val appBarConfig by lazy { AppBarConfiguration(setOf(R.id.dashboard), fallbackOnNavigateUpListener = ::onSupportNavigateUp) }


    override fun getLayoutResourceId() = R.layout.activity_main

    override fun initViews() {}

}