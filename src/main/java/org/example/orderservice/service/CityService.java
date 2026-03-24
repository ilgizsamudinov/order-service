package org.example.orderservice.service;


import lombok.RequiredArgsConstructor;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.service.shipment.strategy.cdec.client.CdekApiClient;
import org.example.orderservice.service.shipment.strategy.cdec.dto.CdekCityResponse;
import org.example.orderservice.model.City;
import org.example.orderservice.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CdekApiClient cdekApiClient;
    private final CityRepository cityRepository;

    public void syncCities() {
        List<CdekCityResponse> cities = cdekApiClient.getCities();

        for (CdekCityResponse dto : cities) {
            City city = cityRepository.findByCode(dto.code())
                    .orElse(new City());

            city.setCode(dto.code());
            city.setCityUuid(dto.cityUuid());
            city.setCity(dto.city());
            city.setCountry(dto.country());
            city.setCountryCode(dto.countryCode());
            city.setRegion(dto.region());
            city.setRegionCode(dto.regionCode());
            city.setLongitude(dto.longitude());
            city.setLatitude(dto.latitude());
            city.setTimeZone(dto.timeZone());
            city.setPaymentLimit(dto.paymentLimit());

            cityRepository.save(city);
        }
    }



    public City  getCityByCode(Integer code){
        return cityRepository.findByCode(code).orElseThrow(()-> new NotFoundException("City not found"));
    }
}
