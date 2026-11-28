package org.tg.tgoneconfig.api.ui.v1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import org.apache.logging.log4j.LogManager
import org.tg.tgoneconfig.api.config.v1.Property

@ExperimentalTGOneConfigUiApi
public abstract class SettingRowDecorator {
    @Composable
    public abstract fun Decorate(property: Property<*>, content: @Composable () -> Unit)
}

@ExperimentalTGOneConfigUiApi
public object SettingRowDecorators {
    private val LOGGER = LogManager.getLogger("TG OneConfig/SettingRowDecorators")
    private val lock = Any()

    private var entries by mutableStateOf(emptyList<SettingRowDecorator>())

    @JvmStatic
    public fun register(entry: SettingRowDecorator) {
        synchronized(lock) {
            if (entry in entries) {
                LOGGER.debug("Ignoring SettingRowDecorator {} that is already registered", entry.javaClass.name)
                return
            }
            Snapshot.withMutableSnapshot { entries = entries + entry }
        }
    }

    @JvmStatic
    public fun unregister(entry: SettingRowDecorator): Boolean = synchronized(lock) {
        if (entry !in entries) return false
        Snapshot.withMutableSnapshot { entries = entries - entry }
        true
    }

    @JvmStatic
    public fun all(): List<SettingRowDecorator> = entries
}
