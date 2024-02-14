package ViewModels;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import Models.MainWindowModel;

public class MainWindowViewModel extends ViewModelBase {

    public MainWindowViewModel(INotifyPropertyChanged listener) {
        super(listener);
        this.model = new MainWindowModel(this, this);

        // Debug
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("view model", null));
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        System.out.println(sender.toString() + e.getPropertyName());
    }
}