package ru.controller;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.service.TriageService;

@RestController("/triage")
public class TriageController {

    private final TriageService ts;

    @Autowired
    public TriageController(TriageService ts) {
        this.ts = ts;
    }
    
    @RequestMapping("/triage")
    public Set<String> index (){
        return ts.triage();
    }

    @RequestMapping("/triage/{cityName}")
    public Set<String> indexByCity(@PathVariable("cityName") String cityName) {
        return ts.triageByCity(cityName);
    }

}