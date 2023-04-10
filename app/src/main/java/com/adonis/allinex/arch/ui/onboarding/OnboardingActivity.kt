package com.adonis.allinex.arch.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.adonis.allinex.R
import com.adonis.allinex.util.UserPreferences
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
@AndroidEntryPoint
class OnboardingActivity : AppIntro2() {

    @Inject
    lateinit var userPreferences: UserPreferences

    companion object{
        fun startActivity(context : Context){
            context.startActivity(Intent(context, OnboardingActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransformer(AppIntroPageTransformerType.Fade)
        setImmersiveMode()
        isSystemBackButtonLocked = true
        isSkipButtonEnabled = false
        setIndicatorColor(getColor(R.color.teal_200) , getColor(R.color.black))

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.onboarding_1))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.onboarding_2))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.onboarding_3))
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        lifecycleScope.launch {
            userPreferences.setIsUserNew(false)
        }
        finish()
    }
}