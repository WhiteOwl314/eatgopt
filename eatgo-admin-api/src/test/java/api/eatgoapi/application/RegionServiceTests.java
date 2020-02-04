package api.eatgoapi.application;

import api.eatgoapi.domain.Region;
import api.eatgoapi.domain.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class RegionServiceTests {

    private RegionService regionService;
    @Mock
    private RegionRepository regionRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        regionService = new RegionService(regionRepository);
    }

    @Test
    public void getRegions(){

        List<Region> mockRegions = new ArrayList<>();
        mockRegions.add(Region.builder()
                .name("Seoul").build());

        given(regionRepository.findAll()).willReturn(mockRegions);

        List<Region> regions = regionService.getRegions();

        Region region = regions.get(0);
        assertThat(region.getName()).isEqualTo("Seoul");
    }

}