package com.hasp.utils;


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

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class JExeptionHandler {

    public JExeptionHandler() {

        Thread thread = new Thread();

        thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t + " throws exception: " + e);

                System.out.println(e.getStackTrace().toString());

                for (Throwable se : e.getSuppressed()) {
                    System.out.println("\t> " + se);
                }

                // Print cause, if any
                Throwable ourCause = e.getCause();
                if (ourCause != null) {
                    System.out.println(ourCause);
                }

                System.out.println(e.toString());
                //     e.printStackTrace();
                StackTraceElement[] st = e.getStackTrace();

                Throwable c = e.getCause();
                while (c != null) {
                    System.out.println(c.getMessage());
                    c = c.getCause();
                }

                if (e.getClass().getSimpleName().equals("RuntimeException")) {
                    System.out.println(((RuntimeException) e).getMessage());

                }

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Exception Dialog");
                alert.setHeaderText("Unexpected error!!!");
                alert.setContentText(e.getMessage());

// Create expandable Exception.
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("The exception stacktrace was:");

                TextArea textArea = new TextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(false);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0, 1);

                alert.getDialogPane().setExpandableContent(expContent);
                alert.showAndWait();

            }
        });
        thread.start();
    }

    public static void alertUser(String message) {
        Logger.logError(message);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Unexpected error!!!");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
