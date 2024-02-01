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

package com.github.minemaniauk.api.kerb.event.player;

import com.github.kerbity.kerb.packet.event.CancellableEvent;
import com.github.minemaniauk.api.format.ChatFormat;
import com.github.minemaniauk.api.user.MineManiaUser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player chat event.
 * Called when a player sends a message on a server.
 * This will contain the message formatted.
 */
public class PlayerPostChatEvent extends CancellableEvent implements PlayerEvent {

    private final @NotNull MineManiaUser user;
    private @NotNull String message;
    private final @NotNull ChatFormat chatFormat;

    private final @NotNull List<String> serverWhitelist;

    /**
     * Used to create a player chat event.
     *
     * @param user    The instance of the user that sent the message.
     * @param message The instance of the message.
     */
    public PlayerPostChatEvent(@NotNull MineManiaUser user, @NotNull String message) {
        this.user = user;
        this.message = message;

        this.serverWhitelist = new ArrayList<>();
        this.chatFormat = new ChatFormat();
    }

    @Override
    public @NotNull MineManiaUser getUser() {
        return this.user;
    }

    /**
     * Used to get the instance of the message.
     *
     * @return The instance of the message.
     */
    public @NotNull String getMessage() {
        return this.message;
    }

    /**
     * Used to get the instance of the chat format.
     * You can use this instance to add a prefix and postfix to the message.
     *
     * @return The instance of the chat format.
     */
    public @NotNull ChatFormat getChatFormat() {
        return this.chatFormat;
    }

    /**
     * Used to get the current list of servers that the
     * message should be broadcast on.
     *
     * @return The list of whitelisted servers.
     */
    public @NotNull List<String> getServerWhitelist() {
        return this.serverWhitelist;
    }

    /**
     * Used to change the content of the message.
     *
     * @param message The content of the message.
     * @return This instance.
     */
    public @NotNull PlayerPostChatEvent setMessage(@NotNull String message) {
        this.message = message;
        return this;
    }

    /**
     * Used to add a server to the server whitelist.
     * The list of servers the message should be broadcast on.
     *
     * @param serverName The server's name.
     * @return This instance.
     */
    public @NotNull PlayerPostChatEvent addWhitelistedServer(@NotNull String serverName) {
        this.serverWhitelist.add(serverName);
        return this;
    }

    /**
     * Used to add a list of servers to the server whitelist.
     *
     * @param serverWhitelist The list of servers.
     * @return This instance.
     */
    public @NotNull PlayerPostChatEvent addWhitelistedServer(@NotNull List<String> serverWhitelist) {
        this.serverWhitelist.addAll(serverWhitelist);
        return this;
    }
}
