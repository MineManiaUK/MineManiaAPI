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
 * @param <T> The type of sessions this class will be managing.
 * @param <A> The type of arena the sessions
 *            will be connected to.
 */
public class SessionManager<T extends Session<A>, A extends Arena> {

    private final @NotNull List<T> sessionList;

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
    public @NotNull Optional<T> getSession(@NotNull UUID arenaIdentifier) {
        for (T session : this.sessionList) {
            if (session.getArenaIdentifier().equals(arenaIdentifier)) return Optional.of(session);
        }

        return Optional.empty();
    }

    /**
     * Used to get the instance of the session list.
     *
     * @return The instance of the session list.
     */
    public @NotNull List<T> getSessionList() {
        return this.sessionList;
    }

    /**
     * Used to register a session with this manager.
     *
     * @param session The session to register.
     * @return This instance.
     */
    public @NotNull SessionManager<T, A> registerSession(@NotNull T session) {
        this.sessionList.add(session);
        return this;
    }

    /**
     * Used to unregister a session with this manager.
     *
     * @param session The instance of the session.
     * @return This instance.
     */
    public @NotNull SessionManager<T, A> unregisterSession(@NotNull T session) {
        this.sessionList.remove(session);
        return this;
    }

    /**
     * Used to unregister all sessions with a specific arena identifier.
     *
     * @param arenaIdentifier The instance of the arena identifier.
     * @return This instance.
     */
    public @NotNull SessionManager<T, A> unregisterSession(@NotNull UUID arenaIdentifier) {
        List<T> toRemove = new ArrayList<>();

        for (T session : this.sessionList) {
            if (session.getArenaIdentifier().equals(arenaIdentifier)) {
                toRemove.add(session);
            }
        }

        this.sessionList.removeAll(toRemove);
        return this;
    }

    /**
     * Used to stop all the session components in this manager.
     *
     * @return This instance.
     */
    public @NotNull SessionManager<T, A> stopAllSessionComponents() {
        for (T session : this.sessionList) {
            session.stopComponents();
        }

        return this;
    }
}
