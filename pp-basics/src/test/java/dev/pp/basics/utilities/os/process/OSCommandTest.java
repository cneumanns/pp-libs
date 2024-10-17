package dev.pp.basics.utilities.os.process;

import dev.pp.basics.utilities.file.TempFileUtils;
import dev.pp.basics.utilities.os.OSDirectories;
import dev.pp.basics.utilities.os.OSName;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.basics.utilities.string.StringTruncator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OSCommandTest {

    @Test
    public void testStartAndContinue() throws IOException {

        Path path = TempFileUtils.createNonEmptyTempTextFile (
            "line 1" + StringConstants.OS_LINE_BREAK + "line 2", false );

        // open file in VSCode at line 2, column 4
        String command = "cmd.exe /c code --goto \"" + path + ":2:4\"";
        // DebugUtils.printNameValue ( "OSCommand", OSCommand );

        if ( false ) OSCommand.startAndContinue ( command );
    }

    @Test
    public void testRunWithStrings() throws IOException, InterruptedException {

        if ( OSName.isWindowsOS() ) {

            Path workingDir = OSDirectories.currentWorkingDirectory();
            OSCommand.OSCommandResult result = OSCommand.runWithStrings (
                new String[]{"cmd.exe", "/c", "cd"}, null );
            assertFalse ( result.hasError() );
            assertTrue ( result.hasOutput() );
            assertEquals ( 0, result.exitCode () );
            String stdout = result.stdout();
            assertNotNull ( stdout );
            stdout = StringTruncator.removeOptionalLineBreakAtEnd ( stdout );
            assertEquals ( workingDir.toString(), stdout );
            assertNull ( result.stderr() );

            result = OSCommand.runWithStrings (
                new String[]{"cmd.exe", "/c", "cd inexistent_directory"}, null );
            assertEquals ( 1, result.exitCode() );
            assertNull ( result.stdout() );
            assertNotNull ( result.stderr() );
            // System.out.println ( result.stderr() );

/*
            String input = "foo";
            result = OSCommand.runWithStrings (
                new String[]{"cmd.exe", "/c", "set /p input=\"\" && echo \"%input%\""}, input );
            assertEquals ( 0, result.exitCode () );
            stdout = result.stdout();
            assertNotNull ( stdout );
            assertEquals ( input, stdout );
            assertNull ( result.stderr() );

 */
        }
    }

}
