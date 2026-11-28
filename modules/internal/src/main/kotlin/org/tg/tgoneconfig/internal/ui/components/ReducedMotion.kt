package org.tg.tgoneconfig.internal.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.ui.MotionDurationScale
import org.tg.tgoneconfig.api.ui.v1.TGOneConfigPrefs
import org.tg.tgoneconfig.api.ui.v1.a11y.Motion
import org.tg.tgoneconfig.internal.ui.themes.PrefsSync

object AnimationsMotionScale : MotionDurationScale {
    @JvmStatic
    var animations: Boolean
        get() = TGOneConfigPrefs.animationsEnabled
        set(value) = PrefsSync.animations(value)

    override val scaleFactor: Float get() = if (TGOneConfigPrefs.animationsEnabled) 1f else 0f
}

object ReducedMotion {
    @JvmStatic
    var enabled: Boolean
        get() = Motion.reduced
        set(value) = PrefsSync.reducedMotion(value)

    fun <T> spec(spec: AnimationSpec<T>): AnimationSpec<T> = Motion.spec(spec)

    fun <T> finiteSpec(spec: FiniteAnimationSpec<T>): FiniteAnimationSpec<T> = Motion.finiteSpec(spec)

    fun enter(enter: EnterTransition): EnterTransition = Motion.enter(enter)

    fun exit(exit: ExitTransition): ExitTransition = Motion.exit(exit)
}
