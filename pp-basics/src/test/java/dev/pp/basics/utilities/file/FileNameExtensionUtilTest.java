package dev.pp.basics.utilities.file;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileNameExtensionUtilTest {

    @Test
    void getExtension() {

        assertEquals ( "txt", FileNameExtensionUtil.getExtension ( "foo.txt" ) );
        assertNull ( FileNameExtensionUtil.getExtension ( "foo" ) );
        assertEquals ( "txt", FileNameExtensionUtil.getExtension ( "foo.bar.txt" ) );
        assertNull ( FileNameExtensionUtil.getExtension ( ".txt" ) );
        assertEquals ( "java", FileNameExtensionUtil.getExtension ( "utils\\Explorer.java" ) );
    }

    @Test
    void hasExtension() {

        assertTrue ( FileNameExtensionUtil.hasExtension (
            Path.of ( "utils\\Explorer.java" ), "java", true ) );
        assertFalse ( FileNameExtensionUtil.hasExtension (
            Path.of ( "utils\\Explorer.java" ), "txt", true ) );
        assertFalse ( FileNameExtensionUtil.hasExtension (
            Path.of ( "utils\\Explorer" ), "txt", true ) );
        assertTrue ( FileNameExtensionUtil.hasExtension (
            Path.of ( "utils\\Explorer.java" ), "JAVA", true ) );
        assertFalse ( FileNameExtensionUtil.hasExtension (
            Path.of ( "utils\\Explorer.java" ), "JAVA", false ) );
    }

    @Test
    void changeExtension() {

        assertEquals ( "foo.html", FileNameExtensionUtil.changeExtension ( "foo.txt", "html" ) );
        assertEquals ( "foo.html", FileNameExtensionUtil.changeExtension ( "foo", "html" ) );
        assertEquals ( "foo.bar.html", FileNameExtensionUtil.changeExtension ( "foo.bar.txt", "html" ) );
    }
}
