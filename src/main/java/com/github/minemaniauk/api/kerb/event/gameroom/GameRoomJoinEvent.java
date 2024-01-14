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
import com.github.minemaniauk.api.user.MineManiaUser;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents the game room join event.
 * This is called when a player joins the game room.
 */
public class GameRoomJoinEvent extends Event implements GameRoomEvent {

    private final @NotNull UUID gameRoomUuid;
    private final @NotNull MineManiaUser user;

    /**
     * Used to create a game room join event.
     * This should be executed after they have been added
     * and all the checks are done.
     *
     * @param gameRoomUuid The game room's uuid.
     *                     This is used instead of the record identifier to
     *                     avoid security issues when sending infomation.
     */
    public GameRoomJoinEvent(@NotNull UUID gameRoomUuid, @NotNull MineManiaUser user) {
        this.gameRoomUuid = gameRoomUuid;
        this.user = user;
    }

    @Override
    public @NotNull GameRoomRecord getGameRoom() {
        return MineManiaAPI.getInstance()
                .getDatabase()
                .getTable(GameRoomCollection.class)
                .getGameRoom(this.gameRoomUuid)
                .orElseThrow();
    }

    /**
     * Used to get the user that joined the game room.
     *
     * @return The instance of the user.
     */
    public @NotNull MineManiaUser getUser() {
        return this.user;
    }
}
