package dev.pp.datatype.nonunion.scalar;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.nonunion.NonUnionDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;

import java.util.function.Supplier;

public abstract class ScalarDataType<T> implements NonUnionDataType<T> {


    protected final @NotNull String name;
    public @NotNull String getName() { return name; }

    protected final @Nullable DataValidator<T> validator;
    public @Nullable DataValidator<T> getValidator() { return validator; }

    protected final @Nullable Supplier<SimpleDocumentation> documentation;
    public @Nullable SimpleDocumentation getDocumentation() { return documentation != null ? documentation.get() : null; }


    public ScalarDataType (
        @NotNull String name,
        @Nullable DataValidator<T> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        this.name = name;
        this.validator = validator;
        this.documentation = documentation;
    }


    // @Nullable -> @NotNull
    @Override
    public abstract @NotNull T parse ( @Nullable String string, @Nullable TextLocation location ) throws DataParserException;

    // only NullDataType returns true
    @Override
    public boolean isNullable() { return false; }
}
