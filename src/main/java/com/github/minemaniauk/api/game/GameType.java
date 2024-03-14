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

package com.github.minemaniauk.api.game;

import org.jetbrains.annotations.NotNull;

/**
 * Represents all the types of games.
 */
public enum GameType {
    TNT_RUN("Tnt Run", "tnt run", "TNT"),
    BED_WARS("Bed Wars", "bed wars", "RED_BED"),
    SPLEEF("Spleef", "spleef", "IRON_SHOVEL"),
    HIDE_AND_SEEK("Hide and Seek", "hide and seek", "CRAFTING_TABLE"),
    TOWER_DEFENCE("Tower Defence", "tower defence", "IRON_AXE");

    private final @NotNull String title;
    private final @NotNull String name;
    private final @NotNull String materialIdentifier;

    GameType(@NotNull String title, @NotNull String name, @NotNull String materialIdentifier) {
        this.title = title;
        this.name = name;
        this.materialIdentifier = materialIdentifier;
    }

    /**
     * Used to convert a material identifier
     * into the material class.
     *
     * @param <T> The material class.
     */
    public interface Converter<T> {

        /**
         * Used to convert a material identifier to a material.
         *
         * @param materialIdentifier The material's identifier.
         * @return The instance of the material.
         */
        @NotNull T convert(@NotNull String materialIdentifier);
    }

    public @NotNull String getTitle() {
        return this.title;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public @NotNull String getMaterialIdentifier() {
        return this.materialIdentifier;
    }

    /**
     * Used to get the instance of the material.
     *
     * @param converter The material converter instance.
     * @param <T>       The material class.
     * @return The instance of the material.
     */
    public @NotNull <T> T getMaterial(@NotNull Converter<T> converter) {
        return converter.convert(this.materialIdentifier);
    }
}
