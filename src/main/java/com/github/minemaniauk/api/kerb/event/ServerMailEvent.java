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

package com.github.minemaniauk.api.kerb.event;

import com.github.kerbity.kerb.event.Event;
import com.github.minemaniauk.api.MineManiaUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServerMailEvent implements Event {

    private final @NotNull MineManiaUser user;
    private final @NotNull String message;

    public ServerMailEvent(@NotNull MineManiaUser user, @NotNull String message) {
        this.user = user;
        this.message = message;
    }

    public ServerMailEvent(@NotNull MineManiaUser user, @NotNull List<String> message) {
        StringBuilder builder = new StringBuilder();
        message.forEach(item -> builder.append(item).append("\n"));

        this.user = user;
        this.message = builder.substring(0, builder.length() - 1);
    }

    public @NotNull MineManiaUser getUser() {
        return this.user;
    }

    public @NotNull String getMessage() {
        return this.message;
    }
}
