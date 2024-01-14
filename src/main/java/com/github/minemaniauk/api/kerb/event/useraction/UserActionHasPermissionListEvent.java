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

package com.github.minemaniauk.api.kerb.event.useraction;

import com.github.kerbity.kerb.packet.event.SettableEvent;
import com.github.minemaniauk.api.user.MineManiaUser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event used to check a list of permissions.
 * If they are not online it will return null.
 */
public class UserActionHasPermissionListEvent extends SettableEvent<Boolean> implements UserActionEvent {

    private final @NotNull MineManiaUser user;
    private final @NotNull List<String> permissionList;

    /**
     * An event used to check if a player has certain permissions on the server.
     *
     * @param user       The instance of the user.
     * @param permission The instance of the permission.
     */
    public UserActionHasPermissionListEvent(@NotNull MineManiaUser user, @NotNull String permission) {
        this.user = user;

        this.permissionList = new ArrayList<>();
        this.permissionList.add(permission);
    }

    /**
     * An event used to check if a player has certain permissions on the server.
     *
     * @param user           The instance of the user.
     * @param permissionList The instance of the permission list.
     */
    public UserActionHasPermissionListEvent(@NotNull MineManiaUser user, @NotNull List<String> permissionList) {
        this.user = user;

        this.permissionList = permissionList;
    }

    /**
     * Represents a permission checker.
     * Used to check a players permissions.
     */
    public interface PermissionChecker {

        /**
         * Used to check if the user has a certain permission.
         *
         * @param permission The permission to check for.
         * @return True if the user has the permission
         * and it is set to true.
         */
        boolean hasPermission(@NotNull String permission);
    }

    @Override
    public @NotNull MineManiaUser getUser() {
        return this.user;
    }

    /**
     * Used to get the permission list.
     *
     * @return The instance of the permission list.
     */
    public @NotNull List<String> getPermissionList() {
        return this.permissionList;
    }

    /**
     * Used to set the result of all the permissions.
     *
     * @param permissionChecker The instance of a permission checker
     *                          to check all the permissions.
     * @return This instance.
     */
    public @NotNull UserActionHasPermissionListEvent setResult(@NotNull PermissionChecker permissionChecker) {

        // Loop though permissions.
        // If they don't have a permission set the result to false.
        for (String permission : this.permissionList) {
            if (permissionChecker.hasPermission(permission)) continue;
            this.set(false);
            return this;
        }

        this.set(true);
        return this;
    }
}
