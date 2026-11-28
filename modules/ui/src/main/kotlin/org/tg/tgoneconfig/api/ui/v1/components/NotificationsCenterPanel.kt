@file:OptIn(ExperimentalTGOneConfigUiApi::class)

package org.tg.tgoneconfig.api.ui.v1.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.internal.feedbackBridge

@ExperimentalTGOneConfigUiApi
@Composable
public fun NotificationsCenterPanel(modifier: Modifier = Modifier) {
    feedbackBridge()?.NotificationsCenter(modifier)
}
