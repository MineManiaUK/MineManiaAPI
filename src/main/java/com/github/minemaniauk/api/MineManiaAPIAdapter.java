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
import com.github.kerbity.kerb.packet.event.Event;
import com.github.kerbity.kerb.packet.event.Priority;
import com.github.kerbity.kerb.result.CompletableResultSet;
import com.github.minemaniauk.api.database.collection.GameRoomCollection;
import com.github.minemaniauk.api.database.collection.UserCollection;
import com.github.minemaniauk.api.game.GameManager;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishydatabase.DatabaseCredentials;
import com.github.smuddgge.squishydatabase.DatabaseFactory;
import com.github.smuddgge.squishydatabase.console.Console;
import com.github.smuddgge.squishydatabase.interfaces.Database;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.time.Duration;

/**
 * Represents the mine mania api adapter.
 * Used to create the api connection.
 */
public class MineManiaAPIAdapter implements MineManiaAPI {

    private static @Nullable MineManiaAPI instance;

    private final @NotNull Configuration configuration;
    private final @NotNull MineManiaAPIContract contract;
    private final @NotNull KerbClient client;
    private final @NotNull Database database;
    private final @NotNull GameManager gameManager;

    /**
     * Used to create a new instance of an api connection.
     *
     * @param configuration The instance of the configuration.
     * @param contract      The instance of the contract.
     */
    public MineManiaAPIAdapter(@NotNull Configuration configuration, @NotNull MineManiaAPIContract contract) {
        try {
            this.configuration = configuration;
            this.contract = contract;

            // Create the instance of the kerb client.
            this.client = new KerbClient(
                    configuration.getString("kerb.client_name"),
                    configuration.getInteger("kerb.server_port"),
                    configuration.getString("kerb.server_address"),
                    new File(configuration.getString("kerb.client_certificate_path")),
                    new File(configuration.getString("kerb.server_certificate_path")),
                    configuration.getString("kerb.password"),
                    Duration.ofMillis(configuration.getInteger("kerb.max_wait_time_millis")),
                    true,
                    Duration.ofSeconds(10),
                    -1
            );
            this.client.connect();
            this.client.registerListener(Priority.LOW, contract);

            //  Create the instance of the database.
            this.database = DatabaseFactory.MONGO.create(DatabaseCredentials.MONGO(
                    configuration.getString("database.connection_string"),
                    configuration.getString("database.database_name")
            )).setup();

            // Set up the tables.
            this.database.createTable(new UserCollection());
            this.database.createTable(new GameRoomCollection());

            // Set up the game manager.
            this.gameManager = new GameManager(this);

            // Set the instance of the mine mania api.
            MineManiaAPIAdapter.setInstance(this);

        } catch (Exception exception) {
            Console.log("Client Name: " + configuration.getString("kerb.client_name"));
            Console.log("Server Port: " + configuration.getInteger("kerb.server_port"));
            Console.log("Server Address: " + configuration.getString("kerb.server_address"));
            Console.log("Server Certificate Path: " + new File(configuration.getString("kerb.server_certificate_path")).getAbsolutePath());
            Console.log("Client Certificate Path: " + new File(configuration.getString("kerb.client_certificate_path")).getAbsolutePath());
            Console.log("Password: " + configuration.getString("kerb.password"));
            Console.log("Max Wait Time Millis: " + Duration.ofMillis(configuration.getInteger("kerb.max_wait_time_millis")));
            throw new RuntimeException(exception);
        }
    }

    @Override
    public @NotNull String getServerName() {
        final String name = this.configuration.getString("server_name", "null");
        if (name == null) throw new RuntimeException("Server name is null. Please change this in the MineManiaAPI -> config.yaml");
        return name;
    }

    @Override
    public @NotNull KerbClient getKerbClient() {
        return this.client;
    }

    @Override
    public @NotNull Database getDatabase() {
        return this.database;
    }

    @Override
    public @NotNull MineManiaAPIContract getContract() {
        return this.contract;
    }

    @Override
    public @NotNull GameManager getGameManager() {
        return this.gameManager;
    }

    @Override
    public @NotNull <T extends Event> CompletableResultSet<T> callEvent(T event) {
        return this.client.callEvent(event);
    }

    /**
     * Used to get the list of active api connections.
     *
     * @return The list of api connections.
     */
    public static @NotNull MineManiaAPI getInstance() {
        if (MineManiaAPIAdapter.instance == null) {
            throw new RuntimeException("Attempted to get the instance of the mine mania api but was null.");
        }

        return MineManiaAPIAdapter.instance;
    }

    /**
     * Used to set the instance of the mine mania api.
     *
     * @param instance The instance of the api.
     */
    public static void setInstance(@Nullable MineManiaAPI instance) {
        MineManiaAPIAdapter.instance = instance;
    }
}
