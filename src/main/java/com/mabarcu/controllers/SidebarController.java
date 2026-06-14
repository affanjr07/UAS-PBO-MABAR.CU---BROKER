package com.mabarcu.controllers;

import com.mabarcu.MainApp;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;

public class SidebarController extends BaseNav {

    @FXML
    @Override
    public void logout() {
        if (AppData.currentUser != null) {
            Database.setOffline(AppData.currentUser.getId());
        }

        AppData.currentUser = null;
        MainApp.setRoot("LoginView");
    }
}
