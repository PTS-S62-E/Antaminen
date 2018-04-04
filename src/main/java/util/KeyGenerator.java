package util;

import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

public class KeyGenerator {

    private static KeyGenerator _instance;
    private Key key;

    public static KeyGenerator getInstance() {
        if(_instance == null) {
            new KeyGenerator();
        }

        return _instance;
    }

    private KeyGenerator() {
        _instance = this;
        key = MacProvider.generateKey();
    }

    public Key getKey() {
        return this.key;
    }


}
