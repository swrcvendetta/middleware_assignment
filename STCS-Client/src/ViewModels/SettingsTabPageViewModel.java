package ViewModels;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import Models.SettingsTabPageModel;

public class SettingsTabPageViewModel extends ViewModelBase{
    public SettingsTabPageViewModel(INotifyPropertyChanged listener) {
        super(listener);
        this.model = new SettingsTabPageModel(this, this);
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "address":
                for (INotifyPropertyChanged l : listeners)
                    l.onPropertyChanged(this, new PropertyChangedEventArgs("address", e.getPropertyValue()));
                break;
            case "port":
                for (INotifyPropertyChanged l : listeners)
                    l.onPropertyChanged(this, new PropertyChangedEventArgs("port", e.getPropertyValue()));
                break;
            default:
                break;
        }
    }
}
