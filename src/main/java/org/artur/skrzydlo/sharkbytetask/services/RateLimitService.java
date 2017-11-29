package org.artur.skrzydlo.sharkbytetask.services;

public interface RateLimitService {

    void incrementLimit();

    boolean validateLimit();
}
