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

package com.github.minemaniauk.api.format;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a chat formatter.
 * Used in the player chat event to organise
 * the order of formatting.
 */
public class ChatFormat {

    private @NotNull Map<String, ChatFormatPriority> prefix;
    private @NotNull Map<String, ChatFormatPriority> postfix;

    /**
     * Used to create a new chat format.
     */
    public ChatFormat() {
        this.prefix = new HashMap<>();
        this.postfix = new HashMap<>();
    }

    /**
     * Used to get the instance of the prefix list
     * for a certain chat format priority.
     *
     * @param priority The chat format priority.
     * @return The instance of the prefixes.
     */
    public @NotNull List<String> getPrefixList(@NotNull ChatFormatPriority priority) {
        List<String> prefixList = new ArrayList<>();
        for (Map.Entry<String, ChatFormatPriority> entry : this.prefix.entrySet()) {
            if (!entry.getValue().equals(priority)) continue;
            prefixList.add(entry.getKey());
        }
        return prefixList;
    }

    /**
     * Used to get the instance of the postfix list
     * for a certain chat format priority.
     *
     * @param priority The chat format priority.
     * @return The instance of the postfixes.
     */
    public @NotNull List<String> getPostfixList(@NotNull ChatFormatPriority priority) {
        List<String> postfixList = new ArrayList<>();
        for (Map.Entry<String, ChatFormatPriority> entry : this.postfix.entrySet()) {
            if (!entry.getValue().equals(priority)) continue;
            postfixList.add(entry.getKey());
        }
        return postfixList;
    }

    /**
     * Used to add a prefix to the message.
     * The priority will denote how close it will be to the message.
     *
     * @param prefix   The instance of the prefix.
     * @param priority The formatting priority.
     * @return This instance
     */
    public @NotNull ChatFormat addPrefix(@NotNull String prefix, @NotNull ChatFormatPriority priority) {
        this.prefix.put(prefix, priority);
        return this;
    }

    /**
     * Used to add a postfix to the message.
     * The priority will denote how close it will be to the message.
     *
     * @param postfix  The instance of the postfix.
     * @param priority The formatting priority.
     * @return This instance
     */
    public @NotNull ChatFormat addPostfix(@NotNull String postfix, @NotNull ChatFormatPriority priority) {
        this.postfix.put(postfix, priority);
        return this;
    }

    /**
     * Used to combine this format with another format.
     *
     * @param format The instance of another format.
     * @return This format combined with another format.
     */
    public @NotNull ChatFormat combine(@NotNull ChatFormat format) {
        this.prefix.putAll(format.prefix);
        this.postfix.putAll(format.postfix);
        return this;
    }

    /**
     * Used to add the prefixes and postfixes to a message.
     *
     * @param message The instance of a message.
     * @return The result string.
     */
    public @NotNull String parse(@NotNull String message) {
        return String.join("", this.getPrefixList(ChatFormatPriority.LOW)) +
                String.join("", this.getPrefixList(ChatFormatPriority.MED)) +
                String.join("", this.getPrefixList(ChatFormatPriority.HIGH)) +
                message +
                String.join("", this.getPostfixList(ChatFormatPriority.HIGH)) +
                String.join("", this.getPostfixList(ChatFormatPriority.MED)) +
                String.join("", this.getPostfixList(ChatFormatPriority.LOW));
    }
}
