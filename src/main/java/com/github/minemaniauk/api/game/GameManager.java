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

import com.github.minemaniauk.api.MineManiaAPI;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Represents the instance of the global game manager.
 * Hooks to kerb and the database to sync with other instances.
 */
public class GameManager {

    private final @NotNull MineManiaAPI api;

    /**
     * Used to interact with the game manager.
     *
     * @param api The pointer to the api to sync.
     */
    public GameManager(@NotNull MineManiaAPI api) {
        this.api = api;
    }

    /**
     * Adds the arena to the database to show when it
     * is available, and registers it with this specific plugin.
     *
     * @param arena The instance of the arena.
     * @return This instance.
     */
    public @NotNull GameManager registerArena(@NotNull Arena arena) {

    }

    public @NotNull GameManager unregisterArena(@NotNull UUID identifier) {

    }

    public @NotNull List<Arena> getArenas(@NotNull GameType gameType) {
        return null;
    }

    public @NotNull List<Arena> getAvailableArenas(@NotNull GameType gameType) {
        return null;
    }
}
