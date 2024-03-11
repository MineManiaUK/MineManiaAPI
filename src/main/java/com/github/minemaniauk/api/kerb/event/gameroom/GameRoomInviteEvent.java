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
import com.github.minemaniauk.api.database.record.GameRoomInviteRecord;
import com.github.minemaniauk.api.database.record.GameRoomRecord;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the game room invite event.
 * This is sent when someone is invited to a game room.
 */
public class GameRoomInviteEvent extends Event implements GameRoomEvent {

    private final @NotNull GameRoomRecord gameRoom;
    private final @NotNull GameRoomInviteRecord gameRoomInvite;

    /**
     * Used to create a new game room invite event.
     *
     * @param gameRoom The instance of the game room invited to.
     * @param gameRoomInvite The instance of the game room invite.
     */
    public GameRoomInviteEvent(@NotNull GameRoomRecord gameRoom, @NotNull GameRoomInviteRecord gameRoomInvite) {
        this.gameRoom = gameRoom;
        this.gameRoomInvite = gameRoomInvite;
    }

    @Override
    public @NotNull GameRoomRecord getGameRoom() {
        return this.gameRoom;
    }

    public @NotNull GameRoomInviteRecord getGameRoomInvite() {
        return this.gameRoomInvite;
    }
}
