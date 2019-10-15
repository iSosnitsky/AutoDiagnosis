package ru.service;

import java.util.Set;

public interface TriageService {
    /*
    Same as above, but with city specified
     */
    Set<String> triageByCity(String cityName);

    /*
    30% patients of each city with most severe probable pathologies will be hospitalized
     */
    Set<String> triage();
}
