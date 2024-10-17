package dev.pp.datatype.nonunion.scalar.impls.enumtype;

import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.text.documentation.SimpleDocumentation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumDataTypeTest {

    enum Size {
        SMALL, MEDIUM, LARGE
    }

    @Test
    void getName() throws DataValidatorException {

        EnumDataType<Size> type = new EnumDataType<> ( Size.class );
        assertEquals ( "size", type.getName() );
    }

    @Test
    void getDocumentation() throws DataValidatorException {

        EnumDataType<Size> type = new EnumDataType<> ( Size.class );
        SimpleDocumentation doc = type.getDocumentation();
        assertNotNull ( doc );
        assertEquals ( "size", doc.getTitle() );
        String description = doc.getDescription();
        assertNotNull ( description );
        System.out.println ( description );
        assertTrue ( description.contains ( "small, medium, large" ) );
    }

    @Test
    void parseWithoutValidating() throws DataParserException {

        EnumDataType<Size> type = new EnumDataType<> ( Size.class );
        assertEquals ( "MEDIUM", type.parse ( "MEDIUM", null ).toString() );
        assertEquals ( "MEDIUM", type.parse ( "medium", null ).toString() );
    }

    @Test
    void validValuesAsString() {

        EnumDataType<Size> type = new EnumDataType<> ( Size.class );
        assertEquals ( "small, medium, large (case-insensitive)", type.validValuesAsString() );
    }
}
