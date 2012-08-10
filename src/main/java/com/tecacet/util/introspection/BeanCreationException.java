package com.tecacet.util.introspection;

public class BeanCreationException extends RuntimeException {

    private static final long serialVersionUID = 8056763244062150780L;

    public BeanCreationException(Exception e) {
        super(e);
    }

}
