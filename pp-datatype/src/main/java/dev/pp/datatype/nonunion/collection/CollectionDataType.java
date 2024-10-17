package dev.pp.datatype.nonunion.collection;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSName;
import dev.pp.datatype.DataType;
import dev.pp.datatype.nonunion.NonUnionDataType;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.text.location.TextLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public interface CollectionDataType<C extends Collection<E>, E> extends NonUnionDataType<C> {

    @NotNull DataType<E> getElementType();

    default boolean isNullElementsAllowed() { return getElementType().isNullable(); }

    default @NotNull Pattern getElementSeparatorPattern() {

        String pattern = OSName.isWindowsOS() ? "\\s*[,;]\\s*" : "\\s*[,:]\\s*";
        return Pattern.compile ( pattern );
    }

    @Override
    default boolean isNullable() { return false; }

    @Override
    default @NotNull C parse (
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException {

        NullDataType.checkNotNullString ( string, location );
        assert string != null;

        String[] elements = getElementSeparatorPattern().split ( string );
        return parseElements ( Arrays.asList ( elements ), string, location );
    }

    @NotNull C parseElements (
        @NotNull List<String> elements,
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException;

    default @Nullable E parseAndValidateElement (
        @NotNull String string,
        @Nullable TextLocation location ) throws DataParserException, DataValidatorException {

        return getElementType().parseAndValidate ( string, location );
    }

    @Override
    default @NotNull String objectToString ( @NotNull C collection ) {
        return objectToString ( collection, ", " );
    }

    default @NotNull String objectToString (
        @NotNull Collection<E> collection,
        @NotNull String elementSeparator ) {

        StringBuilder sb = new StringBuilder();
        for ( E element : collection ) {
            if ( ! sb.isEmpty() ) sb.append ( elementSeparator );
            sb.append ( elementToString ( element ) );
        }
        return sb.toString();
    }

    default @NotNull String elementToString ( E element ) { return getElementType().objectToString ( element ); }
}
