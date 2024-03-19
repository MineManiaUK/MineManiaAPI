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

import org.jetbrains.annotations.NotNull;

/**
 * Represents a location in a world on a server.
 */
public class MineManiaLocation {

    private final @NotNull String serverName;
    private final @NotNull String worldName;
    private final double x;
    private final double y;
    private final double z;

    /**
     * Used to create a new instance of a location.
     *
     * @param serverName The name of the server the location is in.
     * @param worldName  The name of the world the location is in.
     * @param x          The x cord.
     * @param y          The y cord.
     * @param z          The z cord.
     */
    public MineManiaLocation(@NotNull String serverName, @NotNull String worldName, double x, double y, double z) {
        this.serverName = serverName;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Location converter indicator.
     *
     * @param <T> The type of location it can convert to.
     */
    public interface LocationConverter<T> {

        /**
         * Used to convert the location type to a mine mania location.
         *
         * @param location The instance of the location.
         * @return The mine mania location.
         */
        @NotNull MineManiaLocation getMineManiaLocation(@NotNull T location);

        /**
         * Used to convert the mine mania location to a location type.
         *
         * @param location The instance of the mine mania location.
         * @return The instance of the location type.
         */
        @NotNull T getLocationType(@NotNull MineManiaLocation location);
    }

    public @NotNull String getServerName() {
        return this.serverName;
    }

    public @NotNull String getWorldName() {
        return this.worldName;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    /**
     * Used to get the instance of the location type.
     *
     * @param converter The instance of the converter.
     * @param <T>       The type of location.
     * @return The instance of the location type.
     */
    public @NotNull <T> T getLocation(@NotNull LocationConverter<T> converter) {
        return converter.getLocationType(this);
    }
}
