package dev.pp.gui;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class GUIUtil {

    public static void showTextInWindow (
        @NotNull String text,
        @Nullable String windowTitle,
        boolean exitSystemOnWindowClose ) {

        showTextInWindow ( text, windowTitle, exitSystemOnWindowClose, 10, 80, true );
    }

    public static void showTextInWindow (
        @NotNull String text,
        @Nullable String windowTitle,
        boolean exitSystemOnWindowClose,
        int rows,
        int columns,
        boolean useMonospaceFont ) {

        var textArea = new JTextArea ( text, rows, columns );
        if ( useMonospaceFont ) {
            textArea.setFont ( new Font ( Font.MONOSPACED, Font.BOLD, 14 ) );
        }
        textArea.setLineWrap ( true );
        textArea.setWrapStyleWord ( true );

        var panel = new JScrollPane ( textArea );

        var frame = new JFrame ( windowTitle );
        frame.add ( panel );
        if ( exitSystemOnWindowClose ) {
            frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        }
        frame.pack();
        frame.setLocationRelativeTo ( null ); //center window on screen
        frame.setVisible ( true );
    }
}
