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

import com.github.kerbity.kerb.event.CompletableEvent;
import com.github.minemaniauk.api.user.MineManiaUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a player chat event.
 * Used as a broadcast, to tell the other servers
 * a chat event was finalised and should be broadcast
 * to the players on certain servers.
 */
public class PlayerChatEvent extends CompletableEvent implements PlayerEvent {

    private final @NotNull MineManiaUser user;
    private final @NotNull String formattedMessage;
    private final @NotNull List<String> serverWhiteList;

    /**
     * Used to create a player chat event.
     *
     * @param user             The instance of the user sending the message.
     * @param formattedMessage The instance of the formatted message.
     * @param serverWhiteList  The instance of the servers to send the chat message to.
     */
    public PlayerChatEvent(@NotNull MineManiaUser user,
                           @NotNull String formattedMessage,
                           @NotNull List<String> serverWhiteList) {

        this.user = user;
        this.formattedMessage = formattedMessage;
        this.serverWhiteList = serverWhiteList;
    }

    @Override
    public @NotNull MineManiaUser getUser() {
        return this.user;
    }

    /**
     * Used to get the instance of the formatted message.
     *
     * @return The instance of the formatted message.
     */
    public @NotNull String getFormattedMessage() {
        return this.formattedMessage;
    }

    /**
     * Used to get the list of server that the
     * message should be broadcast on.
     *
     * @return The list of servers.
     */
    public @NotNull List<String> getServerWhiteList() {
        return this.serverWhiteList;
    }
}
