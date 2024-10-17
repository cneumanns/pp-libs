package dev.pp.basics.utilities.file;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileNameUtilTest {

    @Test
    void getNameWithoutExtensionAsString() {

        assertEquals ( "foo", FileNameUtils.getNameWithoutExtensionAsString ( Path.of ( "foo.txt" ) ) );
        assertEquals ( "foo", FileNameUtils.getNameWithoutExtensionAsString ( Path.of ( "foo" ) ) );
        assertEquals ( "foo", FileNameUtils.getNameWithoutExtensionAsString ( Path.of ( "foo." ) ) );
        assertEquals ( "foo.html", FileNameUtils.getNameWithoutExtensionAsString ( Path.of ( "foo.html.txt" ) ) );
    }
}
