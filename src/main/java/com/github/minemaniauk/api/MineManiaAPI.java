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
import com.github.kerbity.kerb.event.Event;
import com.github.kerbity.kerb.result.CompletableResultSet;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the instance of the
 * mine mania api.
 */
public interface MineManiaAPI {

    /**
     * Used to get the instance of the kerb client
     * connection.
     *
     * @return The instance of the kerb client.
     */
    @NotNull KerbClient getKerbClient();

    /**
     * Used to get the instance of the database
     * connection.
     *
     * @return The instance of the database.
     */
    @NotNull Database getDatabase();

    /**
     * Used to get the api contract.
     * Contains useful methods that will be filled out.
     *
     * @return The instance of the api contract.
     */
    @NotNull MineManiaAPIContract getContract();

    /**
     * Used to call a kerb event.
     * See {@link KerbClient#callEvent(Event)}
     * for more infomation.
     *
     * @param event The instance of the event.
     * @param <T>   The type of event.
     * @return The requested result set.
     */
    @NotNull <T extends Event> CompletableResultSet<T> callEvent(T event);

    /**
     * Used to get the instance of the
     * api connection.
     *
     * @return The instance of the api.
     */
    static @NotNull MineManiaAPI getInstance() {
        return MineManiaAPIAdapter.getInstance();
    }

    /**
     * Used to create and register a new instance of
     * an api connection.
     *
     * @param configuration The instance of the configuration file.
     * @param contract      The instance of the mine mania contract.
     * @return The instance of the api.
     */
    static @NotNull MineManiaAPI createAndSet(@NotNull Configuration configuration,
                                              @NotNull MineManiaAPIContract contract) {

        return new MineManiaAPIAdapter(configuration, contract);
    }
}
