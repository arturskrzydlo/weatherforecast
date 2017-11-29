package org.artur.skrzydlo.sharkbytetask.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    @Value("${weather.api.request.limit}")
    private int numberOfRequestPerMinute;

    private SimpleEntry<String, Integer> numberOfRequestsCache = new SimpleEntry<String, Integer>("Requests", 0);

    @Scheduled(cron = "0 */1 * * * ?")
    public void resetCache() {
        numberOfRequestsCache.setValue(0);
    }

    public void incrementLimit() {
        numberOfRequestsCache.setValue(numberOfRequestsCache.getValue() + 1);
    }

    public boolean validateLimit() {
        return numberOfRequestsCache.getValue() <= numberOfRequestPerMinute;
    }
}
