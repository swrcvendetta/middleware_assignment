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
