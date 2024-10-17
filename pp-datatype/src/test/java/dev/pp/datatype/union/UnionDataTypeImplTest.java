package dev.pp.datatype.union;

import dev.pp.datatype.CommonDataTypes;
import dev.pp.datatype.nonunion.scalar.impls.booleantype.BooleanDataType;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.nonunion.scalar.impls.string.StringDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.text.documentation.SimpleDocumentation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UnionDataTypeImplTest {

    private static final UnionDataTypeImpl<Object> STRING_OR_BOOLEAN = new UnionDataTypeImpl<> ( List.of (
        StringDataType.DEFAULT, BooleanDataType.INSTANCE ) );

    private static final UnionDataTypeImpl<Object> STRING_OR_BOOLEAN_OR_NULL = new UnionDataTypeImpl<> ( List.of (
        StringDataType.DEFAULT, BooleanDataType.INSTANCE, NullDataType.INSTANCE ) );

    @Test
    void getName() {

        assertEquals ( "string or null", CommonDataTypes.STRING_OR_NULL.getName() );
        assertEquals ( "string or boolean", STRING_OR_BOOLEAN.getName() );
        assertEquals ( "string or boolean or null", STRING_OR_BOOLEAN_OR_NULL.getName() );
    }

    @Test
    void getDocumentation() {

        SimpleDocumentation doc = STRING_OR_BOOLEAN_OR_NULL.getDocumentation();
        assertNotNull ( doc );
        assertEquals ( "string or boolean or null", doc.getTitle() );
        String description = doc.getDescription();
        assertNotNull ( description );
        System.out.println ( description );
        assertTrue ( description.contains ( "string, boolean, null" ) );
    }

    @Test
    void parse() throws DataParserException, DataValidatorException {

        Object object = CommonDataTypes.STRING_OR_NULL.parseAndValidate ( "foo", null );
        assertEquals ( "foo", (String) object );

        object = CommonDataTypes.STRING_OR_NULL.parseAndValidate ( "", null );
        assertNull ( object );
        object = CommonDataTypes.STRING_OR_NULL.parseAndValidate ( null, null );
        assertNull ( object );
        // object = CommonDataTypes.STRING_OR_NULL.parseAndValidate ( "null", null );
        // assertNull ( object );

        object = STRING_OR_BOOLEAN_OR_NULL.parseAndValidate ( "string:bar", null );
        assertEquals ( "bar", (String) object );
        object = STRING_OR_BOOLEAN_OR_NULL.parseAndValidate ( "boolean:true", null );
        assertNotNull ( object );
        assertTrue ( (Boolean) object );
        object = STRING_OR_BOOLEAN_OR_NULL.parseAndValidate ( "", null );
        assertNull ( object );

        assertThrows ( DataParserException.class,
            () -> STRING_OR_BOOLEAN.parseAndValidate ( "", null ) );

        assertThrows ( DataParserException.class,
            () -> STRING_OR_BOOLEAN_OR_NULL.parseAndValidate ( "bar", null ) );
        assertThrows ( DataParserException.class,
            () -> STRING_OR_BOOLEAN_OR_NULL.parseAndValidate ( ":string bar", null ) );
        assertThrows ( DataParserException  .class,
            () -> STRING_OR_BOOLEAN_OR_NULL.parseAndValidate ( "string bar:", null ) );
    }

    @Test
    void isNullable() {

        assertFalse ( StringDataType.DEFAULT.isNullable() );
        assertTrue ( CommonDataTypes.STRING_OR_NULL.isNullable() );
        assertTrue ( NullDataType.INSTANCE.isNullable() );

        assertFalse ( STRING_OR_BOOLEAN.isNullable() );
        assertTrue ( STRING_OR_BOOLEAN_OR_NULL.isNullable() );
    }

    @Test
    void getMemberNamesAsString() {

        assertEquals ( "string, null", CommonDataTypes.STRING_OR_NULL.getMemberNamesAsString() );
        assertEquals ( "string, boolean", STRING_OR_BOOLEAN.getMemberNamesAsString() );
        assertEquals ( "string, boolean, null", STRING_OR_BOOLEAN_OR_NULL.getMemberNamesAsString() );
    }

    @Test
    void hasMember() {

        assertTrue ( STRING_OR_BOOLEAN.hasMember ( "string" ) );
        assertTrue ( STRING_OR_BOOLEAN.hasMember ( "boolean" ) );
        assertFalse ( STRING_OR_BOOLEAN.hasMember ( "null" ) );
        assertTrue ( STRING_OR_BOOLEAN_OR_NULL.hasMember ( "null" ) );
        assertFalse ( STRING_OR_BOOLEAN_OR_NULL.hasMember ( "foo" ) );
    }

    @Test
    void requireMember() {

        assertSame ( STRING_OR_BOOLEAN.requireMember ( "string" ), StringDataType.DEFAULT );
    }
}
