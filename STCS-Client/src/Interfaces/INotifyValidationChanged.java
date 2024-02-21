package Interfaces;

import Events.ValidationChangedEventArgs;

public interface INotifyValidationChanged {
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e);
}
