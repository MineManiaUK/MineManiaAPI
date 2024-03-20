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

import com.github.minemaniauk.api.game.Arena;
import com.github.minemaniauk.api.game.GlobalArena;
import com.github.smuddgge.squishydatabase.record.Field;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an arena record.
 */
public class ArenaRecord extends Record {

    @Field(type = RecordFieldType.PRIMARY)
    public @NotNull String identifier;
    public @NotNull String serverName;
    public @NotNull String gameType;
    public @Nullable String gameRoomIdentifier;
    public int minPlayers;
    public int maxPlayers;
    public @Nullable String displayItemSection;

    public ArenaRecord() {

    }

    public ArenaRecord(@NotNull String identifier, @NotNull String serverName, @NotNull String gameType) {
        this.identifier = identifier;
        this.serverName = serverName;
        this.gameType = gameType;
    }

    /**
     * Used to get this record as an arena instance.
     *
     * @return The instance of the arena.
     */
    public @NotNull Arena asArena() {
        return new GlobalArena(this);
    }
}
