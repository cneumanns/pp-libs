package dev.pp.datatype.nonunion.collection;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.text.location.TextLocation;

import java.util.ArrayList;
import java.util.List;

public interface ListDataType<E> extends CollectionDataType<List<E>, E> { // extends CollectionDataType<T> {

    default @NotNull List<E> parseElements (
        @NotNull List<String> elements,
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException {

        boolean nullElementsAllowed = isNullElementsAllowed();

        List<E> list = new ArrayList<> ();
        for ( String element : elements ) {
            if ( element != null && ! element.isEmpty() ) {
                try {
                    list.add ( parseAndValidateElement ( element, location ) );
                } catch ( DataValidatorException e ) {
                    throw new DataParserException ( e, element, location );
                }
            } else {
                if ( nullElementsAllowed ) {
                    list.add ( null );
                } else {
                    throw new DataParserException (
                        "An empty (null) element is not allowed.",
                        "INVALID_EMPTY_ELEMENT",
                        string, location );
                }
            }
        }

        if ( ! list.isEmpty() ) {
            return list;
        } else {
            throw new DataParserException (
                "An empty (null) collection is not allowed.",
                "INVALID_EMPTY_COLLECTION",
                string, location );
        }
    }
}
