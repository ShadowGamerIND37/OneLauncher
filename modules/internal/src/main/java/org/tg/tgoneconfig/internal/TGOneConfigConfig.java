package org.tg.tgoneconfig.internal;

import kotlin.jvm.functions.Function1;
import org.tg.tgoneconfig.api.config.v1.Config;
import org.tg.tgoneconfig.api.config.v1.ConfigManager;
import org.tg.tgoneconfig.api.config.v1.Node;
import org.tg.tgoneconfig.api.config.v1.Property;
import org.tg.tgoneconfig.api.config.v1.Tree;
import org.tg.tgoneconfig.api.config.v1.annotations.Dropdown;
import org.tg.tgoneconfig.api.config.v1.annotations.Include;
import org.tg.tgoneconfig.api.config.v1.annotations.Keybind;
import org.tg.tgoneconfig.api.config.v1.annotations.Number;
import org.tg.tgoneconfig.api.config.v1.annotations.Slider;
import org.tg.tgoneconfig.api.config.v1.annotations.Switch;
import org.tg.tgoneconfig.api.hud.v1.HudManager;
import org.tg.tgoneconfig.api.platform.v1.Keys;
import org.tg.tgoneconfig.api.platform.v1.Platform;
import org.tg.tgoneconfig.api.ui.v1.keybind.BindNotInScreen;
import org.tg.tgoneconfig.api.ui.v1.keybind.KeyModifiers;
import org.tg.tgoneconfig.api.ui.v1.keybind.KeyPressMode;
import org.tg.tgoneconfig.api.ui.v1.keybind.KeybindManager;
import org.tg.tgoneconfig.api.ui.v1.keybind.KeybindUtils;
import org.tg.tgoneconfig.api.ui.v1.keybind.TGOneConfigKeybind;
import org.tg.tgoneconfig.internal.ui.hud.screens.HudDesignSession;
import org.tg.tgoneconfig.internal.ui.shell.ShellState;
import org.tg.tgoneconfig.internal.ui.sound.UiSounds;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class TGOneConfigConfig extends Config {
    private static final Keys KEYS = Platform.compatibility().keys();
    private static final byte HUD_ACTION_MODS = KeybindUtils.getActionModifier();
    private static final byte HUD_REDO_MODS = (byte) (HUD_ACTION_MODS | KeyModifiers.SHIFT);

    // the open action comes from the minecraft module via setOpenAction since it cannot be serialized
    @Keybind(
        title = "tgoneconfig.preferences.keybind.title",
        subcategory = "tgoneconfig.preferences.category.gui",
        description = "tgoneconfig.preferences.keybind.description"
    )
    public static TGOneConfigKeybind oneConfigKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyRightShift()}, null, KeyModifiers.NONE, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.minimal_keybind.title",
        subcategory = "tgoneconfig.preferences.category.gui",
        description = "tgoneconfig.preferences.minimal_keybind.description"
    )
    public static TGOneConfigKeybind minimalMenuKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyBackslash()}, null, KeyModifiers.NONE, 0L, pressed -> true);

    @Switch(
        title = "tgoneconfig.preferences.keybind_closes_gui.title",
        subcategory = "tgoneconfig.preferences.category.gui",
        description = "tgoneconfig.preferences.keybind_closes_gui.description"
    )
    public static boolean keybindClosesGui = false;

    @Switch(
        title = "tgoneconfig.preferences.hud_first.title",
        subcategory = "tgoneconfig.preferences.category.gui",
        description = "tgoneconfig.preferences.hud_first.description"
    )
    public static boolean hudFirst = false;

    @Keybind(
        title = "tgoneconfig.preferences.hud_settings_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_settings_keybind.description"
    )
    public static TGOneConfigKeybind hudSettingsKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyE()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_visibility_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_visibility_keybind.description"
    )
    public static TGOneConfigKeybind hudVisibilityKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyH()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_lock_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_lock_keybind.description"
    )
    public static TGOneConfigKeybind hudLockKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyL()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_copy_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_copy_keybind.description"
    )
    public static TGOneConfigKeybind hudCopyKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyC()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_cut_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_cut_keybind.description"
    )
    public static TGOneConfigKeybind hudCutKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyX()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_paste_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_paste_keybind.description"
    )
    public static TGOneConfigKeybind hudPasteKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyV()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_duplicate_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_duplicate_keybind.description"
    )
    public static TGOneConfigKeybind hudDuplicateKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyD()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_reset_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_reset_keybind.description"
    )
    public static TGOneConfigKeybind hudResetKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyR()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_delete_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_delete_keybind.description"
    )
    public static TGOneConfigKeybind hudDeleteKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyDelete()}, null, KeyModifiers.NONE, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_select_all_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_select_all_keybind.description"
    )
    public static TGOneConfigKeybind hudSelectAllKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyA()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_undo_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_undo_keybind.description"
    )
    public static TGOneConfigKeybind hudUndoKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyZ()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_redo_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_redo_keybind.description"
    )
    public static TGOneConfigKeybind hudRedoKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyZ()}, null, HUD_REDO_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_redo_alt_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_redo_alt_keybind.description"
    )
    public static TGOneConfigKeybind hudRedoAltKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyY()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_shortcuts_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_shortcuts_keybind.description"
    )
    public static TGOneConfigKeybind hudShortcutsKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyF1()}, null, KeyModifiers.NONE, 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.hud_shortcuts_alt_keybind.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_shortcuts_alt_keybind.description"
    )
    public static TGOneConfigKeybind hudShortcutsAltKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeySlash()}, null, HUD_ACTION_MODS, 0L, pressed -> true);

    @Switch(
        title = "tgoneconfig.preferences.hud_show_keybind_hints.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_show_keybind_hints.description"
    )
    public static boolean showKeybindHints = true;

    @Switch(
        title = "tgoneconfig.preferences.hud_marquee_anim.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        description = "tgoneconfig.preferences.hud_marquee_anim.description"
    )
    public static boolean marqueeSelectionAnim = true;

    @Slider(
        title = "tgoneconfig.preferences.hud_marquee_anim_duration.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        min = 50f,
        max = 500f,
        step = 10f,
        description = "tgoneconfig.preferences.hud_marquee_anim_duration.description"
    )
    public static float marqueeSelectionAnimDuration = 150f;

    @Keybind(
        title = "tgoneconfig.preferences.hud_toggle_all_keybind.title",
        subcategory = "tgoneconfig.preferences.category.huds",
        description = "tgoneconfig.preferences.hud_toggle_all_keybind.description"
    )
    public static TGOneConfigKeybind hudToggleAllKeybind =
        new TGOneConfigKeybind(null, null, KeyModifiers.NONE, 0L, pressed -> true);

    @Dropdown(
        title = "tgoneconfig.preferences.hud_toggle_all_mode.title",
        subcategory = "tgoneconfig.preferences.category.huds",
        options = {
            "tgoneconfig.preferences.key_mode.held",
            "tgoneconfig.preferences.key_mode.toggle",
            "tgoneconfig.preferences.key_mode.quick_press"
        },
        description = "tgoneconfig.preferences.hud_toggle_all_mode.description"
    )
    public static KeyPressMode hudToggleAllKeyMode = KeyPressMode.Held;

    @Slider(
        title = "tgoneconfig.preferences.hud_toggle_all_hold_threshold.title",
        subcategory = "tgoneconfig.preferences.category.huds",
        min = 50f,
        max = 1000f,
        step = 50f,
        description = "tgoneconfig.preferences.hud_toggle_all_hold_threshold.description"
    )
    public static float hudToggleAllHoldThreshold = 300f;

    @Slider(
        title = "tgoneconfig.preferences.hud_drag_ui_opacity.title",
        subcategory = "tgoneconfig.preferences.category.hud_editor",
        min = 0f,
        max = 100f,
        step = 5f,
        description = "tgoneconfig.preferences.hud_drag_ui_opacity.description"
    )
    public static float hudDragUiOpacity = 60f;

    @Switch(
        title = "tgoneconfig.preferences.pause_game.title",
        subcategory = "tgoneconfig.preferences.category.gui",
        description = "tgoneconfig.preferences.pause_game.description"
    )
    public static boolean pauseGame = false;

    @Dropdown(
        title = "tgoneconfig.preferences.remember_last_page.title",
        subcategory = "tgoneconfig.preferences.category.gui",
        options = {
            "tgoneconfig.preferences.remember_last_page.never",
            "tgoneconfig.preferences.remember_last_page.15_seconds",
            "tgoneconfig.preferences.remember_last_page.1_minute",
            "tgoneconfig.preferences.remember_last_page.5_minutes",
            "tgoneconfig.preferences.remember_last_page.always"
        },
        description = "tgoneconfig.preferences.remember_last_page.description"
    )
    public static int rememberLastPage = 1;

    @Include
    private static int openingBehavior = -1;

    @Include
    private static float timeBeforeReset = 15f;

    @Include
    public static boolean hudEditorTourSeen = false;

    @Include
    public static boolean percentMigrated = false;

    @Switch(
        title = "tgoneconfig.preferences.restore_hud_editor.title",
        subcategory = "tgoneconfig.preferences.category.gui",
        description = "tgoneconfig.preferences.restore_hud_editor.description"
    )
    public static boolean restoreHudEditor = false;

    @Switch(
        title = "tgoneconfig.preferences.show_option_action_buttons.title",
        subcategory = "tgoneconfig.preferences.category.gui",
        description = "tgoneconfig.preferences.show_option_action_buttons.description"
    )
    public static boolean showOptionActionButtons = true;

    @Switch(
        title = "tgoneconfig.preferences.instant_search.title",
        subcategory = "tgoneconfig.preferences.category.search",
        description = "tgoneconfig.preferences.instant_search.description"
    )
    public static boolean instantSearch = true;

    @Switch(
        title = "tgoneconfig.preferences.animate_search_results.title",
        subcategory = "tgoneconfig.preferences.category.search",
        description = "tgoneconfig.preferences.animate_search_results.description"
    )
    public static boolean animateSearchResults = false;

    @Keybind(
        title = "tgoneconfig.preferences.search_keybind.title",
        subcategory = "tgoneconfig.preferences.category.search",
        description = "tgoneconfig.preferences.search_keybind.description"
    )
    public static TGOneConfigKeybind searchKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyF()}, null, KeybindUtils.getActionModifier(), 0L, pressed -> true);

    @Keybind(
        title = "tgoneconfig.preferences.palette_keybind.title",
        subcategory = "tgoneconfig.preferences.category.search",
        description = "tgoneconfig.preferences.palette_keybind.description"
    )
    public static TGOneConfigKeybind paletteKeybind =
        new TGOneConfigKeybind(new int[] {KEYS.getKeyK()}, null, KeybindUtils.getActionModifier(), 0L, pressed -> true);

    @Number(
        title = "tgoneconfig.preferences.search_distance.title",
        subcategory = "tgoneconfig.preferences.category.search",
        min = 0f,
        max = 10f,
        description = "tgoneconfig.preferences.search_distance.description"
    )
    public static int searchDistance = 2;

    @Switch(
        title = "tgoneconfig.preferences.ui_sounds.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        description = "tgoneconfig.preferences.ui_sounds.description"
    )
    public static boolean enableUISounds = true;

    @Switch(
        title = "tgoneconfig.preferences.menu_sounds.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        description = "tgoneconfig.preferences.menu_sounds.description"
    )
    public static boolean enableUIMenuSounds = true;

    @Switch(
        title = "tgoneconfig.preferences.click_sounds.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        description = "tgoneconfig.preferences.click_sounds.description"
    )
    public static boolean enableUIClickSounds = true;

    @Switch(
        title = "tgoneconfig.preferences.slider_sounds.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        description = "tgoneconfig.preferences.slider_sounds.description"
    )
    public static boolean enableUISliderSounds = true;

    @Switch(
        title = "tgoneconfig.preferences.hud_editor_sounds.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        description = "tgoneconfig.preferences.hud_editor_sounds.description"
    )
    public static boolean enableHudEditorSounds = true;

    @Switch(
        title = "tgoneconfig.preferences.ui_ambience.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        description = "tgoneconfig.preferences.ui_ambience.description"
    )
    public static boolean enableUIAmbience = true;

    @Switch(
        title = "tgoneconfig.preferences.duck_music.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        description = "tgoneconfig.preferences.duck_music.description"
    )
    public static boolean enableUIMusicDucking = true;

    @Slider(
        title = "tgoneconfig.preferences.ui_sound_volume.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        min = 0f,
        max = 100f,
        step = 5f,
        description = "tgoneconfig.preferences.ui_sound_volume.description"
    )
    public static float uiSoundVolume = 100f;

    @Slider(
        title = "tgoneconfig.preferences.ui_ambience_volume.title",
        subcategory = "tgoneconfig.preferences.category.sounds",
        min = 0f,
        max = 100f,
        step = 5f,
        description = "tgoneconfig.preferences.ui_ambience_volume.description"
    )
    public static float uiAmbienceVolume = 100f;

    @Switch(
        title = "tgoneconfig.preferences.show_first_launch_message.title",
        subcategory = "tgoneconfig.preferences.category.general",
        description = "tgoneconfig.preferences.show_first_launch_message.description"
    )
    public static boolean showFirstLaunchMessage = true;

    @Switch(
        title = "tgoneconfig.preferences.profile_switch_toast.title",
        subcategory = "tgoneconfig.preferences.category.general",
        description = "tgoneconfig.preferences.profile_switch_toast.description"
    )
    public static boolean profileSwitchToast = true;

    /**
     * The live instance used to persist programmatic changes such as {@link #markFirstLaunchShown()}
     */
    public static TGOneConfigConfig INSTANCE;

    /**
     * The action run when {@link #oneConfigKeybind} is pressed
     * <p>
     * Held here rather than on the keybind because deserialization rebuilds the keybind with a null action
     */
    private static Function1<Boolean, Boolean> openAction;

    /**
     * The keybind currently registered with the {@link KeybindManager} rebuilt whenever the keys or action change
     */
    private static TGOneConfigKeybind registeredKeybind;

    /**
     * The action run when {@link #minimalMenuKeybind} is pressed and the keybind registered for it
     */
    private static Function1<Boolean, Boolean> minimalOpenAction;
    private static final AtomicReference<TGOneConfigKeybind> registeredMinimalKeybind = new AtomicReference<>();

    /**
     * The keybind registered with the {@link KeybindManager} for duplicating the selected HUD in the design studio
     */
    private static TGOneConfigKeybind registeredHudDuplicateKeybind;

    /**
     * The keybind registered with the {@link KeybindManager} for focusing the search field
     */
    private static TGOneConfigKeybind registeredSearchKeybind;

    private static TGOneConfigKeybind registeredPaletteKeybind;

    /**
     * The keybinds registered with the {@link KeybindManager} for the remaining HUD editor actions
     */
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudSettingsKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudVisibilityKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudResetKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudCopyKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudCutKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudPasteKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudDeleteKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudSelectAllKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudLockKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudUndoKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudRedoKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudRedoAltKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudShortcutsKeybind = new AtomicReference<>();
    private static final AtomicReference<TGOneConfigKeybind>
        registeredHudShortcutsAltKeybind = new AtomicReference<>();

    /**
     * The keybind registered with the {@link KeybindManager} for temporarily hiding every HUD
     */
    private static TGOneConfigKeybind registeredHudToggleAllKeybind;

    public TGOneConfigConfig() {
        super("tgoneconfig.json", "assets/tgoneconfig/brand/tgoneconfig-icon.svg", "TG OneConfig", Category.QOL);
        INSTANCE = this;
    }

    @Override
    protected void initialize(boolean byConfigManager) {
        boolean hadPreferences = false;
        if (tree == null) {
            percentMigrated = false;
            uiSoundVolume = 100f;
            uiAmbienceVolume = 100f;
            hudDragUiOpacity = 60f;
            hadPreferences = Files.exists(ConfigManager.active().getFolder().resolve(id));
        }
        super.initialize(byConfigManager);
        if (tree == null) {
            return;
        }
        migrateOpeningBehavior();
        addDependency(
            "restoreHudEditor",
            "Remember last page",
            () -> rememberLastPage != REMEMBER_NEVER ? Property.Display.SHOWN : Property.Display.DISABLED);
        migrateToPercent(hadPreferences);
        for (String option : SOUND_OPTIONS) hideIf(option, "enableUISounds");
        hideIf("enableUIMusicDucking", "enableUIAmbience");
        hideIf("uiAmbienceVolume", "enableUIAmbience");
        hideIf("marqueeSelectionAnimDuration", "marqueeSelectionAnim");
        for (String option : KEYBINDS_PAGE_ONLY) getProperty(option).addMetadata("keybindsPageOnly", true);
        addCallback(
            "enableUIAmbience", (Boolean v) -> {
                UiSounds.refreshAmbience();
                return false;
            });
        addCallback(
            "enableUIMusicDucking", (Boolean v) -> {
                UiSounds.refreshAmbience();
                return false;
            });
        addCallback(
            "uiAmbienceVolume", (Float v) -> {
                UiSounds.refreshAmbience();
                return false;
            });
        // also refreshed once below because a keybind loaded from disk carries no action
        addCallback(
            "oneConfigKeybind", (TGOneConfigKeybind kb) -> {
                refreshKeybind(kb);
                return false;
            });
        refreshKeybind(oneConfigKeybind);
        addCallback(
            "minimalMenuKeybind", (TGOneConfigKeybind kb) -> {
                refreshMinimalKeybind();
                return false;
            });
        refreshMinimalKeybind();
        addCallback(
            "searchKeybind", (TGOneConfigKeybind kb) -> {
                refreshSearchKeybind();
                return false;
            });
        refreshSearchKeybind();
        addCallback(
            "paletteKeybind", (TGOneConfigKeybind kb) -> {
                refreshPaletteKeybind();
                return false;
            });
        refreshPaletteKeybind();
        addCallback(
            "hudDuplicateKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudDuplicateKeybind();
                return false;
            });
        refreshHudDuplicateKeybind();
        addCallback(
            "hudSettingsKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudSettingsKeybind();
                return false;
            });
        refreshHudSettingsKeybind();
        addCallback(
            "hudVisibilityKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudVisibilityKeybind();
                return false;
            });
        refreshHudVisibilityKeybind();
        addCallback(
            "hudResetKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudResetKeybind();
                return false;
            });
        refreshHudResetKeybind();
        addCallback(
            "hudCopyKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudCopyKeybind();
                return false;
            });
        refreshHudCopyKeybind();
        addCallback(
            "hudCutKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudCutKeybind();
                return false;
            });
        refreshHudCutKeybind();
        addCallback(
            "hudPasteKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudPasteKeybind();
                return false;
            });
        refreshHudPasteKeybind();
        addCallback(
            "hudDeleteKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudDeleteKeybind();
                return false;
            });
        refreshHudDeleteKeybind();
        addCallback(
            "hudSelectAllKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudSelectAllKeybind();
                return false;
            });
        refreshHudSelectAllKeybind();
        addCallback(
            "hudLockKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudLockKeybind();
                return false;
            });
        refreshHudLockKeybind();
        addCallback(
            "hudUndoKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudUndoKeybind();
                return false;
            });
        refreshHudUndoKeybind();
        addCallback(
            "hudRedoKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudRedoKeybind();
                return false;
            });
        refreshHudRedoKeybind();
        addCallback(
            "hudRedoAltKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudRedoAltKeybind();
                return false;
            });
        refreshHudRedoAltKeybind();
        addCallback(
            "hudShortcutsKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudShortcutsKeybind();
                return false;
            });
        refreshHudShortcutsKeybind();
        addCallback(
            "hudShortcutsAltKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudShortcutsAltKeybind();
                return false;
            });
        refreshHudShortcutsAltKeybind();
        addCallback(
            "hudToggleAllKeybind", (TGOneConfigKeybind kb) -> {
                refreshHudToggleAllKeybind();
                return false;
            });
        refreshHudToggleAllKeybind();
        addCallback(
            "hudToggleAllKeyMode", (KeyPressMode mode) -> {
                HudManager.resetAllHudsKey();
                return false;
            });
        addDependency(
            "hudToggleAllHoldThreshold",
            "Mode",
            () -> hudToggleAllKeyMode == KeyPressMode.QuickPress ? Property.Display.SHOWN : Property.Display.DISABLED);
    }

    /**
     * Rebuilds the keybind which temporarily hides every HUD
     * <p>
     * Built as a {@link BindNotInScreen} so it never fires while a screen is open
     */
    private static void refreshHudToggleAllKeybind() {
        if (registeredHudToggleAllKeybind != null) {
            KeybindManager.unregister(registeredHudToggleAllKeybind);
            registeredHudToggleAllKeybind = null;
        }
        HudManager.resetAllHudsKey();
        TGOneConfigKeybind src = hudToggleAllKeybind;
        if (src == null || !src.isBound()) {
            return;
        }
        registeredHudToggleAllKeybind = KeybindManager.register(new BindNotInScreen(
            src.getKeyCodes(),
            src.getMouseBtns(),
            src.getMods(),
            0L,
            pressed -> {
                HudManager.allHudsKeyInput(pressed, hudToggleAllKeyMode, (long) hudToggleAllHoldThreshold);
                return true;
            }));
    }

    private static final String[] SOUND_OPTIONS = {"enableUIMenuSounds", "enableUIClickSounds", "enableUISliderSounds", "enableHudEditorSounds", "uiSoundVolume"};

    private static final String[] KEYBINDS_PAGE_ONLY = {
        "searchKeybind", "paletteKeybind", "hudSettingsKeybind", "hudVisibilityKeybind", "hudLockKeybind", "hudCopyKeybind", "hudCutKeybind",
        "hudPasteKeybind", "hudDuplicateKeybind", "hudResetKeybind", "hudDeleteKeybind", "hudSelectAllKeybind",
        "hudUndoKeybind", "hudRedoKeybind", "hudRedoAltKeybind", "hudShortcutsKeybind", "hudShortcutsAltKeybind"
    };

    private void migrateToPercent(boolean hadPreferences) {
        if (percentMigrated) return;
        Float saved;
        Float legacy;
        try {
            saved = hadPreferences ? storedNumber(id, "hudDragUiOpacity") : null;
            legacy = storedNumber("themes.json", "hudDragUiOpacity");
        } catch (Exception e) {
            return;
        }
        if (saved == null || saved <= 1f) {
            if (legacy != null) hudDragUiOpacity = toPercent(legacy);
            else if (saved != null) hudDragUiOpacity = toPercent(saved);
        }
        uiSoundVolume = toPercent(uiSoundVolume);
        uiAmbienceVolume = toPercent(uiAmbienceVolume);
        percentMigrated = true;
        save();
    }

    private static float toPercent(float value) {
        return value <= 1f ? value * 100f : value;
    }

    private static Float storedNumber(String file, String key) throws Exception {
        Path path = ConfigManager.active().getFolder().resolve(file);
        if (!Files.exists(path)) return null;
        Tree stored = ConfigManager.active().getNoRegister(path);
        Node value = stored == null ? null : stored.get(key);
        if (!(value instanceof Property)) return null;
        Object raw = ((Property<?>) value).getAs();
        return raw instanceof java.lang.Number ? ((java.lang.Number) raw).floatValue() : null;
    }

    private static final int REMEMBER_NEVER = 0;
    private static final int REMEMBER_ALWAYS = 4;
    private static final long[] REMEMBER_LAST_PAGE_SECONDS = {15L, 60L, 300L};

    public static boolean remembersPageClosedAt(long closedAt) {
        int option = rememberLastPage;
        if (option <= REMEMBER_NEVER) return false;
        if (option >= REMEMBER_ALWAYS) return true;
        return closedAt > 0L && System.currentTimeMillis() - closedAt <= REMEMBER_LAST_PAGE_SECONDS[option - 1] * 1000L;
    }

    private void migrateOpeningBehavior() {
        if (openingBehavior < 0) return;
        switch (openingBehavior) {
            case 2: // previous page
                rememberLastPage = REMEMBER_ALWAYS;
                break;
            case 3: // smart reset, 37.5 is halfway between 15 seconds and 1 minute
                rememberLastPage = timeBeforeReset > 37.5f ? 2 : 1;
                break;
            default: // mods or preferences
                rememberLastPage = REMEMBER_NEVER;
        }
        openingBehavior = -1;
        save();
    }

    /** When the TGOneConfig keybind last closed the GUI so the same press cannot immediately reopen it */
    private static volatile long keybindClosedAt = 0L;

    /** How long a keybind-driven close blocks the open action for */
    private static final long KEYBIND_CLOSE_GRACE_MS = 300L;

    /**
     * Records that the TGOneConfig keybind just closed the GUI
     * <p>
     * The keybind manager evaluates presses at the end of a frame after the screen has been drawn so without this
     * mark the same press would reopen the GUI when the closing animation is disabled
     *
     * @see #consumeKeybindClose()
     */
    public static void notifyKeybindClosedGui() {
        keybindClosedAt = System.currentTimeMillis();
    }

    /**
     * True if the press being handled is the one that just closed the GUI
     * <p>
     * Consumes the mark so only the first press after a close is dropped
     */
    public static boolean consumeKeybindClose() {
        long at = keybindClosedAt;
        if (at == 0L) return false;
        keybindClosedAt = 0L;
        return System.currentTimeMillis() - at <= KEYBIND_CLOSE_GRACE_MS;
    }

    /**
     * Supplies the action run when the TGOneConfig keybind is pressed
     * <p>
     * Called by the minecraft module because the action references platform classes this module cannot depend on
     *
     * @see #openAction
     */
    public static void setOpenAction(Function1<Boolean, Boolean> action) {
        openAction = action;
        refreshKeybind(oneConfigKeybind);
    }

    /**
     * Supplies the action run when {@link #minimalMenuKeybind} is pressed, see {@link #setOpenAction}
     */
    public static void setMinimalOpenAction(Function1<Boolean, Boolean> action) {
        minimalOpenAction = action;
        refreshMinimalKeybind();
    }

    private static void refreshMinimalKeybind() {
        if (minimalOpenAction == null) return;
        refreshHudEditorKeybind(minimalMenuKeybind, registeredMinimalKeybind, minimalOpenAction);
    }

    /**
     * Rebuilds the registered keybind from {@code src}'s GLFW keys plus {@link #openAction} and re-registers it
     * with the {@link KeybindManager}
     */
    private static void refreshKeybind(TGOneConfigKeybind src) {
        if (registeredKeybind != null) {
            KeybindManager.unregister(registeredKeybind);
            registeredKeybind = null;
        }
        if (src == null || openAction == null || !src.isBound()) {
            return;
        }
        registeredKeybind = new TGOneConfigKeybind(
            src.getKeyCodes(),
            src.getMouseBtns(),
            src.getMods(),
            src.getDurationNanos(),
            openAction);
        KeybindManager.register(registeredKeybind);
    }

    private static void refreshPaletteKeybind() {
        if (registeredPaletteKeybind != null) {
            KeybindManager.unregister(registeredPaletteKeybind);
            registeredPaletteKeybind = null;
        }
        TGOneConfigKeybind src = paletteKeybind;
        if (src == null || !src.isBound()) {
            return;
        }
        registeredPaletteKeybind = new TGOneConfigKeybind(
            src.getKeyCodes(),
            src.getMouseBtns(),
            src.getMods(),
            src.getDurationNanos(),
            ShellState::handlePaletteKeybind);
        registeredPaletteKeybind.setFiresWhileTyping(true);
        KeybindManager.register(registeredPaletteKeybind);
    }

    /**
     * Rebuilds the registered search keybind from {@code searchKeybind}'s GLFW keys and re-registers it
     * with the {@link KeybindManager}
     * <p>
     * The action is resolved here rather than stored on the keybind so it survives deserialization, and it fires
     * while typing so it still works with a text field focused
     */
    private static void refreshSearchKeybind() {
        if (registeredSearchKeybind != null) {
            KeybindManager.unregister(registeredSearchKeybind);
            registeredSearchKeybind = null;
        }
        TGOneConfigKeybind src = searchKeybind;
        if (src == null || !src.isBound()) {
            return;
        }
        registeredSearchKeybind = new TGOneConfigKeybind(
            src.getKeyCodes(),
            src.getMouseBtns(),
            src.getMods(),
            src.getDurationNanos(),
            ShellState::handleSearchKeybind);
        registeredSearchKeybind.setFiresWhileTyping(true);
        KeybindManager.register(registeredSearchKeybind);
    }

    /**
     * Rebuilds the registered duplicate-HUD keybind from {@code hudDuplicateKeybind}'s GLFW keys and re-registers it
     * with the {@link KeybindManager}
     * <p>
     * The action is resolved here rather than stored on the keybind so it survives deserialization
     */
    private static void refreshHudDuplicateKeybind() {
        if (registeredHudDuplicateKeybind != null) {
            KeybindManager.unregister(registeredHudDuplicateKeybind);
            registeredHudDuplicateKeybind = null;
        }
        TGOneConfigKeybind src = hudDuplicateKeybind;
        if (src == null || !src.isBound()) {
            return;
        }
        registeredHudDuplicateKeybind = new TGOneConfigKeybind(
            src.getKeyCodes(),
            src.getMouseBtns(),
            src.getMods(),
            src.getDurationNanos(),
            HudDesignSession::handleDuplicate);
        KeybindManager.register(registeredHudDuplicateKeybind);
    }

    /**
     * Rebuilds one of the additional HUD editor keybinds from its config field's GLFW keys and re-registers it
     * with the {@link KeybindManager}
     * <p>
     * The action is resolved here rather than stored on the keybind so it survives deserialization
     */
    private static void refreshHudEditorKeybind(
        TGOneConfigKeybind src,
        AtomicReference<TGOneConfigKeybind> slot,
        Function1<Boolean, Boolean> action) {
        refreshHudEditorKeybind(src, slot, action, false);
    }

    private static void refreshHudEditorKeybind(
        TGOneConfigKeybind src,
        AtomicReference<TGOneConfigKeybind> slot,
        Function1<Boolean, Boolean> action,
        boolean strictShift) {
        TGOneConfigKeybind old = slot.getAndSet(null);
        if (old != null) {
            KeybindManager.unregister(old);
        }
        if (src == null || !src.isBound()) {
            return;
        }
        TGOneConfigKeybind keybind = new TGOneConfigKeybind(
            src.getKeyCodes(),
            src.getMouseBtns(),
            src.getMods(),
            src.getDurationNanos(),
            action) {
            @Override
            public boolean test(Set<Integer> downKeys, Set<Integer> downMouse, byte currentMods) {
                // a keybind matches with extra modifiers held, so Ctrl+Shift+Z would undo as well as redo
                boolean extraShift = (currentMods & KeyModifiers.SHIFT) != 0 && (getMods() & KeyModifiers.SHIFT) == 0;
                return !(strictShift && extraShift) && super.test(downKeys, downMouse, currentMods);
            }
        };
        KeybindManager.register(keybind);
        slot.set(keybind);
    }

    private static void refreshHudSettingsKeybind() {
        refreshHudEditorKeybind(
            hudSettingsKeybind,
            registeredHudSettingsKeybind,
            HudDesignSession::handleOpenSettings);
    }

    private static void refreshHudVisibilityKeybind() {
        refreshHudEditorKeybind(
            hudVisibilityKeybind,
            registeredHudVisibilityKeybind,
            HudDesignSession::handleToggleVisibility);
    }

    private static void refreshHudResetKeybind() {
        refreshHudEditorKeybind(
            hudResetKeybind,
            registeredHudResetKeybind,
            HudDesignSession::handleReset);
    }

    private static void refreshHudCopyKeybind() {
        refreshHudEditorKeybind(
            hudCopyKeybind,
            registeredHudCopyKeybind,
            HudDesignSession::handleCopy);
    }

    private static void refreshHudCutKeybind() {
        refreshHudEditorKeybind(
            hudCutKeybind,
            registeredHudCutKeybind,
            HudDesignSession::handleCut);
    }

    private static void refreshHudPasteKeybind() {
        refreshHudEditorKeybind(
            hudPasteKeybind,
            registeredHudPasteKeybind,
            HudDesignSession::handlePaste);
    }

    private static void refreshHudDeleteKeybind() {
        refreshHudEditorKeybind(
            hudDeleteKeybind,
            registeredHudDeleteKeybind,
            HudDesignSession::handleDelete);
    }

    private static void refreshHudSelectAllKeybind() {
        refreshHudEditorKeybind(
            hudSelectAllKeybind,
            registeredHudSelectAllKeybind,
            HudDesignSession::handleSelectAll);
    }

    private static void refreshHudLockKeybind() {
        refreshHudEditorKeybind(
            hudLockKeybind,
            registeredHudLockKeybind,
                HudDesignSession::handleLock);
    }

    private static void refreshHudUndoKeybind() {
        refreshHudEditorKeybind(
            hudUndoKeybind,
            registeredHudUndoKeybind,
            HudDesignSession::handleUndo,
            true);
    }

    private static void refreshHudRedoKeybind() {
        refreshHudEditorKeybind(
            hudRedoKeybind,
            registeredHudRedoKeybind,
            HudDesignSession::handleRedo);
    }

    private static void refreshHudRedoAltKeybind() {
        refreshHudEditorKeybind(
            hudRedoAltKeybind,
            registeredHudRedoAltKeybind,
            HudDesignSession::handleRedo);
    }

    private static void refreshHudShortcutsKeybind() {
        refreshHudEditorKeybind(
            hudShortcutsKeybind,
            registeredHudShortcutsKeybind,
            HudDesignSession::handleShortcuts);
    }

    private static void refreshHudShortcutsAltKeybind() {
        refreshHudEditorKeybind(
            hudShortcutsAltKeybind,
            registeredHudShortcutsAltKeybind,
            HudDesignSession::handleShortcuts);
    }

    /**
     * Called once after the first-launch message has been shown
     * <p>
     * Turns {@link #showFirstLaunchMessage} off and persists it until the user re-enables it from the menu
     */
    public static void markFirstLaunchShown() {
        if (!showFirstLaunchMessage) {
            return;
        }
        showFirstLaunchMessage = false;
        if (INSTANCE != null && INSTANCE.tree != null) {
            try {
                INSTANCE.getProperty("showFirstLaunchMessage").setAs(Boolean.FALSE);
            } catch (Exception ignored) {
            }
            INSTANCE.save();
        }
    }

    public static void markHudEditorTourSeen() {

        if (hudEditorTourSeen) {
            return;
        }
        hudEditorTourSeen = true;
        if (INSTANCE != null && INSTANCE.tree != null) {
            try {
                INSTANCE.getProperty("hudEditorTourSeen").setAs(Boolean.TRUE);
            } catch (Exception ignored) {
            }
            INSTANCE.save();
        }
    }
}
