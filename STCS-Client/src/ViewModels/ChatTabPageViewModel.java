package ViewModels;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import Models.ChatTabPageModel;

public class ChatTabPageViewModel extends ViewModelBase {
    public ChatTabPageViewModel(INotifyPropertyChanged listener) {
        super(listener);
        this.model = new ChatTabPageModel(this, this);

        // Debug
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("view model", null));
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {

    }
}
