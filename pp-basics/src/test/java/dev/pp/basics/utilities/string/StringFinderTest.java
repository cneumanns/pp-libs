package dev.pp.basics.utilities.string;

import dev.pp.basics.utilities.character.CharChecks;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringFinderTest {

    @Test
    public void testFindFirstIndex() {

        assertEquals ( -1, StringFinder.findFirstCharacter ( "abc", CharChecks::isSpaceOrTab ) );
        assertEquals ( 0, StringFinder.findFirstCharacter ( " abc", CharChecks::isSpaceOrTab ) );
        assertEquals ( 1, StringFinder.findFirstCharacter ( "a bc", CharChecks::isSpaceOrTab ) );
        assertEquals ( 3, StringFinder.findFirstCharacter ( "abc ", CharChecks::isSpaceOrTab ) );
    }

    @Test
    public void testFindAllIndexes() {

        assertEquals ( "[0, 5, 10]", StringFinder.findAllSubStringIndexes ( "oo234oo7o9oo", "oo" ).toString() );
        assertEquals ( "[0, 2]", StringFinder.findAllSubStringIndexes ( "oooo", "oo" ).toString() );
        assertEquals ( "[0, 2]", StringFinder.findAllSubStringIndexes ( "ooooo", "oo" ).toString() );
        assertEquals ( "[0, 1]", StringFinder.findAllSubStringIndexes ( "oo", "o" ).toString() );
        assertEquals ( "[0]", StringFinder.findAllSubStringIndexes ( "oo", "oo" ).toString() );
        assertNull ( StringFinder.findAllSubStringIndexes ( "o123o", "oo" ) );
    }
}