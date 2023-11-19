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

package com.github.minemaniauk.api.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a mine mania user on the server.
 */
public class MineManiaUser {

    private final @NotNull UUID uuid;
    private final @NotNull String name;

    /**
     * Used to create a mine mania user.
     *
     * @param uuid The players minecraft uuid.
     * @param name The players minecraft name.
     */
    public MineManiaUser(@NotNull UUID uuid, @NotNull String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * There minecraft unique id.
     *
     * @return There minecraft unique id.
     */
    public @NotNull UUID getUniqueId() {
        return this.uuid;
    }

    /**
     * There minecraft name.
     *
     * @return There minecraft name.
     */
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * Used to get the possible actions available for the user.
     *
     * @return The instance of the actions.
     */
    public @NotNull MineManiaUserActionSet getActions() {
        return new MineManiaUserActionSet(this);
    }
}
