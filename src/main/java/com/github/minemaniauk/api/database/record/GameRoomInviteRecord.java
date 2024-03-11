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

package com.github.minemaniauk.api.database.record;

import com.github.minemaniauk.api.MineManiaAPIAdapter;
import com.github.minemaniauk.api.database.collection.GameRoomInviteCollection;
import com.github.minemaniauk.api.database.collection.GameRoomCollection;
import com.github.smuddgge.squishydatabase.record.Field;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


/**
 * Represents the game invite record.
 * Contains an invitation to a game room from a player
 * to another player.
 */
public class GameRoomInviteRecord extends Record {

    @Field(type = RecordFieldType.PRIMARY)
    public @NotNull String uuid;
    public @NotNull String gameRoomUuid;
    public @NotNull String toPlayerUuid;

    /**
     * Used to check if the invite is still valid.
     * If the game room no longer exists, the invite is invalid.
     *
     * @return If this invite is valid.
     */
    public boolean isValid() {
        return MineManiaAPIAdapter.getInstance().getDatabase()
                .getTable(GameRoomCollection.class)
                .getGameRoom(UUID.fromString(gameRoomUuid))
                .isPresent();
    }

    /**
     * Used to remove this record.
     *
     * @return This instance.
     */
    public @NotNull GameRoomInviteRecord remove() {
        MineManiaAPIAdapter.getInstance().getDatabase()
                .getTable(GameRoomInviteCollection.class)
                .removeRecord(this);
        return this;
    }
}
