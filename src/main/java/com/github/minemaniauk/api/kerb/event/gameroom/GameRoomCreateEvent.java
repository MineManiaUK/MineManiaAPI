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

import com.github.kerbity.kerb.packet.event.Event;
import com.github.minemaniauk.api.MineManiaAPI;
import com.github.minemaniauk.api.database.collection.GameRoomCollection;
import com.github.minemaniauk.api.database.record.GameRoomRecord;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a game room create event.
 * This is sent to all clients when a game room is created.
 */
public class GameRoomCreateEvent extends Event implements GameRoomEvent {

    private final @NotNull UUID gameRoomUuid;

    /**
     * Used to create a game room create event.
     *
     * @param gameRoomUuid The game room's uuid.
     *                     This is used instead of the record identifier to
     *                     avoid security issues when sending infomation.
     */
    public GameRoomCreateEvent(@NotNull UUID gameRoomUuid) {
        this.gameRoomUuid = gameRoomUuid;
    }

    @Override
    public @NotNull GameRoomRecord getGameRoom() {
        return MineManiaAPI.getInstance()
                .getDatabase()
                .getTable(GameRoomCollection.class)
                .getGameRoom(this.gameRoomUuid)
                .orElseThrow();
    }
}
