package com.javdc.napptiloompas.presentation.util

import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.Transition
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import com.javdc.napptiloompas.presentation.R

fun Fragment.setUpSharedElementEnterTransitionForContainerTransform() {
    sharedElementEnterTransition = MaterialContainerTransform().apply {
        drawingViewId = R.id.mainNavHostFragment
    }
}

fun Fragment.setUpExitAndReenterTransitionsForContainerTransform() {
    exitTransition = MaterialElevationScale(false).apply {
        addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                // no-op
            }

            override fun onTransitionEnd(transition: Transition) {
                exitTransition = null
            }

            override fun onTransitionCancel(transition: Transition) {
                // no-op
            }

            override fun onTransitionPause(transition: Transition) {
                // no-op
            }

            override fun onTransitionResume(transition: Transition) {
                // no-op
            }
        })
    }
    reenterTransition = MaterialFade().apply {
        addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                // no-op
            }

            override fun onTransitionEnd(transition: Transition) {
                reenterTransition = null
            }

            override fun onTransitionCancel(transition: Transition) {
                // no-op
            }

            override fun onTransitionPause(transition: Transition) {
                // no-op
            }

            override fun onTransitionResume(transition: Transition) {
                // no-op
            }
        })
    }
}

fun Fragment.setUpPostponedEnterTransition(view: View) {
    postponeEnterTransition()
    view.doOnPreDraw { startPostponedEnterTransition() }
}