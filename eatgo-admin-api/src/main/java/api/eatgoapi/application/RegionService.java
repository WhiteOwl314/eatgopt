package api.eatgoapi.application;

import api.eatgoapi.domain.Region;
import api.eatgoapi.domain.RegionRepository;
import api.eatgoapi.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {

    private RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions() {

        List<Region> regions = regionRepository.findAll();


        return regions;
    }
}
