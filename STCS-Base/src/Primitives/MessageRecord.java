package Primitives;

import java.io.Serializable;
import java.sql.Timestamp;

public record MessageRecord(String user, String message, Timestamp timestamp) implements Serializable {
}
