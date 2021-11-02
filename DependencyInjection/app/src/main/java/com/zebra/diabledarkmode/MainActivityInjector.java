package com.zebra.diabledarkmode;

import com.zebra.MainActivityViewModel;
import com.zebra.diabledarkmode.db.DataStorageModule;

import dagger.Component;

@Component(modules = {DataStorageModule.class})
public interface MainActivityInjector {
    MainActivityViewModel getMainViewModel();
    void fieldInjection(MainActivity mainActivity);
}
