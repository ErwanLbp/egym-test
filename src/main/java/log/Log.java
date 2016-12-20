package log;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Logging class, it print the logs on the console, and can also, if the constants are enabled, print it in a file or in a pop-up
 *
 * @author Erwan LBP
 * @version 1.0
 * @since 19-12-2016
 */
public class Log {

    private static boolean writeInFile = true;
    private static boolean popUp = false;
    private static String filepath = "log.txt";

    /**
     * @param activate True to activate the writing in the filepath
     */
    public static void set_writeInFile(boolean activate) {
        Log.writeInFile = activate;
    }

    /**
     * @return True if logs will be writen in the filepath, otherwise false
     */
    private static boolean is_set_writeInFile() {
        return Log.writeInFile;
    }

    /**
     * @param activate True to activate the printing of a pop-up with the logs
     */
    private static void set_popUp(boolean activate) {
        Log.popUp = activate;
    }

    /**
     * @return True if logs will be writen in a pop-up, otherwise false
     */
    private static boolean is_set_popUp() {
        return Log.popUp;
    }

    /**
     * Write the log in the filepath
     *
     * @param log The log to write
     * @return True if everything goes fine, otherwise false
     */
    private static boolean write_in_file(String log) {
        try (BufferedWriter bos = Files.newBufferedWriter(Paths.get(filepath), Charset.forName("ISO-8859-1"), StandardOpenOption.APPEND)) {
            bos.write(log);
        } catch (IOException e) {
            System.out.println("[X]\tError writing the log : " + log + "; The file " + filepath + " has to be present");
            return false;
        }
        return true;
    }

    /**
     * Print a pop-up with the log
     *
     * @param log The log to write
     * @return True if everything goes fine, otherwise false
     */
    private static boolean popUp(String log) {
        try {
            JOptionPane.showMessageDialog(null, log);
        } catch (Exception e) {
            System.out.println("[X]\tError printing on screen the log : " + log);
            return false;
        }
        return true;
    }

    /**
     * Format the log on a line or inline depending on the lineStart and lineEnd arguments<br/>
     * Then, print the log in a pop-up and/or in the console depending on the popUp and writeInFile attributes
     *
     * @param msg       The message to write
     * @param lineStart If true, will print the log with a space at the end, and without an EOL char
     * @param lineEnd   If true, will print an EOL char at the end of the message
     */
    public static void info(String msg, boolean lineStart, boolean lineEnd) {
        String msg_format;
        if (lineStart && lineEnd)
            msg_format = msg + "\n";
        else {
            if (lineStart)
                msg_format = msg + " ";
            else if (lineEnd)
                msg_format = msg + "\n";
            else
                msg_format = msg + " ";
        }
        System.out.print(msg_format);

        if (is_set_popUp())
            popUp(msg_format);
        if (is_set_writeInFile())
            write_in_file(msg_format);
    }

    /**
     * Simplify the call of the function info()
     *
     * @param msg The log to print
     */
    public static void info(String msg) {
        info(msg, true, true);
    }

    public static void error(String msg, boolean onlyLineStart) {
        String msg_format = "[X]\t" + msg;
        if (onlyLineStart)
            info(msg_format, true, false);
        else
            info(msg_format, true, true);
    }

    /**
     * Simplify the call of the function error()
     *
     * @param msg The log to print
     */
    public static void error(String msg) {
        error(msg, false);
    }

    /**
     * Format the message with "[O]" at the beginning and call info() to print it, to simplify the reading of the logs
     *
     * @param msg           The log to print
     * @param onlyLineStart If true, will not add an EOL char
     */
    public static void success(String msg, boolean onlyLineStart) {
        String msg_format = "[O]\t" + msg;
        if (onlyLineStart)
            info(msg_format, true, false);
        else
            info(msg_format, true, true);
    }

    /**
     * Simplify the call of the function success()
     *
     * @param msg The log to print
     */
    public static void success(String msg) {
        success(msg, false);
    }
}
