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

import com.github.kerbity.kerb.packet.event.SettableEvent;
import com.github.minemaniauk.api.user.MineManiaUser;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a user action is online event.
 * The settable event boolean is used to set
 * weather or not they are on the server.
 */
public class UserActionIsOnlineEvent extends SettableEvent<Boolean> implements UserActionEvent {

    private final @NotNull MineManiaUser user;

    /**
     * An event used to check if a player is online.
     *
     * @param user The instance of the user to check.
     */
    public UserActionIsOnlineEvent(@NotNull MineManiaUser user) {
        this.user = user;
    }

    @Override
    public @NotNull MineManiaUser getUser() {
        return this.user;
    }
}
