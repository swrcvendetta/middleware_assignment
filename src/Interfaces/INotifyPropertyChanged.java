package Interfaces;

import Events.PropertyChangedEventArgs;

/*
    onPropertyChanged based on:

    https://stackoverflow.com/questions/6270132/create-a-custom-event-in-java
 */

public interface INotifyPropertyChanged {
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e);
}
