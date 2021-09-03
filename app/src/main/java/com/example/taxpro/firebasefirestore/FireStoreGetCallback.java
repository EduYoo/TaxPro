package com.example.taxpro.firebasefirestore;

import java.text.ParseException;

public interface FireStoreGetCallback <T>
{
    void callback(T object) throws ParseException;
}
