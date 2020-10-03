/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.business;

import com.nbcc.dataaccess.CityRepository;
import com.nbcc.models.City;
import java.util.List;

/**
 *
 * @author chris.cusack
 */
public class CityService {

    private CityRepository repo;

    public CityService() {
        repo = new CityRepository();
    }
    
    public City getCity(int id) {
        return repo.retrieveCity(id);
    }
    
    public City createCity(City c){
        return repo.createCity(c);
    }
    
    public City UpdateCity(int id, City c){
        return repo.updateCity(id, c);
    }
    
    public boolean deleteCity(int id) {
        return repo.deleteCity(id);
    }

    public List<City> getCities(){
        return repo.retrieveCities();
    }
    
    
}
