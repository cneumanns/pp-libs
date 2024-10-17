package dev.pp.basics.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionUtilsTest {

    @Test
    void getRootCause() {

        Exception exceptionLevel3 = new Exception ( "level 3 message" );
        assertSame ( ExceptionUtil.getRootCause ( exceptionLevel3 ), exceptionLevel3 );
        Exception exceptionLevel2 = new Exception ( "level 2 message", exceptionLevel3 );
        assertSame ( ExceptionUtil.getRootCause ( exceptionLevel2 ), exceptionLevel3 );
        Exception exceptionLevel1 = new Exception ( "level 1 message", exceptionLevel2 );
        assertSame ( ExceptionUtil.getRootCause ( exceptionLevel1 ), exceptionLevel3 );
        assertSame ( ExceptionUtil.getRootCause ( exceptionLevel3 ), exceptionLevel3 );
    }

    @Test
    void throwableToUserString() {

        Exception checkedExceptionLevel3 = new Exception ( "level 3 message" );
        String string = ExceptionUtil.throwableToUserString ( checkedExceptionLevel3 );
        assertEquals ( "level 3 message", string );

        Exception checkedExceptionLevel2 = new Exception ( "level 2 message", checkedExceptionLevel3 );
        Exception checkedExceptionLevel1 = new Exception ( "level 1 message", checkedExceptionLevel2 );
        string = ExceptionUtil.throwableToUserString ( checkedExceptionLevel1 ).replace ( "\r", "" );
        String expected = """
            level 1 message
            Cause:
            level 2 message
            Cause:
            level 3 message""";
        assertEquals ( expected, string );

        RuntimeException uncheckedException = new RuntimeException ( "unchecked exception" );
        string = ExceptionUtil.throwableToUserString ( uncheckedException );
        // System.out.println ( string );
        assertTrue ( string.contains ( "unchecked exception" ) );
        assertTrue ( string.contains ( "at " ) ); //stack trace

        uncheckedException = new RuntimeException ( "unchecked exception", checkedExceptionLevel1 );
        string = ExceptionUtil.throwableToUserString ( uncheckedException );
        // System.out.println ( "======" );
        // System.out.println ( string );
        assertTrue ( string.contains ( "unchecked exception" ) );
        assertTrue ( string.contains ( "level 1 message" ) );
        assertTrue ( string.contains ( "level 3 message" ) );
    }
}
