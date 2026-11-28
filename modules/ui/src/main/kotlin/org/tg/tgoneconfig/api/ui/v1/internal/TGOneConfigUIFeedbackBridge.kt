package org.tg.tgoneconfig.api.ui.v1.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.InternalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
public interface TGOneConfigUIFeedbackBridge {
    @Composable
    public fun NotificationsCenter(modifier: Modifier)
}

@ExperimentalTGOneConfigUiApi
private val loadedFeedbackBridge: TGOneConfigUIFeedbackBridge? by lazy {
    loadBridge(TGOneConfigUIFeedbackBridge::class.java, "the notification centre panel stays empty")
}

@ExperimentalTGOneConfigUiApi
internal fun feedbackBridge(): TGOneConfigUIFeedbackBridge? = loadedFeedbackBridge
