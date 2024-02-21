package Events;

public class ValidationChangedEventArgs extends EventArgs{
    protected String _propertyName;
    protected boolean _valid;
    protected String _validationMessage;

    public ValidationChangedEventArgs(String propertyName, boolean valid, String validationMessage) {
        this._propertyName = propertyName;
        this._valid = valid;
        this._validationMessage = validationMessage;
    }

    public String getPropertyName() {
        return this._propertyName;
    }
    public boolean isValid() {
        return this._valid;
    }
    public String getValidationMessage() {
        return this._validationMessage;
    }
}
