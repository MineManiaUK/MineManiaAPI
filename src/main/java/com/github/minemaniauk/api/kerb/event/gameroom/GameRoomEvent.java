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

package com.github.minemaniauk.api.kerb.event.gameroom;

import com.github.minemaniauk.api.database.record.GameRoomRecord;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a game room event.
 */
public interface GameRoomEvent {

    /**
     * Used to get the instance of the game room.
     * <li>
     * This method uses the database. Be careful with multiple calls.
     * </li>
     *
     * @return The instance of the game room.
     */
    @NotNull GameRoomRecord getGameRoom();
}
