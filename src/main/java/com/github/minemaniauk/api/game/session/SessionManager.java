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

package com.github.minemaniauk.api.game.session;

import com.github.minemaniauk.api.game.Arena;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a session manager.
 * Contains active sessions.
 *
 * @param <A> The type of arena the sessions
 *            will be connected to.
 */
public class SessionManager<A extends Arena> {

    private final @NotNull List<Session<A>> sessionList;

    /**
     * Used to create a new session manager instance.
     */
    public SessionManager() {
        this.sessionList = new ArrayList<>();
    }

    /**
     * Used to get a session registered with this manager.
     *
     * @param arenaIdentifier The arena identifier to look for.
     * @return The optional session instance.
     */
    public @NotNull Optional<Session<A>> getSession(@NotNull UUID arenaIdentifier) {
        for (Session<A> session : this.sessionList) {
            if (session.getArenaIdentifier().equals(arenaIdentifier)) return Optional.of(session);
        }

        return Optional.empty();
    }

    /**
     * Used to register a session with this manager.
     *
     * @param session The session to register.
     * @return This instance.
     */
    public @NotNull SessionManager<A> registerSession(@NotNull Session<A> session) {
        this.sessionList.add(session);
        return this;
    }

    /**
     * Used to unregister a session with this manager.
     *
     * @param session The instance of the session.
     * @return This instance.
     */
    public @NotNull SessionManager<A> unregisterSession(@NotNull Session<A> session) {
        this.sessionList.remove(session);
        return this;
    }

    /**
     * Used to unregister all sessions with a specific arena identifier.
     *
     * @param arenaIdentifier The instance of the arena identifier.
     * @return This instance.
     */
    public @NotNull SessionManager<A> unregisterSession(@NotNull UUID arenaIdentifier) {
        List<Session<A>> toRemove = new ArrayList<>();

        for (Session<A> session : this.sessionList) {
            if (session.getArenaIdentifier().equals(arenaIdentifier)) {
                toRemove.add(session);
            }
        }

        this.sessionList.removeAll(toRemove);
        return this;
    }
}
