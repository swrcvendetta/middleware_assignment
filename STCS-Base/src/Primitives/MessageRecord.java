/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package Primitives;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The MessageRecord class represents a record for storing message details.
 * It is a serializable record class with information about the user, message content, and timestamp.
 *
 * @param user      The user associated with the message.
 * @param message   The content of the message.
 * @param timestamp The timestamp indicating when the message was created.
 */
public record MessageRecord(String user, String message, Timestamp timestamp) implements Serializable {
}
