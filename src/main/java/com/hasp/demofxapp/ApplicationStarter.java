package com.hasp.demofxapp;


/*
 *  Copyright (C) 2008-2023 Ioannis Torounoglou <johntor@ionio.gr>
 *        _       _           _
 *       | | ___ | |__  _ __ | |_ ___  _ __
 *    _  | |/ _ \| '_ \| '_ \| __/ _ \| '__|
 *   | |_| | (_) | | | | | | | || (_) | |
 *    \___/ \___/|_| |_|_| |_|\__\___/|_|
 *
 *  Project files can not be copied and/or distributed without the
 *  written permission of Ioannis Torounoglou
 *
 */
import com.hasp.mainform.MainFormView;
import com.hasp.utils.Logger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ApplicationStarter extends Application {

    private static String saveFilesPath = ".";
    private static Stage stage;
    private static Scene scene;

    public static void setSaveFilesPath(String saveFilesPath) {
        ApplicationStarter.saveFilesPath = saveFilesPath;
        updateTitle();
    }

    public static String getSaveFilesPath() {
        if (saveFilesPath.endsWith("\\")) {
            return saveFilesPath;
        } else {
            return saveFilesPath + "\\";
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationStarter.stage = stage;
        MainFormView appView = new MainFormView();
        scene = new Scene(appView.getView());
        updateTitle();

        stage.getIcons().add(new Image(ApplicationStarter.class.getResourceAsStream("/icon.png")));
        final String uri = getClass().getResource("jmodena.css").toExternalForm();
        scene.getStylesheets().add(uri);

        Preferences userPrefs = Preferences.userNodeForPackage(getClass());

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        double x = userPrefs.getDouble("stage.x", (bounds.getWidth() - 900) / 2);
        double y = userPrefs.getDouble("stage.y", (bounds.getHeight() - 800) / 2);
        double w = userPrefs.getDouble("stage.width", 900);
        double h = userPrefs.getDouble("stage.height", 800);

        stage.setScene(scene);
        stage.setX(x);
        stage.setY(y);
        stage.setWidth(w);
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setHeight(h);

        stage.show();
    }

    public static void run(String[] args) throws IOException {
        Logger.logInfo("Application Starting!");
        Logger.clear();
        loadSettings();
        //   LauncherImpl.launchApplication(ApplicationStarter.class, Splash.class, args);
        System.setProperty("javafx.preloader", Splash.class.getCanonicalName());
        launch(args);

    }

    public static void loadSettings() {
        Logger.logInfo("Loadinging settings");
        Properties settings = new Properties();
        try (FileInputStream in = new FileInputStream("settings.ini")) {
            settings.load(in);
            saveFilesPath = settings.getProperty("SaveFilesPath", ".");
        } catch (Exception ex) {
            saveFilesPath = ".";
        }
    }

    public static void saveSettings() throws FileNotFoundException, IOException {
        Properties settings = new Properties();
        try (FileOutputStream out = new FileOutputStream("settings.ini")) {
            settings.setProperty("SaveFilesPath", saveFilesPath);
            settings.store(out, "---  DemoFXApp Settings  ---");
        }
        Logger.logInfo("Saving settings");

    }

    private static void updateTitle() {
        if (saveFilesPath.contains(".") || saveFilesPath.isEmpty()) {
            stage.setTitle("DemoFXApp");
        } else {
            stage.setTitle("DemoFXApp - " + saveFilesPath);

        }
    }

    @Override
    public void stop() {
        Preferences userPrefs = Preferences.userNodeForPackage(getClass());
        userPrefs.putDouble("stage.x", stage.getX());
        userPrefs.putDouble("stage.y", stage.getY());
        userPrefs.putDouble("stage.width", stage.getWidth());
        userPrefs.putDouble("stage.height", stage.getHeight());
    }

    public static Scene getScene() {
        return scene;
    }
}
