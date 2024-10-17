package dev.pp.datatype.newversion;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.List;
import java.util.function.Supplier;

public class UnionDataType_NEW extends AbstractDataType_NEW<Object> {

    // TODO? use map
    private final @NotNull List<AnyDataType<?>> members;
    public @NotNull List<AnyDataType<?>> getMembers() { return members; }


    public UnionDataType_NEW (
        @Nullable String namespace,
        @NotNull String name,
        @Nullable Supplier<SimpleDocumentation> documentationSupplier,
        @Nullable ValueValidator_NEW<Object> validator,
        @Nullable ValueReader_NEW<Object> reader,
        @Nullable ValueParser_NEW<Object> parser,
        @Nullable ValueWriter_NEW<Object> writer,
        @NotNull List<AnyDataType<?>> members ) {

        super ( namespace, name, documentationSupplier, validator, reader, parser, writer );

        // TODO check members (no doubles, no union type (or flatten))
        this.members = members;
    }

    public boolean isNullable() {

        for ( AnyDataType<?> member : members ) {
            // if ( member == NullDataType_NEW.INSTANCE ) return true;
            if ( member instanceof NullDataType_NEW ) return true;
        }
        return false;
    }
}
