package ru.job.step.city;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import ru.dao.entity.City;
import ru.dao.repository.CityRepository;

import java.util.List;
import java.util.Map;

@Slf4j
public class CityWriter implements ItemWriter<Map<String, String>> {
    private final CityRepository cityRepository;
    private final static String NAME_FIELD = "name";
    private final static String REGION_FIELD = "region";

    public CityWriter(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void write(List<? extends Map<String, String>> list) throws Exception {
        list.forEach(cityMap -> {
            City city = cityRepository.findFirstByName(cityMap.get(NAME_FIELD))
                    .orElseGet(() -> {
                        City newcity = new City();
                        newcity.setRegion(cityMap.get(REGION_FIELD));
                        newcity.setName(cityMap.get(NAME_FIELD));
                        newcity.setUpdates(-1);
                        return newcity;
                    });
            try {
                city.setUpdates(city.getUpdates()+1);
                cityRepository.save(city);
            } catch (Exception e) {
                log.error("Unable to save city {}", city.getName());
                e.printStackTrace();
            }
        });
    }
}
