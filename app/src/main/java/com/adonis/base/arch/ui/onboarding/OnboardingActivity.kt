package com.adonis.base.arch.ui.onboarding

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.adonis.base.R
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType

@Suppress("SpellCheckingInspection")
class OnboardingActivity : AppIntro2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTransformer(AppIntroPageTransformerType.Fade)
        setImmersiveMode()
        isSystemBackButtonLocked = true
        isSkipButtonEnabled = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setIndicatorColor(getColor(R.color.teal_200) , getColor(R.color.black))
        }

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.onboarding_1))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.onboarding_2))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.onboarding_3))
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
    }
}