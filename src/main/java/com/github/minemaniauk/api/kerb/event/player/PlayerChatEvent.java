/*
 * MineManiaAPI
 * Used for interacting with the database and message broker.
 *
 * Copyright (C) 2023  MineManiaUK Staff
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.minemaniauk.api.kerb.event.player;

import com.github.minemaniauk.api.user.MineManiaUser;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a player chat event.
 * Called when a player sends a message on a server.
 * This will contain the message formatted.
 */
public class PlayerChatEvent implements PlayerEvent {

    private final @NotNull MineManiaUser user;
    private final @NotNull String message;

    /**
     * Used to create a player chat event.
     *
     * @param user The instance of the user that sent the message.
     * @param message The instance of the message.
     */
    public PlayerChatEvent(@NotNull MineManiaUser user, @NotNull String message) {
        this.user = user;
        this.message = message;
    }

    @Override
    public @NotNull MineManiaUser getUser() {
        return this.user;
    }

    /**
     * Used to get the instance of the
     * message formatted.
     *
     * @return The instance of the
     * message formatted.
     */
    public @NotNull String getMessageFormatted() {
        return this.message;
    }
}
