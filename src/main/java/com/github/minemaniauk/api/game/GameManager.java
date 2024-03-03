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

import com.github.kerbity.kerb.client.listener.EventListener;
import com.github.kerbity.kerb.packet.event.Event;
import com.github.kerbity.kerb.packet.event.Priority;
import com.github.minemaniauk.api.MineManiaAPI;
import com.github.minemaniauk.api.database.collection.ArenaCollection;
import com.github.minemaniauk.api.database.record.ArenaRecord;
import com.github.minemaniauk.api.kerb.event.game.GameArenaActivate;
import com.github.minemaniauk.api.kerb.event.game.GameArenaDeactivate;
import com.github.smuddgge.squishydatabase.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents the instance of the global game manager.
 * Hooks to kerb and the database to sync with other instances.
 */
public class GameManager {

    private final @NotNull MineManiaAPI api;
    private final @NotNull List<Arena> localArenas;

    /**
     * Used to interact with the game manager.
     *
     * @param api The pointer to the api to sync.
     */
    public GameManager(@NotNull MineManiaAPI api) {
        this.api = api;
        this.localArenas = new ArrayList<>();

        // Register activate listener.
        this.api.getKerbClient().registerListener(Priority.HIGH, new EventListener<GameArenaActivate>() {
            @Override
            public @Nullable Event onEvent(GameArenaActivate event) {
                for (Arena arena : GameManager.this.localArenas) {
                    if (!arena.getIdentifier().equals(event.getArenaIdentifier())) continue;
                    arena.activate();
                }
                return event;
            }
        });

        // Register deactivate listener.
        this.api.getKerbClient().registerListener(Priority.HIGH, new EventListener<GameArenaDeactivate>() {
            @Override
            public @Nullable Event onEvent(GameArenaDeactivate event) {
                for (Arena arena : GameManager.this.localArenas) {
                    if (!arena.getIdentifier().equals(event.getArenaIdentifier())) continue;
                    arena.deactivate();
                }
                return event;
            }
        });
    }

    /**
     * Adds the arena to the database to show when it
     * is available, and registers it with this specific plugin.
     *
     * @param arena The instance of the arena.
     * @return This instance.
     */
    public @NotNull GameManager registerArena(@NotNull Arena arena) {

        // Register the arena locally.
        this.localArenas.add(arena);

        // Register the arena in the database.
        arena.save();
        return this;
    }

    /**
     * Used to unregister the arena in the database and locally.
     *
     * @param identifier The identifier of the arena.
     * @return This instance.
     */
    public @NotNull GameManager unregisterArena(@NotNull UUID identifier) {

        // Unregister the arena locally.
        this.localArenas.removeIf((arena -> arena.getIdentifier().equals(identifier)));

        // Unregister the arena in the database.
        this.api.getDatabase()
                .getTable(ArenaCollection.class)
                .removeAllRecords(new Query().match("identifier", identifier.toString()));

        return this;
    }

    /**
     * Used to get the list of registered arenas in the database.
     *
     * @param gameType The game type to filter.
     * @return The list of arena's.
     */
    public @NotNull List<Arena> getArenas(@NotNull GameType gameType) {

        // Check if the database is disabled.
        if (this.api.getDatabase().isDisabled()) return new ArrayList<>();

        // Get the instance of the record list.
        List<ArenaRecord> recordList = this.api.getDatabase()
                .getTable(ArenaCollection.class)
                .getRecordList(new Query().match("gameType", gameType.name()));

        // Map the records to arenas.
        return recordList.stream().map(ArenaRecord::asArena).toList();
    }

    /**
     * Used to get an arena from a game room uuid.
     *
     * @param gameRoomIdentifier The game room identifier.
     * @return The instance of the arena in the database.
     */
    public @NotNull Optional<Arena> getArena(@NotNull UUID gameRoomIdentifier) {

        // Check if the database is disabled.
        if (this.api.getDatabase().isDisabled()) return Optional.empty();

        // Get the record.
        ArenaRecord record = this.api.getDatabase().getTable(ArenaCollection.class)
                .getFirstRecord(new Query().match("gameRoomIdentifier", gameRoomIdentifier.toString()));

        // Check if it returned null.
        if (record == null) return Optional.empty();
        return Optional.of(record.asArena());
    }

    /**
     * Used to get the list of available arenas in the database.
     *
     * @param gameType The game type to filter.
     * @return The list of arena's.
     */
    public @NotNull List<Arena> getAvailableArenas(@NotNull GameType gameType) {
        return this.getArenas(gameType).stream().filter(Arena::isDeactivated).toList();
    }
}
