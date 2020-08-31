package de.yugimuo.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Wazed
 * Created on 31.08.2020
 */
public class Logger {

    public static void LOG(String message, LoggerType type) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss");
        String dateString = format.format(new Date());
        String logString = "[" + dateString + "] [" + type.prefix + "]: " + message;
        if (type == LoggerType.ERROR || type == LoggerType.FATAL_ERROR) {
            System.err.println(logString);
        } else {
            System.out.println(logString);
        }
    }

    public enum LoggerType {
        DEBUG("DEBUG"),
        INFO("INFO"),
        ERROR("ERROR"),
        FATAL_ERROR("FATAL");

        final String prefix;

        LoggerType(String prefix) {
            this.prefix = prefix;
        }
    }

}
