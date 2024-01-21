package Models;

import java.util.Date;

public class DataPackage {
    private String _user;
    private String _message;
    private Date _timestamp;
    public DataPackage(String user, String message, Date timestamp) {
        this._user = user;
        this._message = message;
        this._timestamp = timestamp;
    }
    public String getUser() {
        return this._user;
    }
    public String getMessage() {
        return this._message;
    }
    public Date getTimestamp() {
        return this._timestamp;
    }
}
