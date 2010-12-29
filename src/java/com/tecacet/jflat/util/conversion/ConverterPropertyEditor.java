package com.tecacet.jflat.util.conversion;

import java.beans.PropertyEditorSupport;

import org.apache.commons.beanutils.Converter;

public class ConverterPropertyEditor extends PropertyEditorSupport {

    private Class type;
    private Converter converter;

    public ConverterPropertyEditor(Class type, Converter converter) {
        super();
        this.type = type;
        this.converter = converter;
    }

    @Override
    public void setAsText(String s) throws IllegalArgumentException {
        setValue(converter.convert(type, s));
    }
}
