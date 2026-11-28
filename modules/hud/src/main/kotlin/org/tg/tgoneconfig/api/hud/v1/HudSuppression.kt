@file:OptIn(ExperimentalTGOneConfigUiApi::class)
@file:JvmName("HudSuppression")

package org.tg.tgoneconfig.api.hud.v1

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
public fun Hud.suppress(otherHudId: String, reason: String): AutoCloseable = HudManager.addSuppression(this, otherHudId, reason)
