/*
 * This file is part of TGOneConfig.
 * TGOneConfig - Next Generation Config Library for Minecraft: Java Edition
 * Copyright (C) 2021~2024 TG.
 *   <https://github.com/ShadowGamerIND37> <https://github.com/TG/>
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *   TGOneConfig is licensed under the terms of version 3 of the GNU Lesser
 * General Public License as published by the Free Software Foundation, AND
 * under the Additional Terms Applicable to TGOneConfig, as published by TG,
 * either version 1.0 of the Additional Terms, or (at your option) any later
 * version.
 *
 *   This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 * License.  If not, see <https://www.gnu.org/licenses/>. You should
 * have also received a copy of the Additional Terms Applicable
 * to TGOneConfig, as published by TG. If not, see
 * <https://github.com/ShadowGamerIND37/legal/tgoneconfig/additional-terms>
 */

package org.tg.tgoneconfig.api.ui.v1.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

/**
 * API for native file dialogs and message boxes
 * <p>
 * Backed by TinyFD before Minecraft 26.3 and by SDL from then on
 * <p>
 * Every method blocks the calling thread until the dialog is closed so call them from a background thread
 * <br>File dialogs leave the render thread free meanwhile whereas message boxes on Minecraft 26.3 and above
 * block it until they are closed
 */
@SuppressWarnings("unused")
public interface TinyFdApi {
    static TinyFdApi getInstance() {
        return TinyFdHolder.INSTANCE;
    }

    String QUESTION_ICON = "question";
    String ERROR_ICON = "error";
    String WARNING_ICON = "warning";
    String INFO_ICON = "info";

    String OK_DIALOG = "ok";
    String OK_CANCEL_DIALOG = "okcancel";
    String YES_NO_DIALOG = "yesno";
    String YES_NO_CANCEL_DIALOG = "yesnocancel";

    /**
     * Open a save file selection prompt
     * <p>
     * Same as {@link #openFileSelector(String, String, String[], String)} but says save instead of open
     */
    Path openSaveSelector(@Nullable String title, @Nullable String defaultFilePath, String[] filterPatterns, @Nullable String filterDescription);

    /**
     * Open a file selection prompt
     *
     * @param title             the title of the prompt
     * @param defaultFilePath   the path to the default file to select
     * @param filterPatterns    the file extensions to filter by such as new String[]{"*.png", "*.jpg"}
     * @param filterDescription the description for said filter such as "Images"
     * @return the selected file or null if the user cancelled
     */
    @Nullable
    Path openFileSelector(@Nullable String title, @Nullable String defaultFilePath, String[] filterPatterns, @Nullable String filterDescription);

    /**
     * Open a multi file selection prompt
     * <p>
     * Same as {@link #openFileSelector(String, String, String[], String)} but allows the user to select multiple files
     */
    Path[] openMultiFileSelector(@Nullable String title, @Nullable String defaultFilePath, String[] filterPatterns, @Nullable String filterDescription);

    /**
     * Open a folder selection prompt
     * <p>
     * Same as {@link #openFileSelector(String, String, String[], String)} but allows the user to select a folder
     */
    Path openFolderSelector(@Nullable String title, @Nullable String defaultFolderPath);

    /**
     * Shows a message box
     *
     * @param message      the message which may contain \n and \t
     * @param dialog       the type of message box to show
     *                     <ul><li>{@link #OK_DIALOG}</li><li>{@link #OK_CANCEL_DIALOG}</li><li>{@link #YES_NO_DIALOG}</li><li>{@link #YES_NO_CANCEL_DIALOG}</li></ul>
     * @param icon         the icon to use
     *                     <ul><li>{@link #QUESTION_ICON}</li><li>{@link #ERROR_ICON}</li><li>{@link #WARNING_ICON}</li><li>{@link #INFO_ICON}</li></ul>
     * @param defaultValue the default value to return if the user closes the dialog without clicking a button
     * @return true if the user clicked the "ok" or "yes" button <br>false for "cancel" or "no"
     */
    boolean showMessageBox(String title, String message, @NotNull String dialog, String icon, boolean defaultValue);

    /**
     * Shows a notification
     *
     * @param icon the icon to use
     *             <ul><li>{@link #QUESTION_ICON}</li><li>{@link #ERROR_ICON}</li><li>{@link #WARNING_ICON}</li><li>{@link #INFO_ICON}</li></ul>
     * @return 0 if the user clicked the "ok" button <br>1 for "cancel"
     */
    int showNotification(String title, String message, String icon);
}
