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

package com.github.minemaniauk.api.database.record;

import com.github.minemaniauk.api.database.collection.UserCollection;
import com.github.smuddgge.squishydatabase.record.Field;
import com.github.smuddgge.squishydatabase.record.Record;
import com.github.smuddgge.squishydatabase.record.RecordFieldType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a single user record in
 * the {@link UserCollection}.
 */
public class UserRecord extends Record {

    private @Field(type = RecordFieldType.PRIMARY) String mc_uuid;
    private String mc_name;
    private final String name = "None";
    private final String email = "e@e.e";
    private long paws = 0;

    /**
     * Used to get the users minecraft uuid.
     *
     * @return The user's minecraft uuid.
     */
    public @NotNull UUID getMinecraftUuid() {
        if (this.mc_uuid == null) {
            throw new RuntimeException("Attempted to get " + this.mc_name + "'s uuid from the database but it is null.");
        }

        return UUID.fromString(this.mc_uuid);
    }

    /**
     * Used to get a users minecraft name.
     *
     * @return The users minecraft name.
     */
    public @NotNull String getMinecraftName() {
        if (this.mc_name == null) {
            throw new RuntimeException("Attempted to get uuid=" + this.mc_uuid + " minecraft name from the database but it is null.");
        }

        return this.mc_name;
    }

    /**
     * Used to get the users real life name.
     *
     * @return The user's real life name.
     */
    public @NotNull String getName() {
        if (this.name == null) {
            throw new RuntimeException("Attempted to get " + this.mc_name + "'s real life name from the database but it is null.");
        }

        return this.name;
    }

    /**
     * Used to get a user's email.
     *
     * @return The user's email.
     */
    public @NotNull String getEmail() {
        if (this.email == null) {
            throw new RuntimeException("Attempted to get " + this.mc_name + "'s email from the database but it is null.");
        }

        return this.email;
    }

    /**
     * Used to get a user's paws.
     * This is a global currency on the server.
     *
     * @return The user's paws.
     */
    public long getPaws() {
        return this.paws;
    }

    /**
     * Used to set the amount of paws a user has.
     *
     * @param amount The amount to set for this user.
     * @return This instance.
     */
    public @NotNull UserRecord setPaws(long amount) {
        this.paws = amount;
        return this;
    }

    /**
     * Used to add a certain number of paws to a user.
     * This could be negative to take paws away.
     *
     * @param amount The number of paws to add.
     * @return This instance.
     */
    public @NotNull UserRecord addPaws(long amount) {
        this.paws += amount;
        return this;
    }
}
