package com.tecacet.util.conversion.commons;

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
    public void setValue(Object value) {
        //TODO I need some generic way to handle primitive defaults
        if (type.isAssignableFrom(boolean.class) && value == null) {
            super.setValue(false);
        } else {
            super.setValue(value);
        }
    }

    @Override
    public void setAsText(String s) throws IllegalArgumentException {
        setValue(converter.convert(type, s));
    }
}
