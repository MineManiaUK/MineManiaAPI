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

package com.github.minemaniauk.api.user;

import com.github.kerbity.kerb.result.CompletableResultSet;
import com.github.minemaniauk.api.MineManiaAPI;
import com.github.minemaniauk.api.MineManiaLocation;
import com.github.minemaniauk.api.kerb.event.useraction.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a mine mania user action set.
 * This will contain methods that use kerb
 * events to execute.
 */
public class MineManiaUserActionSet {

    private final @NotNull MineManiaUser user;

    /**
     * Used to create a new action set for a user.
     *
     * @param user The instance of the user.
     */
    public MineManiaUserActionSet(@NotNull MineManiaUser user) {
        this.user = user;
    }

    /**
     * Used to send a message to the user
     * though a message event.
     * <ul>
     *     <li>
     *         Warning this action can take some time to
     *         process and is recommended to be threaded.
     *     </li>
     * </ul>
     *
     * @param message The instance of the message.
     * @return True if the message was sent.
     */
    public boolean sendMessage(@NotNull String message) {
        CompletableResultSet<UserActionMessageEvent> result = MineManiaAPI.getInstance()
                .callEvent(new UserActionMessageEvent(this.user, message));

        // Wait for the final result to be completed.
        result.waitForFinalResult();

        // Check if it contains a completed event.
        return result.containsCompleted();
    }

    /**
     * Used to send a list of messages to the user.
     * This will be combined to create one message
     * with line breaks.
     * <ul>
     *     <li>
     *         Warning this action can take some time to
     *          process and is recommended to be threaded.
     *     </li>
     * </ul>
     *
     * @param message The instance of the message.
     * @return True if the message was sent.
     */
    public boolean sendMessage(@NotNull List<String> message) {
        CompletableResultSet<UserActionMessageEvent> result = MineManiaAPI.getInstance()
                .callEvent(new UserActionMessageEvent(this.user, message));

        // Wait for the final result to be completed.
        result.waitForFinalResult();

        // Check if it contains a completed event.
        return result.containsCompleted();
    }

    /**
     * Used to send a message on a thread.
     * This will not interrupt the main thread.
     *
     * @param message The instance of the message to send the user.
     * @return The completable result.
     */
    public @NotNull CompletableResultSet<Boolean> sendMessageThreaded(@NotNull String message) {
        CompletableResultSet<Boolean> result = new CompletableResultSet<>(1);
        new Thread(() -> result.addResult(this.sendMessage(message))).start();
        return result;
    }

    /**
     * Used to send a message with breaks on a thread.
     * This will not interrupt the main thread.
     *
     * @param message The message to send, this will be
     *                replaced with a string with new lines.
     * @return The completable result.
     */
    public @NotNull CompletableResultSet<Boolean> sendMessageThreaded(@NotNull List<String> message) {
        CompletableResultSet<Boolean> result = new CompletableResultSet<>(1);
        new Thread(() -> result.addResult(this.sendMessage(message))).start();
        return result;
    }

    /**
     * Used to check if a user is online.
     *
     * @return The completable boolean.
     */
    public @NotNull CompletableResultSet<Boolean> isOnline() {
        CompletableResultSet<Boolean> result = new CompletableResultSet<>(1);

        // Check if the event contains a true value, they are online.
        new Thread(() -> result.addResult(
                MineManiaAPI.getInstance()
                        .callEvent(new UserActionIsOnlineEvent(this.user))
                        .waitForComplete()
                        .containsSettable(true)
        )).start();

        return result;
    }

    /**
     * Used to check if a user is vanished.
     *
     * @return The completable boolean.
     */
    public @NotNull CompletableResultSet<Boolean> isVanished() {
        CompletableResultSet<Boolean> result = new CompletableResultSet<>(1);

        // Check if the event contains a true value, they are vanished.
        new Thread(() -> result.addResult(
                MineManiaAPI.getInstance()
                        .callEvent(new UserActionIsVanishedEvent(this.user))
                        .waitForComplete()
                        .containsSettable(true)
        )).start();

        return result;
    }

    /**
     * Used to check if the user has a certain permission.
     *
     * @param permission The instance of the permission string.
     * @return The completable result.
     */
    public @NotNull CompletableResultSet<Boolean> hasPermission(@NotNull String permission) {
        CompletableResultSet<Boolean> result = new CompletableResultSet<>(1);

        // Check if the event contains a true value, they have the permission.
        new Thread(() -> result.addResult(
                MineManiaAPI.getInstance()
                        .callEvent(new UserActionHasPermissionListEvent(this.user, permission))
                        .waitForComplete()
                        .containsSettable(true)
        )).start();

        return result;
    }

    /**
     * Used to check if a user has all the permissions
     * in a permission list.
     *
     * @param permissionList The instance of the permission list.
     * @return The completable result.
     */
    public @NotNull CompletableResultSet<Boolean> hasPermission(@NotNull List<String> permissionList) {
        CompletableResultSet<Boolean> result = new CompletableResultSet<>(1);

        // Check if the event contains a true value, they have the permission.
        new Thread(() -> result.addResult(
                MineManiaAPI.getInstance()
                        .callEvent(new UserActionHasPermissionListEvent(this.user, permissionList))
                        .waitForComplete()
                        .containsSettable(true)
        )).start();

        return result;
    }

    /**
     * Used to teleport a player to a world location in a server.
     *
     * @param location The location to teleport to.
     * @return The completable result.
     */
    public @NotNull CompletableResultSet<Boolean> teleport(@NotNull MineManiaLocation location) {
        CompletableResultSet<Boolean> result = new CompletableResultSet<>(1);

        new Thread(() -> {

            // Check if it's been completed.
            boolean completed = MineManiaAPI.getInstance()
                    .callEvent(new UserActionTeleportEvent(this.user, location))
                    .waitForComplete()
                    .containsCompleted();

            if (completed) {
                result.addResult(true);
                return;
            }

            // Try again.
            this.teleport(location);
        }).start();


        return result;
    }
}
