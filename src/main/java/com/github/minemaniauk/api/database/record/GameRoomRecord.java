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

import com.github.minemaniauk.api.MineManiaAPI;
import com.github.minemaniauk.api.game.GameType;
import com.github.minemaniauk.api.user.MineManiaUser;
import com.github.smuddgge.squishydatabase.record.Field;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a game room record.
 */
public class GameRoomRecord extends Record {

    private final @NotNull
    @Field(type = RecordFieldType.PRIMARY) String uuid;
    private @NotNull String owner_uuid;
    private @NotNull String player_uuids;
    private final @NotNull String game_type;
    private boolean is_private;

    /**
     * Used to create a new game room record.
     *
     * @param ownerUuid The game room owner's uuid.
     * @param gameType  The type of game the room is for.
     */
    public GameRoomRecord(@NotNull UUID ownerUuid, @NotNull GameType gameType) {
        this.uuid = UUID.randomUUID().toString();
        this.owner_uuid = ownerUuid.toString();
        this.player_uuids = "";
        this.game_type = gameType.toString();
        this.is_private = false;
    }

    /**
     * Used to get the game rooms unique identifier.
     *
     * @return The game rooms unique identifier.
     */
    public @NotNull UUID getUuid() {
        return UUID.fromString(uuid);
    }

    /**
     * Used to get the instance of the owner
     * as a mine mania user.
     * This relies on the complexity of the method
     * in the api contract to get the user from an uuid.
     *
     * @return The instance of the owner.
     */
    public @NotNull MineManiaUser getOwner() {
        return MineManiaAPI.getInstance().getContract().getUser(this.owner_uuid);
    }

    /**
     * Used to get the list of player uuid's in the game room.
     *
     * @return The list of player uuids.
     */
    public @NotNull List<UUID> getPlayerUuids() {
        List<UUID> list = new ArrayList<>();
        for (String uuid : this.player_uuids.split(",")) {
            list.add(UUID.fromString(uuid));
        }
        return list;
    }

    /**
     * Used to get the list of players in this game room.
     * This relies on the complexity of the method in the
     * api contract to get the user from an uuid.
     *
     * @return The list of users.
     */
    public @NotNull List<MineManiaUser> getPlayers() {
        List<MineManiaUser> list = new ArrayList<>();
        for (String uuid : this.player_uuids.split(",")) {
            list.add(MineManiaAPI.getInstance().getContract().getUser(UUID.fromString(uuid)));
        }
        return list;
    }

    /**
     * Used to get the value of the game type.
     *
     * @return The game type.
     */
    public @NotNull GameType getGameType() {
        return GameType.valueOf(this.game_type);
    }

    /**
     * Used to check if the game room is private.
     *
     * @return True if the game room is private.
     */
    public boolean isPrivate() {
        return this.is_private;
    }

    /**
     * Used to set if the game room should be private.
     *
     * @param isPrivate True if the game room should be private.
     * @return This instance.
     */
    public @NotNull GameRoomRecord setPrivate(boolean isPrivate) {
        this.is_private = isPrivate;
        return this;
    }

    public @NotNull GameRoomRecord setOwner(@NotNull UUID uuid) {
        this.owner_uuid = uuid.toString();
        List<UUID> playerUuids = this.getPlayerUuids();
        if (playerUuids.contains(uuid)) return this;
        List<UUID> orderedList = new LinkedList<>();
        orderedList.add(uuid);
        orderedList.addAll(playerUuids);
        this.player_uuids = String.join(",", orderedList.stream().map(UUID::toString).toList());
        return this;
    }

    /**
     * Used to add a player to the game room.
     *
     * @param uuid The player's uuid.
     * @return This instance.
     */
    public @NotNull GameRoomRecord addPlayer(@NotNull UUID uuid) {
        List<UUID> playerUuids = this.getPlayerUuids();
        playerUuids.add(uuid);
        this.player_uuids = String.join(",", playerUuids.stream().map(UUID::toString).toList());
        return this;
    }

    /**
     * Used to remove a player from the game room.
     *
     * @param uuid The player's uuid.
     * @return This instance.
     */
    public @NotNull GameRoomRecord removePlayer(@NotNull UUID uuid) {
        List<UUID> playerUuids = this.getPlayerUuids();
        playerUuids.remove(uuid);
        this.player_uuids = String.join(",", playerUuids.stream().map(UUID::toString).toList());
        return this;
    }
}
