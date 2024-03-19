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
import com.github.minemaniauk.api.database.collection.GameRoomInviteCollection;
import com.github.minemaniauk.api.database.record.ArenaRecord;
import com.github.minemaniauk.api.database.record.GameRoomInviteRecord;
import com.github.minemaniauk.api.database.record.GameRoomRecord;
import com.github.minemaniauk.api.kerb.event.game.GameArenaActivate;
import com.github.minemaniauk.api.kerb.event.game.GameArenaDeactivate;
import com.github.minemaniauk.api.kerb.event.gameroom.GameRoomInviteEvent;
import com.github.smuddgge.squishydatabase.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
                    arena.setGameRoomIdentifier(event.getGameRoomIdentifier());
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
                    arena.setGameRoomIdentifier(null);
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
     * Used to unregister all local arenas.
     *
     * @return This instance.
     */
    public @NotNull GameManager unregisterLocalArenas() {
        List<UUID> identifiersToUnregister = new ArrayList<>();
        for (Arena arena : new ArrayList<>(this.localArenas)) {
            identifiersToUnregister.add(arena.getIdentifier());
        }

        // Loop though identifiers.
        for (UUID identifier : identifiersToUnregister) {
            this.unregisterArena(identifier);
        }

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
     * Used to get the instance of the local arenas.
     *
     * @return The instance of the local arenas.
     */
    public @NotNull List<Arena> getLocalArenas() {
        return this.localArenas;
    }

    /**
     * Used to get a specific local arena given the identifier.
     *
     * @param identifier The arena's identifier.
     * @return The optional arena.
     */
    public @NotNull Optional<Arena> getLocalArena(@NotNull UUID identifier) {
        for (Arena arena : this.localArenas) {
            if (arena.getIdentifier().equals(identifier)) return Optional.of(arena);
        }
        return Optional.empty();
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

    /**
     * Used to get the first available arena from the database.
     *
     * @param gameType The game type to filter.
     * @param players  The number of players.
     * @return The available arena. Empty if there is non.
     */
    public @NotNull Optional<Arena> getFirstAvailableArena(@NotNull GameType gameType, int players) {
        List<Arena> list = this.getAvailableArenas(gameType).stream()
                .filter(arena -> arena.getMinPlayers() <= players && players <= arena.getMaxPlayers())
                .toList();

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    /**
     * Used to get the map of arena availability.
     * <li>Map<"Min-Max" Players, List[Available, Amount]></li>
     *
     * @param gameType The type of game arena to filter.
     * @return The map of arena availability.
     */
    public @NotNull Map<String, List<Integer>> getArenaAvailability(@NotNull GameType gameType) {
        Map<String, List<Integer>> map = new LinkedHashMap<>();

        for (Arena arena : this.getArenas(gameType)) {
            final String key = arena.getMinPlayers() + "-" + arena.getMaxPlayers();

            if (map.containsKey(key)) {
                List<Integer> list = map.get(key);
                map.put(key, List.of(
                        arena.isActivated() ? list.get(0) : list.get(0) + 1,
                        list.get(1) + 1
                ));
            }

            map.put(key, List.of(
                    arena.isActivated() ? 0 : 1,
                    1
            ));
        }

        return map;
    }

    /**
     * Used to get the arena availability as lore.
     *
     * <li>List["Min-Max Players Available/Amount Available Arenas", ...]</li>
     *
     * @param gameType The game type to filter.
     * @return The lore list.
     */
    public @NotNull List<String> getArenaAvailabilityAsLore(@NotNull GameType gameType) {
        Map<String, List<Integer>> map = this.getArenaAvailability(gameType);
        List<String> lore = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            lore.add(entry.getKey() + " Players " + entry.getValue().get(0) + "/" + entry.getValue().get(1) + " Available Arenas");
        }
        return lore;
    }

    /**
     * Used to get an invitation from the database.
     *
     * @param uuid The uuid of the invite.
     * @return The optional invite.
     */
    public @NotNull Optional<GameRoomInviteRecord> getInvite(@NotNull UUID uuid) {
        return Optional.ofNullable(this.api.getDatabase()
                .getTable(GameRoomInviteCollection.class)
                .getFirstRecord(new Query().match("uuid", uuid.toString()))
        );
    }

    /**
     * Used to get an invitation from the database.
     *
     * @param toPlayerUuid The player it is to.
     * @param gameRoomUuid The game room it is from
     * @return The instance of the invite record.
     */
    public @NotNull Optional<GameRoomInviteRecord> getInvite(@NotNull UUID toPlayerUuid, @NotNull UUID gameRoomUuid) {
        return Optional.ofNullable(this.api.getDatabase()
                .getTable(GameRoomInviteCollection.class)
                .getFirstRecord(new Query()
                        .match("toPlayerUuid", toPlayerUuid.toString())
                        .match("gameRoomUuid", gameRoomUuid.toString())
                )
        );
    }

    /**
     * Used to get the list of invites sent to this player.
     *
     * @param toPlayerUuid The player that has been sent the invites.
     * @return The list of invites.
     */
    public @NotNull List<GameRoomInviteRecord> getInviteList(@NotNull UUID toPlayerUuid) {
        return this.api.getDatabase()
                .getTable(GameRoomInviteCollection.class)
                .getRecordList(new Query()
                        .match("toPlayerUuid", toPlayerUuid.toString())
                );
    }

    /**
     * Used to get the invite list for a game room.
     *
     * @param gameRoomUuid The game room uuid.
     * @return The list of game room invites.
     */
    public @NotNull List<GameRoomInviteRecord> getInviteListForGameRoom(@NotNull UUID gameRoomUuid) {
        return this.api.getDatabase()
                .getTable(GameRoomInviteCollection.class)
                .getRecordList(new Query()
                        .match("gameRoomUuid", gameRoomUuid)
                );
    }

    /**
     * Used to check if a player has a pending invite to a game room.
     *
     * @param playerSentToUuid The instance of the player uuid
     *                         the invite was sent to.
     * @param gameRoomUuid     The instance of the game room uuid.
     * @return True if they have been invited
     */
    public boolean hasBeenInvited(@NotNull UUID playerSentToUuid, @NotNull UUID gameRoomUuid) {
        return this.getInvite(playerSentToUuid, gameRoomUuid).isPresent();
    }

    /**
     * Used to send an invitation to a player from a game room.
     *
     * @param toPlayerUuid   The player it should be sent to.
     * @param gameRoomRecord The game room record it is being sent from.
     */
    public void sendInvite(@NotNull UUID toPlayerUuid, @NotNull GameRoomRecord gameRoomRecord) {

        // Create the invite record.
        GameRoomInviteRecord invite = new GameRoomInviteRecord();
        invite.uuid = UUID.randomUUID().toString();
        invite.toPlayerUuid = toPlayerUuid.toString();
        invite.gameRoomUuid = gameRoomRecord.getUuid().toString();

        // Add to the database.
        this.api.getDatabase()
                .getTable(GameRoomInviteCollection.class)
                .insertRecord(invite);

        // Broadcast the event.
        this.api.getKerbClient().callEvent(new GameRoomInviteEvent(gameRoomRecord, invite));
    }
}
