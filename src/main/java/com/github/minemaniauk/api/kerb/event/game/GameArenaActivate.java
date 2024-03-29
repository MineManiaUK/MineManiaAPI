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

package com.github.minemaniauk.api.kerb.event.game;

import com.github.kerbity.kerb.packet.event.Event;
import com.github.minemaniauk.api.game.Arena;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents the game arena activated.
 * This is fired when an arena has been activated.
 * This api will listen to this event and check if
 * the arena is on this server. If the arena is on this server
 * it will call {@link Arena#activate()}.
 */
public class GameArenaActivate extends Event implements GameEvent {

    private final @NotNull UUID arenaIdentifier;
    private final @NotNull UUID gameRoomIdentifier;

    /**
     * Used to create a game arena activate event.
     *
     * @param arenaIdentifier The arena identifier to activate.
     */
    public GameArenaActivate(@NotNull UUID arenaIdentifier, @NotNull UUID gameRoomIdentifier) {
        this.arenaIdentifier = arenaIdentifier;
        this.gameRoomIdentifier = gameRoomIdentifier;
    }

    /**
     * Used to get the arena's identifier.
     *
     * @return The arena's identifier.
     */
    public @NotNull UUID getArenaIdentifier() {
        return this.arenaIdentifier;
    }

    /**
     * Used to get the game room identifier.
     *
     * @return The game room identifier.
     */
    public @NotNull UUID getGameRoomIdentifier() {
        return this.gameRoomIdentifier;
    }
}
