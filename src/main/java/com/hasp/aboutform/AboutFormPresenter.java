package com.hasp.aboutform;


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

import com.hasp.utils.JExeptionHandler;
import com.hasp.utils.Logger;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

public class AboutFormPresenter implements Initializable {

    @FXML
    WebView wvText;
    @FXML
    Button btnClose;
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        readReadme();
        Logger.logInfo("Showing about form");
    }

    private String parzeLine(String line) {
        if (line.startsWith("=")) {
            line = "<span style=\"color: #FF9600;font-weight: bold;\">" + line + "</span>";
        } else {
            line = line.replaceAll("(\\d)", "<span style=\"color: #FF8787;font-weight: bold;\">" + "$1" + "</span>");
            line = line.replaceAll("(##+)", "<span style=\"color: #99C6F6;font-weight: bold;\">" + "$1" + "</span>");
            line = line.replaceAll("(\\s(<|>|=|\\+|\\.|\\,)\\s)", "<span style=\"color: #99C6F6;font-weight: bold;\">" + "$1" + "</span>");
            line = line.replaceAll("(^#)(.*)", "<span style=\"color: #CA0000;font-weight: bold;\">" + "$1" + "</span>" + "<span style=\"color: #9BD22D;font-weight: bold;\">" + "$2" + "</span>");
            line = line.replace("|", "<span style=\"color: #FF9600;font-weight: bold;\">" + "|" + "</span>");
            line = line.replaceAll("(\\s\\-.*?:)", "<span style=\"color: #5AAAEB;font-weight: bold;\">" + "$1" + "</span>");
            line = line.replaceAll("([^\\(]\\*.*?:)", "<span style=\"color: #F0EB8E;font-weight: bold;\">" + "$1" + "</span>");
            line = line.replaceAll("(\\{.*?})", "<span style=\"color: #5AAAEB;font-weight: bold;\">" + "$1" + "</span>");
        }
        return line + "\n";
    }

    private void readReadme() {

        String backgroundColor = "#000A14";
        String fontSize = "15px";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body onload=\"toBottom()\" style=\"color:#FFFFFF;background-color:").append(backgroundColor)
          .append(";font-family:'Consolas';font-size:").append(fontSize).append(";margin:5px\">").append("\n<pre>");
        String filename = "readme.txt";

        String line = "";
        try {

            InputStream in = getClass().getResourceAsStream("/" + filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                stringBuilder.append(parzeLine(line));

            }
            br.close();
        } catch (FileNotFoundException ex) {
            JExeptionHandler.alertUser("File Not Found: '" + filename + "'");
        } catch (IOException ex) {
            JExeptionHandler.alertUser("Error reading file: '" + filename + "'");
        }
        stringBuilder.append("</pre></body></html>");
        wvText.getEngine().loadContent(stringBuilder.toString());
    }

    @FXML
    public void btnCloseClicked() {
        btnClose.getScene().getWindow().hide();
    }

}
