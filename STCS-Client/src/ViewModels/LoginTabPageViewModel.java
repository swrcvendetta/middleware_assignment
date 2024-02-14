package ViewModels;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import Models.LoginTabPageModel;

public class LoginTabPageViewModel extends ViewModelBase{
    public LoginTabPageViewModel(INotifyPropertyChanged listener) {
        super(listener);
        this.model = new LoginTabPageModel(this, this);

        // Debug
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("view model", null));
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        System.out.println(sender.toString() + e.getPropertyName());
    }
}
