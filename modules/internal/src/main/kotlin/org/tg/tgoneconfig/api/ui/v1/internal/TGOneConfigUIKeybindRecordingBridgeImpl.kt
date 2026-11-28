package org.tg.tgoneconfig.api.ui.v1.internal

import org.tg.tgoneconfig.internal.ui.keybind.KeybindRecordingBus

class TGOneConfigUIKeybindRecordingBridgeImpl : TGOneConfigUIKeybindRecordingBridge {
    override fun startRecording(onEscape: () -> Unit, onMouse: (button: Int, pressed: Boolean) -> Unit) {
        KeybindRecordingBus.setEscapeHandler(onEscape)
        KeybindRecordingBus.setMouseHandler(onMouse)
    }

    override fun stopRecording(onEscape: () -> Unit, onMouse: (button: Int, pressed: Boolean) -> Unit) {
        KeybindRecordingBus.clearEscapeHandler(onEscape)
        KeybindRecordingBus.clearMouseHandler(onMouse)
    }

    override val isRecording: Boolean
        get() = KeybindRecordingBus.isRecording
}
