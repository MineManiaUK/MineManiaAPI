/*
 * MineManiaTNTRun
 * Used for interacting with the database and message broker.
 * Copyright (C) 2023  MineManiaUK Staff
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
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
 * Represents a session.
 *
 * @param <A> The type of arena this session can be run in.
 */
public class Session<A extends Arena> {

    private final @NotNull UUID arenaIdentifier;
    private final @NotNull ArenaFactory<A> arenaFactory;
    private final @NotNull List<SessionComponent<?>> componentList;

    /**
     * Used to create a new session instance.
     *
     * @param arenaIdentifier The arena identifier.
     */
    public Session(@NotNull UUID arenaIdentifier, @NotNull ArenaFactory<A> arenaFactory) {
        this.arenaIdentifier = arenaIdentifier;
        this.arenaFactory = arenaFactory;
        this.componentList = new ArrayList<>();
    }

    /**
     * Represents an arena factory.
     *
     * @param <A> The type of arena.
     */
    public interface ArenaFactory<A extends Arena> {

        /**
         * Used to get the instance of an arena.
         *
         * @param identifier The identifier of the arena.
         * @return The instance of the arena.
         */
        @NotNull Optional<A> getArena(@NotNull UUID identifier);
    }

    /**
     * Used to get the instance of the arena identifier.
     *
     * @return The instance of the arena identifier.
     */
    public @NotNull UUID getArenaIdentifier() {
        return this.arenaIdentifier;
    }

    /**
     * Used to get the instance of the arena this session is in.
     *
     * @return The instance of the arena.
     */
    public @NotNull A getArena() {
        return this.arenaFactory.getArena(this.arenaIdentifier).orElseThrow();
    }

    /**
     * Used to get the instance of the arena factory used
     * to get the instance of the arena.
     *
     * @return The instance of the arena factory.
     */
    public @NotNull ArenaFactory<A> getArenaFactory() {
        return this.arenaFactory;
    }

    /**
     * Used to register a component with this session.
     *
     * @param component The component to register.
     * @return This instance.
     */
    public @NotNull Session<A> registerComponent(@NotNull SessionComponent component) {
        this.componentList.add(component);
        return this;
    }

    /**
     * Used to unregister a component with this session.
     *
     * @param clazz The type of class to unregister.
     * @return This instance.
     */
    public @NotNull Session<A> unregisterComponent(@NotNull Class<SessionComponent> clazz) {
        this.componentList.removeIf(item -> item.getClass().isInstance(clazz));
        return this;
    }

    /**
     * Used to get a session component from this session.
     *
     * @param clazz The type of class to look for.
     * @param <T>   The session component type.
     * @return The instance of the class.
     * @throws RuntimeException If the class doesn't exist.
     */
    public @NotNull <T> T getComponent(@NotNull Class<T> clazz) {

        // Loop though components.
        for (SessionComponent<?> component : this.componentList) {
            if (clazz.isInstance(component)) return (T) component;
        }

        throw new RuntimeException("Tried to get component from session but it doesnt exist. " + clazz.getName());
    }

    /**
     * Used to start all the components in this session.
     *
     * @return This instance.
     */
    public @NotNull Session<A> startComponents() {
        for (SessionComponent component : this.componentList) {
            component.start();
        }

        return this;
    }

    /**
     * Used to stop all the components in this session.
     *
     * @return This instance.
     */
    public @NotNull Session<A> stopComponents() {
        for (SessionComponent component : this.componentList) {
            component.stop();
        }

        return this;
    }
}
