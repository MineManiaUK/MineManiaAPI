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

package com.github.minemaniauk.api;

import com.github.minemaniauk.api.kerb.EventManager;
import com.github.minemaniauk.api.kerb.event.ServerMailEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserActionSet {

    private final @NotNull MineManiaUser user;

    public UserActionSet(@NotNull MineManiaUser user) {
        this.user = user;
    }

    public @NotNull UserActionSet sendMessage(@NotNull String message) {
        EventManager.get().callEvent(new ServerMailEvent(
                this.user, message
        ));
        return this;
    }

    public @NotNull UserActionSet sendMessage(@NotNull List<String> message) {
        EventManager.get().callEvent(new ServerMailEvent(
                this.user, message
        ));
        return this;
    }

//
//    boolean isOnline();
//
//    boolean isVanished();
//
//    boolean hasPermission(@NotNull String permission);
//
//    boolean hasPermission(@NotNull List<String> permissionList);
}
