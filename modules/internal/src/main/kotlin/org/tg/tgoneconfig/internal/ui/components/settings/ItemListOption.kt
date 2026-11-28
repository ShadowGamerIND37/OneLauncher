package org.tg.tgoneconfig.internal.ui.components.settings

import androidx.compose.runtime.Composable
import org.tg.tgoneconfig.internal.ui.api.settings.ItemListOptionData
import org.tg.tgoneconfig.internal.ui.components.item.ItemIcon
import org.tg.tgoneconfig.internal.ui.components.item.ItemStrings
import org.tg.tgoneconfig.internal.ui.components.item.ItemTileIcon
import org.tg.tgoneconfig.internal.ui.components.item.rememberItemCatalog

@Composable
fun ItemListOption(data: ItemListOptionData) = TileListOption(
    data,
    rememberItemCatalog(data.prop),
    ItemTileIcon,
    titleKey = ItemStrings.Title,
    searchPlaceholderKey = ItemStrings.Search,
    emptyKey = ItemStrings.Empty,
    unavailableKey = ItemStrings.Unavailable,
    rowIcon = { id, _, modifier -> ItemIcon(id, modifier) },
)
