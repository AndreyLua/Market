package com.example.market;
import java.security.Principal;
import java.util.Map;

public class EmptyPrincipal implements Principal {
    @Override
    public boolean equals(Object another) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }
}