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

package com.github.minemaniauk.api;

import com.github.kerbity.kerb.client.listener.EventListener;
import com.github.kerbity.kerb.event.Event;
import com.github.minemaniauk.api.kerb.event.player.PlayerChatEvent;
import com.github.minemaniauk.api.kerb.event.useraction.UserActionHasPermissionListEvent;
import com.github.minemaniauk.api.kerb.event.useraction.UserActionIsOnlineEvent;
import com.github.minemaniauk.api.kerb.event.useraction.UserActionIsVanishedEvent;
import com.github.minemaniauk.api.kerb.event.useraction.UserActionMessageEvent;
import com.github.minemaniauk.api.user.MineManiaUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a mine mania api contract.
 * The software using this api must complete these methods
 * to use the api.
 */
public interface MineManiaAPIContract extends EventListener<Event> {

    @Override
    default @Nullable Event onEvent(Event event) {
        if (event instanceof UserActionHasPermissionListEvent userEvent) return this.onHasPermission(userEvent);
        if (event instanceof UserActionIsOnlineEvent userEvent) return this.onIsOnline(userEvent);
        if (event instanceof UserActionIsVanishedEvent userEvent) return this.onIsVanished(userEvent);
        if (event instanceof UserActionMessageEvent userEvent) return this.onMessage(userEvent);
        if (event instanceof PlayerChatEvent playerEvent) return this.onChatEvent(playerEvent);
        return event;
    }

    /**
     * Used to get a mine mania user from the players uuid.
     *
     * @param uuid The uuid of the player.
     * @return The mine mania user.
     */
    @NotNull MineManiaUser getUser(@NotNull UUID uuid);

    /**
     * Used to get a mine mania user from the player's name.
     *
     * @param name The name of the player.
     * @return The mine mania user.
     */
    @NotNull MineManiaUser getUser(@NotNull String name);

    /**
     * Called when a server wants to check a users permissions.
     *
     * @param event The instance of the event.
     * @return The event result.
     */
    @Nullable UserActionHasPermissionListEvent onHasPermission(@NotNull UserActionHasPermissionListEvent event);

    /**
     * Called when a server wants to check if a user is online.
     *
     * @param event The instance of the event.
     * @return The event result.
     */
    @Nullable UserActionIsOnlineEvent onIsOnline(@NotNull UserActionIsOnlineEvent event);

    /**
     * Called when a server wants to check if a user is vanished.
     *
     * @param event The instance of the event.
     * @return The event result.
     */
    @Nullable UserActionIsVanishedEvent onIsVanished(@NotNull UserActionIsVanishedEvent event);

    /**
     * Called when a server wants to send a message to a player.
     *
     * @param event The instance of the event.
     * @return The event result.
     */
    @Nullable UserActionMessageEvent onMessage(@NotNull UserActionMessageEvent event);

    /**
     * Called when the server should broadcast a message to all the players.
     *
     * @param event The instance of the event.
     * @return The completed event instance.
     */
    @NotNull PlayerChatEvent onChatEvent(@NotNull PlayerChatEvent event);
}
