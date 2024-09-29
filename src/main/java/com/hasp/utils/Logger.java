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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public enum LogLevels {
        DEBUG, INFO, WARN, ERROR
    };
    private static String filename = "log.log";
    private static boolean inludeDate = false;

    private static boolean haltOnError = false;

    private static LogLevels logLevelWrite = LogLevels.DEBUG;
    private static LogLevels logLevelDisplay = LogLevels.DEBUG;
//   0 DEBUG
//   1 INFO
//   2 WARN
//   3 ERROR

    public static void setFilename(String fname) {
        filename = fname;
    }

    public static void setLogLevelWrite(LogLevels loggingLevelWrite) {
        logLevelWrite = loggingLevelWrite;
        System.out.println("Log Level Write:" + logLevelWrite.name());
    }

    public static void setLogLevelWrite(String loggingLevelWrite) {
        switch (loggingLevelWrite.toLowerCase()) {
            case "debug":
                setLogLevelWrite(LogLevels.DEBUG);
                break;
            case "info":
                setLogLevelWrite(LogLevels.INFO);
                break;
            case "warn":
                setLogLevelWrite(LogLevels.WARN);
                break;
            case "error":
                setLogLevelWrite(LogLevels.ERROR);
                break;
            default:
                setLogLevelWrite(LogLevels.DEBUG);
        }
    }

    public static void setLogLevelDisplay(LogLevels loggingLevelDisplay) {
        logLevelDisplay = loggingLevelDisplay;
        System.out.println("Log Level Display:" + logLevelDisplay.name());
    }

    public static void setLogLevelDisplay(String loggingLevelDisplay) {
        switch (loggingLevelDisplay.toLowerCase()) {
            case "debug":
                setLogLevelDisplay(LogLevels.DEBUG);
                break;
            case "info":
                setLogLevelDisplay(LogLevels.INFO);
                break;
            case "warn":
                setLogLevelDisplay(LogLevels.WARN);
                break;
            case "error":
                setLogLevelDisplay(LogLevels.ERROR);
                break;
            default:
                throw new AssertionError();
        }
    }

    private static void log(String msg) {
        String s = "";
        try {
            DateFormat formatter;
            Date date = new Date();
            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
            s = formatter.format(date);

            FileWriter fileWriter = new FileWriter(getFilename(), true);
            BufferedWriter out = new BufferedWriter(fileWriter);

            if (!msg.contains("\n")) {
                out.write(s + " " + msg + "\n");
            } else {
                int c = 0;
                String[] lines = msg.split("\n");
                out.write(s);
                for (String line : lines) {
                    if (c > 0) {
                        out.write("                       ");
                    }
                    out.write(" " + line + "\n");
                    c++;
                }

            }

            out.flush();
            out.close();
            fileWriter.close();
        } catch (Exception ex) {
            System.out.println(s + " " + msg);
        }
    }

    public static void inludeDate() {
        inludeDate = true;
    }

    public static void clear() {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.close();
            fileWriter.close();
        } catch (Exception ex) {

        }
    }

    private static String getFilename() {
        if (!inludeDate) {
            return filename;
        } else {
            DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
            return formatter.format(new Date()) + filename;
        }
    }

    public static void logDebug(String msg) {
        if (logLevelWrite.ordinal() <= LogLevels.DEBUG.ordinal()) {
            log("DEBUG: " + msg);
        }
        if (logLevelDisplay.ordinal() <= LogLevels.DEBUG.ordinal()) {
            System.out.println("DEBUG: " + msg);
        }
    }

    public static void logInfo(String msg) {
        if (logLevelWrite.ordinal() <= LogLevels.INFO.ordinal()) {
            log("INFO:  " + msg);
        }
        if (logLevelDisplay.ordinal() <= LogLevels.INFO.ordinal()) {
            System.out.println("INFO:  " + msg);
        }
    }

    public static void logWarn(String msg) {
        if (logLevelWrite.ordinal() <= LogLevels.WARN.ordinal()) {
            log("WARN:  " + msg);
        }
        if (logLevelDisplay.ordinal() <= LogLevels.WARN.ordinal()) {
            System.out.println("WARN:  " + msg);
        }
    }

    public static void logError(String msg) {
        if (logLevelWrite.ordinal() <= LogLevels.ERROR.ordinal()) {
            log("ERROR: " + msg);
        }
        if (logLevelDisplay.ordinal() <= LogLevels.ERROR.ordinal()) {
            System.out.println("ERROR: " + msg);
        }
        if (haltOnError) {
            System.exit(0);
        }
    }

    /**
     * @param aHaltOnErroe the haltOnErroe to set
     */
    public static void setHaltOnError() {
        haltOnError = true;
    }

    public static void removeHaltOnError() {
        haltOnError = false;
    }
}
