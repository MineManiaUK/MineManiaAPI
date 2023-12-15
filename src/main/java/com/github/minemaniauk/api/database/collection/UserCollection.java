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

package com.github.minemaniauk.api.database.collection;

import com.github.minemaniauk.api.database.record.UserRecord;
import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.interfaces.TableAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents the user collection.
 */
public class UserCollection extends TableAdapter<UserRecord> {

    @Override
    public @NotNull String getName() {
        return "users";
    }

    /**
     * Used to get the instance of a user given there minecraft uuid.
     *
     * @param minecraftUuid The players minecraft uuid.
     * @return The optional user record.
     * This will be empty if the record could not be found.
     */
    public @NotNull Optional<UserRecord> getUser(@NotNull UUID minecraftUuid) {
        String minecraftUuidString = minecraftUuid.toString();
        UserRecord user = this.getFirstRecord(new Query().match("mc_uuid", minecraftUuidString));
        if (user == null) return Optional.empty();
        return Optional.of(user);
    }
}
