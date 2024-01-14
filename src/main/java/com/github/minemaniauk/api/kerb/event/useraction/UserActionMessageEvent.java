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

package com.github.minemaniauk.api.kerb.event.useraction;

import com.github.kerbity.kerb.packet.event.CompletableEvent;
import com.github.minemaniauk.api.user.MineManiaUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a user action completable event.
 * This is used to send a message to a player
 * not knowing what server they are on.
 */
public class UserActionMessageEvent extends CompletableEvent implements UserActionEvent {

    private final @NotNull MineManiaUser user;
    private final @NotNull String message;

    /**
     * Used to create the event with the
     * instance of a single string.
     *
     * @param user    The instance of the user.
     * @param message The instance of the message.
     */
    public UserActionMessageEvent(@NotNull MineManiaUser user, @NotNull String message) {
        this.user = user;
        this.message = message;
    }

    /**
     * Used to create the event with the
     * instance of a list of strings.
     * The list of strings will be combined and
     * seperated by a new line character.
     *
     * @param user    The instance of the user.
     * @param message The instance of the message.
     */
    public UserActionMessageEvent(@NotNull MineManiaUser user, @NotNull List<String> message) {
        StringBuilder builder = new StringBuilder();
        message.forEach(item -> builder.append(item).append("\n"));

        this.user = user;
        this.message = builder.substring(0, builder.length() - 1);
    }

    @Override
    public @NotNull MineManiaUser getUser() {
        return this.user;
    }

    /**
     * Used to get the instance of the message.
     *
     * @return The instance of the message.
     */
    public @NotNull String getMessage() {
        return this.message;
    }
}
