package com.contactvault.cvault.helper;

public class ResourceNotFoundException extends RuntimeException{


    //constructor
    public ResourceNotFoundException(String message) {
        super(message);
        }

    //no args constructor
    public ResourceNotFoundException() {
        super();
        }
        
    
}
