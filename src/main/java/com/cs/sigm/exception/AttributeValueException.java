package com.cs.sigm.exception;

public class AttributeValueException extends RuntimeException {

    public AttributeValueException(String parameter, String value) {
        super("Attribute " + parameter + " has an invalid value: " + value + ".");
    }

}
