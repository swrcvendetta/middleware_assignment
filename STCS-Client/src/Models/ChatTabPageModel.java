package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import ViewModels.ViewModelBase;

public class ChatTabPageModel extends ModelBase{
    public ChatTabPageModel(ViewModelBase viewModel, INotifyPropertyChanged listener) {
        super(viewModel, listener);

        // Debug
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("model", null));
    }
}
