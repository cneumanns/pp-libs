package dev.pp.scripting.bindings.core;

import javax.swing.JOptionPane;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.scripting.bindings.ScriptingBinding;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class GUIDialogsBinding implements ScriptingBinding {

    public GUIDialogsBinding() {}


    public @NotNull String bindingName () { return "GUIUtils"; }

    public void showInfo ( @NotNull String info ) {
        showInfoWithTitle ( info, "Info" );
    }

    public void showInfoWithTitle ( @NotNull String info, @Nullable String title ) {
        JOptionPane.showMessageDialog ( null, info, title, JOptionPane.INFORMATION_MESSAGE );
    }

    public void showWarning ( @NotNull String warning ) {
        showWarningWithTitle ( warning, "Warning" );
    }

    public void showWarningWithTitle ( @NotNull String warning, @Nullable String title ) {
        JOptionPane.showMessageDialog ( null, warning, title, JOptionPane.WARNING_MESSAGE );
    }

    public void showError ( @NotNull String error ) {
        showErrorWithTitle ( error, "Error" );
    }

    public void showErrorWithTitle ( @NotNull String error, @Nullable String title ) {
        JOptionPane.showMessageDialog ( null, error, title, JOptionPane.ERROR_MESSAGE );
    }

    public @Nullable String askString ( @NotNull String message ) {
        return askStringWithTitle ( message, "User Input" );
    }

    public @Nullable String askStringWithTitle ( @NotNull String message, @Nullable String title ) {

        String response = JOptionPane.showInputDialog ( null, message, title, JOptionPane.PLAIN_MESSAGE );
        return response == null || response.isEmpty() ? null : response;
    }

    public @Nullable Boolean askYesNo ( @NotNull String question ) {
        return askYesNoWithTitle ( question, "Question" );
    }

    public @Nullable Boolean askYesNoWithTitle ( @NotNull String question, @Nullable String title ) {

        int response = JOptionPane.showConfirmDialog (
            null, question, title,
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );

        return switch ( response ) {
            case JOptionPane.YES_OPTION -> true;
            case JOptionPane.NO_OPTION -> false;
            default -> null;
        };
    }

    public void openFile ( @NotNull String filePath ) throws IOException {

        Desktop desktop = Desktop.getDesktop();
        desktop.open ( new File ( filePath) );
    }

    /* TODO
        askInteger
        askDecimal
        askCharacter
        see https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html
            askFile
            askDirectory
        see https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Desktop.html
            browse(URI uri)
            browseFileDirectory(File file)
            edit(File file)
            mail()
            mail(URI mailtoURI)
            moveToTrash(File file)
            open(File file)
            print(File file)
     */
}
