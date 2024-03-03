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

import com.github.kerbity.kerb.client.KerbClient;
import com.github.minemaniauk.api.MineManiaAPI;
import com.github.minemaniauk.api.database.record.ArenaRecord;
import com.github.minemaniauk.api.kerb.event.game.GameArenaActivate;
import com.github.minemaniauk.api.kerb.event.game.GameArenaDeactivate;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a global arena.
 * This is a representation of an arena that can be
 * on a different server.
 */
public class GlobalArena extends Arena {

    /**
     * Used to create an instance of a global arena.
     *
     * @param record The instance of the arena record
     *               to abstract.
     */
    public GlobalArena(@NotNull ArenaRecord record) {
        super(UUID.fromString(record.identifier), record.serverName, GameType.valueOf(record.gameType));
        this.setGameRoomIdentifier(record.gameRoomIdentifier == null ? null : UUID.fromString(record.gameRoomIdentifier));
        this.setMinPlayers(record.minPlayers);
        this.setMaxPlayers(record.maxPlayers);
    }

    /**
     * This will fire a kerb event.
     * See this method for more info about what this method does:
     * {@link Arena#activate()}
     */
    @Override
    public void activate() {

        // Get the pointer to the client.
        final KerbClient client = MineManiaAPI.getInstance().getKerbClient();

        // Check the client is valid.
        if (!client.isConnected() || !client.isValid()) return;

        // Call the event.
        client.callEvent(new GameArenaActivate(this.getIdentifier()));
    }

    /**
     * This will fire a kerb event.
     * See this method for more info about what this method does:
     * {@link Arena#deactivate()}
     */
    @Override
    public void deactivate() {

        // Get the pointer to the client.
        final KerbClient client = MineManiaAPI.getInstance().getKerbClient();

        // Check the client is valid.
        if (!client.isConnected() || !client.isValid()) return;

        // Call the event.
        client.callEvent(new GameArenaDeactivate(this.getIdentifier()));
    }
}
