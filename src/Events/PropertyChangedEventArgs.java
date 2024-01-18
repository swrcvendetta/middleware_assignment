package Events;

import jdk.jfr.Event;

public class PropertyChangedEventArgs extends EventArgs {
    protected String _propertyName;
    public PropertyChangedEventArgs(String propertyName) {
        this._propertyName = propertyName;
    }

    public String getPropertyName() {
        return this._propertyName;
    }
}
