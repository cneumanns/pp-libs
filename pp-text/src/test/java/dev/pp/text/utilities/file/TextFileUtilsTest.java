package dev.pp.text.utilities.file;

import dev.pp.basics.utilities.file.TempFileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextFileUtilsTest {

    @Test
    public void testGetNthLineInFile() throws IOException {

        Path path = TempFileUtils.createNonEmptyTempTextFile ( "line 1\r\nline 2\n\nline 4\n", true );

        assertEquals ( "line 1", TextFileUtils.getNthLineInFile ( path, 1 ) );
        assertEquals ( "line 2", TextFileUtils.getNthLineInFile ( path, 2 ) );
        assertEquals ( "", TextFileUtils.getNthLineInFile ( path, 3 ) );
        assertEquals ( "line 4", TextFileUtils.getNthLineInFile ( path, 4 ) );

        /*
        assertNull ( FileUtilities.getNthLineInFile ( file, 0 ) );
        assertNull ( FileUtilities.getNthLineInFile ( file, 5 ) );
        assertNull ( FileUtilities.getNthLineInFile ( file, 6 ) );
        */
    }

/*
    @Test
    public void testGetLineWithMarkerInFile() throws IOException {

        File file = FileUtilities.createTempFile ( "line 1\r123456\nline 3\n" );
        String marker = " >>> ";

        assertEquals ( " >>> 123456", FileUtilities.getLineWithMarkerInFile ( file, 2, 1, marker ) );
        assertEquals ( "1 >>> 23456", FileUtilities.getLineWithMarkerInFile ( file, 2, 2, marker ) );
        assertEquals ( "1234 >>> 56", FileUtilities.getLineWithMarkerInFile ( file, 2, 5, marker ) );
        assertEquals ( "12345 >>> 6", FileUtilities.getLineWithMarkerInFile ( file, 2, 6, marker ) );

        assertEquals ( " >>> 123456", FileUtilities.getLineWithMarkerInFile ( file, 2, 0, marker ) );
        assertEquals ( "123456 >>> ", FileUtilities.getLineWithMarkerInFile ( file, 2, 7, marker ) );
        assertEquals ( "123456 >>> ", FileUtilities.getLineWithMarkerInFile ( file, 2, 10, marker ) );
    }
*/
}
