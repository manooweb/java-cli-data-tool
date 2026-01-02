package fr.manooweb.cli;

import picocli.CommandLine.ITypeConverter;

import java.util.Locale;

public class OperationNameConverter implements ITypeConverter<OperationName> {

    @Override
    public OperationName convert(String value) {
        if (value == null) {
            return null;
        }
        return OperationName.valueOf(value.trim().toUpperCase(Locale.ROOT));
    }
}
