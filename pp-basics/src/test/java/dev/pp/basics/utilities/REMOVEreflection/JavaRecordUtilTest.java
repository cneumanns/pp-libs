package dev.pp.basics.utilities.REMOVEreflection;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JavaRecordUtilTest {

    record TestRecord (
        String stringField,
        int intField ) {}

    @Test
    void fieldValues() throws Exception {

        TestRecord record = new TestRecord ( "foo", 123 );
        Map<String, Object> fieldValues = JavaRecordUtil.fields ( record );
        assertEquals ( 2, fieldValues.size() );
        assertEquals ( "foo", fieldValues.get ( "stringField" ) );
        assertEquals ( 123, fieldValues.get ( "intField" ) );
    }

    @Test
    void createRecord() throws Exception {

        TestRecord record = JavaRecordUtil.createRecord (
            TestRecord.class, List.of ( "bar", 111 ) );
        assertEquals ( "bar", record.stringField );
        assertEquals ( 111, record.intField );
    }
}
