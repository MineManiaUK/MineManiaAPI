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
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MineManiaAPIInstance implements MineManiaAPI {

    private static @NotNull List<MineManiaAPI> apiList = new ArrayList<>();

    private final @NotNull Configuration configuration;
    private final @NotNull KerbClient client;
    private final @NotNull Database database;

    public MineManiaAPIInstance(@NotNull Configuration configuration) {

        this.client = new KerbClient(
                configuration.getString("kerb.client_name"),
                configuration.getInteger("kerb.server_port"),
                configuration.getString("kerb.server_address"),
                new File("server-certificate.p12"),
                new File("client-certificate.p12"),
                configuration.getString("kerb.password"),
                Duration.ofMillis(configuration.getInteger("kerb.max_wait_time_millis"))
        );
    }

    @Override
    public @NotNull KerbClient getKerbClient() {
        return this.client;
    }

    @Override
    public @NotNull Database getDatabase() {
        return this.database;
    }

    /**
     * Used to get the list of active api connections.
     *
     * @return The list of api connections.
     */
    public static List<MineManiaAPI> getList() {
        return MineManiaAPIInstance.apiList;
    }
}
