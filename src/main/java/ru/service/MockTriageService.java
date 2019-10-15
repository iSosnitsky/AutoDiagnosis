package ru.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class MockTriageService implements TriageService {
    @Override
    public Set<String> triageByCity(String cityName) {
        return new HashSet<>();
    }

    @Override
    public Set<String> triage() {
        return new HashSet<>();
    }
}
