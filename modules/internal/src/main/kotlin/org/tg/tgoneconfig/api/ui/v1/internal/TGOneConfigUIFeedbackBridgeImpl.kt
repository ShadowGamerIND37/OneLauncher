package org.tg.tgoneconfig.api.ui.v1.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.tg.tgoneconfig.internal.ui.components.NotificationsCenter as InternalNotificationsCenter

class TGOneConfigUIFeedbackBridgeImpl : TGOneConfigUIFeedbackBridge {
    @Composable
    override fun NotificationsCenter(modifier: Modifier) {
        Box(modifier) { InternalNotificationsCenter() }
    }
}
