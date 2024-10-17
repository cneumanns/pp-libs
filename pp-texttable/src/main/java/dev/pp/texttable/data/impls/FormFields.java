package dev.pp.texttable.data.impls;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FormFields<VALUE> {

    private final List<FormField<VALUE>> list;
    public List<FormField<VALUE>> getList() { return list; }


    public FormFields() {
        this.list = new ArrayList<>();
    }

    public FormFields<VALUE> add ( @NotNull FormField<VALUE> formField ) {
        list.add ( formField );
        return this;
    }

    public FormFields<VALUE> add ( @Nullable String label, VALUE value ) {
        return add ( new FormField<> ( label, value ) );
    }

    public FormFields<VALUE> addIfNonNullValue ( @Nullable String label, VALUE value ) {
        if ( value != null ) {
            add ( label, value );
        }
        return this;
    }
}
