package org.tg.tgoneconfig.api.ui.v1.internal

import org.tg.tgoneconfig.api.ui.v1.ExperimentalTGOneConfigUiApi
import org.tg.tgoneconfig.api.ui.v1.InternalTGOneConfigUiApi

@ExperimentalTGOneConfigUiApi
@InternalTGOneConfigUiApi
public interface TGOneConfigUIKeybindRecordingBridge {
    public fun startRecording(onEscape: () -> Unit, onMouse: (button: Int, pressed: Boolean) -> Unit)

    public fun stopRecording(onEscape: () -> Unit, onMouse: (button: Int, pressed: Boolean) -> Unit)

    public val isRecording: Boolean
}

@ExperimentalTGOneConfigUiApi
private val loadedKeybindRecordingBridge: TGOneConfigUIKeybindRecordingBridge? by lazy {
    loadBridge(TGOneConfigUIKeybindRecordingBridge::class.java, "keybind fields cannot record mouse buttons and Escape may close the screen while recording")
}

@ExperimentalTGOneConfigUiApi
internal fun keybindRecordingBridge(): TGOneConfigUIKeybindRecordingBridge? = loadedKeybindRecordingBridge
