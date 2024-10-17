package dev.pp.gui;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import javax.swing.*;

public class DialogUtil {

    public static void showInfo (
        @NotNull String info,
        @Nullable String title ) {

        JOptionPane.showMessageDialog (
            null, info, title, JOptionPane.INFORMATION_MESSAGE );
    }

    public static void showWarning (
        @NotNull String warning,
        @Nullable String title ) {

        JOptionPane.showMessageDialog (
            null, warning, title, JOptionPane.WARNING_MESSAGE );
    }

    public static void showError (
        @NotNull String error,
        @Nullable String title ) {

        JOptionPane.showMessageDialog (
            null, error, title, JOptionPane.ERROR_MESSAGE );
    }

    public static @Nullable Boolean askYesNo (
        @NotNull String message,
        @Nullable String title ) {

        int response = JOptionPane.showConfirmDialog (
            null, message, title,
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );

        if ( response == JOptionPane.YES_OPTION ) {
            return true;
        } else if ( response == JOptionPane.NO_OPTION ) {
            return false;
        } else {
            return null;
        }
    }

    public static @Nullable String askString (
        @NotNull String message,
        @Nullable String title ) {

        String response = JOptionPane.showInputDialog (
            null, message, title,
            JOptionPane.PLAIN_MESSAGE );

        if ( response != null && ! response.isEmpty() ) {
            return response;
        } else {
            return null;
        }
    }
}
