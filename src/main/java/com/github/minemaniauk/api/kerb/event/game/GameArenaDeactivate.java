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
 * Represents the game arena deactivated.
 * This is fired when an arena has been deactivated.
 * This api will listen to this event and check if
 * the arena is on this server. If the arena is on this server
 * it will call {@link Arena#deactivate()}.
 */
public class GameArenaDeactivate extends Event implements GameEvent {

    private final @NotNull UUID arenaIdentifier;

    /**
     * Used to create a game arena deactivate event.
     *
     * @param arenaIdentifier The arena identifier to deactivate.
     */
    public GameArenaDeactivate(@NotNull UUID arenaIdentifier) {
        this.arenaIdentifier = arenaIdentifier;
    }

    /**
     * Used to get the arena's identifier.
     *
     * @return The arena's identifier.
     */
    public @NotNull UUID getArenaIdentifier() {
        return this.arenaIdentifier;
    }
}
