package log;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Log {

    private static boolean writeInFile = false;
    private static boolean popUp = false;
    private static String filepath = "log.txt";

    public static void set_writeInFile(boolean activate) {
        Log.writeInFile = activate;
    }

    private static boolean is_set_writeInFile() {
        return Log.writeInFile;
    }

    private static void set_popUp(boolean activate) {
        Log.popUp = activate;
    }

    private static boolean is_set_popUp() {
        return Log.popUp;
    }

    private static boolean write_in_file(String log) {
        try (BufferedWriter bos = Files.newBufferedWriter(Paths.get(filepath), Charset.forName("ISO-8859-1"), StandardOpenOption.APPEND)) {
            bos.write(log);
        } catch (IOException e) {
            System.out.println("[X]\tError writing the log : " + log);
            return false;
        }
        return true;
    }

    private static boolean popUp(String log) {
        try {
            JOptionPane.showMessageDialog(null, log);
        } catch (Exception e) {
            System.out.println("[X]\tError printing on screen the log : " + log);
            return false;
        }
        return true;
    }

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

    public static void error(String msg) {
        error(msg, false);
    }

    public static void success(String msg, boolean onlyLineStart) {
        String msg_format = "[O]\t" + msg;
        if (onlyLineStart)
            info(msg_format, true, false);
        else
            info(msg_format, true, true);
    }

    public static void success(String msg) {
        success(msg, false);
    }

}
