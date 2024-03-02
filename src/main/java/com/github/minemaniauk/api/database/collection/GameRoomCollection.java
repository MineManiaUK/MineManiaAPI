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

package com.github.minemaniauk.api.database.collection;

import com.github.minemaniauk.api.database.record.GameRoomRecord;
import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents the game room collection.
 * A collection of game room records.
 */
public class GameRoomCollection extends TableAdapter<GameRoomRecord> {

    @Override
    public @NotNull String getName() {
        return "gamerooms";
    }

    /**
     * Used to get the instance of a game room record
     * given its uuid.
     *
     * @param gameRoomUuid The unique identifier of the game room.
     * @return The instance of the game room record or empty optional.
     */
    public @NotNull Optional<GameRoomRecord> getGameRoom(@NotNull UUID gameRoomUuid) {
        GameRoomRecord gameRoomRecord = this.getFirstRecord(
                new Query().match("uuid", gameRoomUuid.toString())
        );

        if (gameRoomRecord == null) return Optional.empty();
        return Optional.of(gameRoomRecord);
    }

    /**
     * Used to get the instance of a game room
     * given the uuid of the owner.
     *
     * @param playerUuid The owner's uuid.
     * @return The optional game room record.
     */
    public @NotNull Optional<GameRoomRecord> getGameRoomFromOwner(@NotNull UUID playerUuid) {
        GameRoomRecord gameRoomRecord = this.getFirstRecord(
                new Query().match("owner_uuid", playerUuid.toString())
        );

        if (gameRoomRecord == null) return Optional.empty();
        return Optional.of(gameRoomRecord);
    }

    /**
     * Used to get the game room a player is in.
     *
     * @param playerUuid The player's uuid.
     * @return Empty if the player is not in a game room.
     */
    public @NotNull Optional<GameRoomRecord> getGameRoomFromPlayer(@NotNull UUID playerUuid) {
        List<GameRoomRecord> gameRoomRecordList = this.getRecordList();

        for (GameRoomRecord record : gameRoomRecordList) {
            if (record.getPlayerUuids().contains(playerUuid)) return Optional.of(record);
        }
        return Optional.empty();
    }
}
