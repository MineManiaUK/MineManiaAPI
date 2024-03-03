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

package com.github.minemaniauk.api.game;

import com.github.minemaniauk.api.MineManiaAPI;
import com.github.minemaniauk.api.database.collection.ArenaCollection;
import com.github.minemaniauk.api.database.collection.GameRoomCollection;
import com.github.minemaniauk.api.database.record.ArenaRecord;
import com.github.minemaniauk.api.database.record.GameRoomRecord;
import com.github.minemaniauk.api.indicator.Savable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents an arena.
 */
public abstract class Arena implements Savable {

    private final @NotNull UUID identifier;
    private final @NotNull String serverName;
    private final @NotNull GameType gameType;
    private @Nullable UUID gameRoomIdentifier;
    private int minPlayers;
    private int maxPlayers;

    /**
     * Used to create a new instance of an arena.
     *
     * @param identifier The arena's unique identifier.
     * @param serverName The servers name.
     * @param gameType The arena's game type.
     */
    public Arena(@NotNull UUID identifier, @NotNull String serverName, @NotNull GameType gameType) {
        this.identifier = identifier;
        this.serverName = serverName;
        this.gameType = gameType;
    }

    /**
     * Called when the arena should activate
     * now a game room has been added.
     * This action will be sent though kerb to the local
     * server the arena is on.
     */
    public abstract void activate();

    /**
     * Called when the arena should be deactivated.
     * This action will be sent though kerb to the local
     * server the arena is on.
     */
    public abstract void deactivate();

    /**
     * Used to get the arenas unique identifier.
     *
     * @return The arenas unique identifier.
     */
    public @NotNull UUID getIdentifier() {
        return this.identifier;
    }

    /**
     * Used to get the name of the server that the
     * arena is on.
     *
     * @return This is defined in the api config.yaml
     * in every server.
     */
    public @NotNull String getServerName() {
        return this.serverName;
    }

    /**
     * Used to get the game type of the arena.
     *
     * @return The arena's game type.
     */
    public @NotNull GameType getGameType() {
        return this.gameType;
    }

    /**
     * If there are players using this arena, this method will
     * return the game room the players are in.
     *
     * @return The game room identifier.
     * Null if there is no game room currently using this arena.
     */
    public @NotNull Optional<UUID> getGameRoomIdentifier() {
        return Optional.ofNullable(this.gameRoomIdentifier);
    }

    /**
     * Used to get the instance of the game room linked
     * to this arena.
     * If there are no players using this arena,
     * it will return empty.
     * <li>This will use a database operation.</li>
     *
     * @return The instance of the game room.
     */
    public @NotNull Optional<GameRoomRecord> getGameRoom() {
        if (this.getGameRoomIdentifier().isEmpty()) return Optional.empty();
        return MineManiaAPI.getInstance()
                .getDatabase()
                .getTable(GameRoomCollection.class)
                .getGameRoom(this.getGameRoomIdentifier().get());
    }

    public int getMinPlayers() {
        return this.minPlayers;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    /**
     * Used to set the gamer room identifier in this instance of the arena.
     * Please use the method .save() to save these changes.
     *
     * @param gameRoomIdentifier The game room identifier.
     * @return This instance.
     */
    public @NotNull Arena setGameRoomIdentifier(@Nullable UUID gameRoomIdentifier) {
        this.gameRoomIdentifier = gameRoomIdentifier;
        return this;
    }

    public @NotNull Arena setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    public @NotNull Arena setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        return this;
    }

    /**
     * Checks if there are players playing a game in this arena.
     * This is checked by seeing if the game room identifier is not null.
     *
     * @return True if activated.
     */
    public boolean isActivated() {
        return this.gameRoomIdentifier != null;
    }

    /**
     * Checks if there are no players playing a game in this arena.
     * This is checked by seeing if the game room identifier is null.
     *
     * @return True if deactivated.
     */
    public boolean isDeactivated() {
        return this.gameRoomIdentifier == null;
    }

    @Override
    public void save() {

        // Check if the database is disabled.
        if (MineManiaAPI.getInstance().getDatabase().isDisabled()) return;

        // Create this as a record.
        ArenaRecord record = new ArenaRecord(
                this.identifier.toString(),
                this.serverName,
                this.gameType.name()
        );

        record.gameRoomIdentifier = this.gameRoomIdentifier.toString();
        record.minPlayers = this.minPlayers;
        record.maxPlayers = this.maxPlayers;

        // Update record.
        MineManiaAPI.getInstance().getDatabase()
                .getTable(ArenaCollection.class)
                .insertRecord(record);
    }
}
