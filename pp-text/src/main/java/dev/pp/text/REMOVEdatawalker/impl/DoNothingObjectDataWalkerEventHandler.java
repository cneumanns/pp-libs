package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.REMOVEdatawalker.DataWalkerEventHandler;
import dev.pp.text.REMOVEdatawalker.DataWalkerException;

import java.io.IOException;

public class DoNothingObjectDataWalkerEventHandler implements DataWalkerEventHandler {

    @Override public void onStart () throws IOException, DataWalkerException {

    }

    @Override public void onEnd () throws IOException, DataWalkerException {

    }

    @Override public void onRecordStart () throws IOException, DataWalkerException {

    }

    @Override public void onRecordEnd () throws IOException, DataWalkerException {

    }

    @Override public void onRecordFieldStart ( @NotNull String fieldName, @Nullable Object forFieldValue ) throws IOException, DataWalkerException {

    }

    @Override public void onRecordFieldEnd () throws IOException, DataWalkerException {

    }

    @Override public void onBetweenNameValueOfRecordField () throws IOException, DataWalkerException {

    }

    @Override public void onBetweenRecordFields () throws IOException, DataWalkerException {

    }

    @Override public void onIterableStart () throws IOException, DataWalkerException {

    }

    @Override public void onIterableEnd () throws IOException, DataWalkerException {

    }

    @Override public void onIterableElementStart ( @Nullable Object element ) throws IOException, DataWalkerException {

    }

    @Override public void onIterableElementEnd () throws IOException, DataWalkerException {

    }

    @Override public void onBetweenIterableElements () throws IOException, DataWalkerException {

    }

    @Override public void onString ( @NotNull String string ) throws IOException, DataWalkerException {

    }

    @Override public void onNumber ( @NotNull Number number ) throws IOException, DataWalkerException {

    }

    @Override public void onBoolean ( @NotNull Boolean aBoolean ) throws IOException, DataWalkerException {

    }

    @Override public void onNull () throws IOException, DataWalkerException {

    }
}
