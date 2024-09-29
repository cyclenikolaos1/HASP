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

import javafx.animation.PauseTransition;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Splash extends Preloader {

  private Stage splashStage;

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.initStyle(StageStyle.UNDECORATED);
    splashStage = primaryStage;

    StackPane root = new StackPane();
    root.setAlignment(Pos.CENTER);

    Image image = new Image(getClass().getResourceAsStream("/logo.png"));
    Label logo = new Label("", new ImageView(image));
    root.getChildren().add(logo);

    ProgressBar progressBar = new ProgressBar();
    progressBar.setPrefWidth(200);
    progressBar.setPrefHeight(20);
    progressBar.setPadding(new Insets(0, 0, 20, 0));
    root.setAlignment(progressBar, Pos.BOTTOM_CENTER);
    root.getChildren().add(progressBar);

    Scene scene = new Scene(root);

    splashStage.setWidth(482);
    splashStage.setHeight(300);
    splashStage.setScene(scene);
    splashStage.show();
  }

  @Override
  public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
    if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
      PauseTransition pause = new PauseTransition(Duration.seconds(1));
      pause.setOnFinished(event -> {
        splashStage.hide();
      });
      pause.play();
    }
  }

}