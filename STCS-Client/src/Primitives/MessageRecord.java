package Primitives;

import java.sql.Timestamp;

public record MessageRecord(String user, String message, Timestamp timestamp) {
}
