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

import com.github.kerbity.kerb.client.KerbClient;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a mine mania api adapter for
 * multiple api connections.
 */
public class MineManiaAPIAdapter implements MineManiaAPI {

    private final @NotNull List<@NotNull MineManiaAPI> apiList;

    public MineManiaAPIAdapter(@NotNull List<@NotNull MineManiaAPI> apiList) {
        this.apiList = apiList;
    }

    @Override
    public @NotNull KerbClient getKerbClient() {
        if (this.apiList.isEmpty()) {
            throw new RuntimeException("Tried to get the kerb client, but there are no registered api connections. "
                    + "Please create a new instance of the mine mania api with the correct credentials."
                    + "This can be done with MineManiaAPI.create();"
            );
        }
        return this.apiList.get(0).getKerbClient();
    }

    @Override
    public @NotNull Database getDatabase() {
        if (this.apiList.isEmpty()) {
            throw new RuntimeException("Tried to get the kerb client, but there are no registered api connections. "
                    + "Please create a new instance of the mine mania api with the correct credentials."
                    + "This can be done with MineManiaAPI.create();"
            );
        }
        return this.apiList.get(0).getDatabase();
    }
}
