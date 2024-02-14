package Events;

public class PropertyChangedEventArgs extends EventArgs {
    protected String _propertyName;
    protected Object _value;

    public PropertyChangedEventArgs(String propertyName, Object value) {
        this._propertyName = propertyName;
        this._value = value;
    }

    public String getPropertyName() {
        return this._propertyName;
    }
    public Object getPropertyValue() {
        return this._value;
    }
}
