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
import com.github.minemaniauk.api.MineManiaLocation;
import com.github.minemaniauk.api.user.MineManiaUser;
import org.jetbrains.annotations.NotNull;

/**
 * Used to teleport a player to a server and a
 * location within the server.
 */
public class UserActionTeleportEvent extends CompletableEvent implements UserActionEvent {

    private final @NotNull MineManiaUser user;
    private final @NotNull MineManiaLocation location;

    /**
     * Used to create the event with the
     * instance of a single string.
     *
     * @param user     The instance of the user.
     * @param location The location to teleport to.
     */
    public UserActionTeleportEvent(@NotNull MineManiaUser user, @NotNull MineManiaLocation location) {
        this.user = user;
        this.location = location;
    }

    @Override
    public @NotNull MineManiaUser getUser() {
        return this.user;
    }

    /**
     * Used to get the instance of the location.
     *
     * @return The instance of the location.
     */
    public @NotNull MineManiaLocation getLocation() {
        return this.location;
    }
}
