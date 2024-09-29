package com.hasp.mainform;


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
import com.hasp.demofxapp.ApplicationStarter;
import com.hasp.jmvp.FXMLView;
import com.hasp.utils.JExeptionHandler;
import com.hasp.utils.Logger;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainFormPresenter implements Initializable {

    @FXML
    Button btnAdd;
    @FXML
    TextArea outputText;
    @FXML
    MenuItem mnuLoad;
    @FXML
    Menu mnuAbout;
    @FXML
    MenuItem mnuSave;
    @FXML
    MenuItem mnuClose;
    @FXML
    Button btnOk;
    @FXML
    Button btnClose;
    @FXML
    AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        Logger.logInfo("Mainform initialize");

        new JExeptionHandler();

        Label about = new Label("About");
        about.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mnuAboutClicked();
            }
        });
        mnuAbout.setGraphic(about);

    }

    public void mnuLoadPressed() throws Exception {
        Logger.logDebug("Load Pressed");

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("my files (*.ext)", "*.ext");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog((Stage) root.getScene().getWindow());
        if (file != null) {
            try {
                BufferedReader br;
                String line;
                Logger.logDebug("Loading file:" + file.getAbsolutePath());
                br = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                outputText.setText(sb.toString());
                br.close();
            } catch (IOException ex) {
                JExeptionHandler.alertUser("Error reading file: '" + file.getName() + "'");
            }
        }
    }
    
    public void mnuSavePressed() {
        Logger.logDebug("Save Pressed");
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("my files (*.ext)", "*.ext");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());
        Logger.logDebug("Selected: " + file.getAbsolutePath());
        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write("Write Test\n");
                fileWriter.write(outputText.getText() + "\n");

                fileWriter.close();
            } catch (IOException ex) {
                JExeptionHandler.alertUser("Error writing file: '" + file.getName() + "'");
            }
        }
    }

    public void btnOkPressed() {
        Logger.logInfo("Ok Pressed");
    }

    public void btnClosePressed() {
        Platform.exit();
        System.exit(0);
    }

    public void mnuAboutClicked() {
        Logger.logInfo("About Pressed");
        showForm("AboutForm");
    }

    public void showForm(String applicationName) {
        try {
            Logger.logInfo("Showing Form" + applicationName);
            Stage stage = new Stage();
            Object view = Class.forName("com.hasp." + applicationName.toLowerCase() + "." + applicationName + "View").getConstructor().newInstance();
            Scene scene = new Scene(((FXMLView) view).getView());
            stage.setTitle(applicationName);
            stage.initModality(Modality.APPLICATION_MODAL);
            ObservableList<String> stylesheets = (ApplicationStarter.getScene()).getStylesheets();
            for (String stylesheet : stylesheets) {
                scene.getStylesheets().add(stylesheet);
            }

            stage.setScene(scene);
            stage.getIcons().add(new Image(ApplicationStarter.class.getResourceAsStream("/icon.png")));
            stage.show();

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.logError("Showing: " + applicationName + " error: " + ex.getMessage());
        }
    }

}
