/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.nbcc.models.City;
import java.sql.Statement;

/**
 *
 * @author chris.cusack
 */
public class CityRepository {

    private final String SPROC_GET_CITIES = "CALL GetCities(null);";
    
    private String url;
    private String username;
    private String password;
    
    public CityRepository() {
        try {
            Properties props = DALHelper.getProperties();
            url = props.getProperty("mysql.url");
            username = props.getProperty("mysql.username");
            password = props.getProperty("mysql.password");           
        } catch (Exception e) {
        }
    }
    
    public City retrieveCity(int id){
        City city = null;
        
        try {
            String sql = "SELECT * FROM City WHERE id=" + id + "";
            
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (Statement pstmt = conn.createStatement()){
                    try (ResultSet rs = pstmt.executeQuery(sql)) {
                        while (rs.next()) {
                            //Write the while statement
                            //populate the city props with the rs column
                            city = new City();
                            city.setId(rs.getInt("id"));
                            city.setName(rs.getString("name"));
                            city.setCountryCode(rs.getString("countrycode"));
                            city.setPopulation(rs.getInt("population"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return city;
    }
    
    public boolean deleteCity(int id){
        boolean result = false;
        try {
            String sql = "DELETE FROM City WHERE id = ?"; //for MySQL
            
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, id); //Set my param by position
                    int recordsAffected = pstmt.executeUpdate();
                    
                    result = recordsAffected == 1;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return result;
    }
    
    public City createCity(City c){
        City city = null;
        
        try {
            String sql = "INSERT INTO City (Name, CountryCode, Population) values (? ,?, ?)";
            
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, c.getName());
                    pstmt.setString(2, c.getCountryCode());
                    pstmt.setInt(3, c.getPopulation());
                    
                    pstmt.execute();
                }
                try (PreparedStatement pstmt = conn.prepareStatement(SPROC_GET_CITIES)) {
                    try (ResultSet rs = (ResultSet) pstmt.executeQuery()) {
                        while (rs.next()) {
                            if(rs.isLast()){
                                city = new City();
                                city.setId(rs.getInt("id"));
                                city.setName(rs.getString("name"));
                                city.setCountryCode(rs.getString("countrycode"));
                                city.setPopulation(rs.getInt("population"));
                            }
                        }
                    }
                }
            }
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return city;
    }
    
    public City updateCity(int id, City c){
        City city = null;
        
        try {
            String sql = "UPDATE City SET Name = ?, CountryCode = ?, Population = ? WHERE ID = ?";
            
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, c.getName());
                    pstmt.setString(2, c.getCountryCode());
                    pstmt.setInt(3, c.getPopulation());
                    pstmt.setInt(4, id);
                    pstmt.execute();
                }
                try (PreparedStatement pstmt = conn.prepareStatement(SPROC_GET_CITIES)) {
                    try (ResultSet rs = (ResultSet) pstmt.executeQuery()) {
                        while (rs.next()) {
                            if(rs.isLast()){
                                city = new City();
                                city.setId(rs.getInt("id"));
                                city.setName(rs.getString("name"));
                                city.setCountryCode(rs.getString("countrycode"));
                                city.setPopulation(rs.getInt("population"));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return city;
    }
    
    public List<City> retrieveCities() {
        List<City> cities = new ArrayList();
        City city;

        try {
            Properties props = DALHelper.getProperties();

            String url = props.getProperty("mysql.url");
            String userName = props.getProperty("mysql.username");
            String password = props.getProperty("mysql.password");

            try (Connection conn = DriverManager.getConnection(url, userName, password)) {
                try (PreparedStatement pstmt = conn.prepareStatement(SPROC_GET_CITIES)) {
                    try (ResultSet rs = (ResultSet) pstmt.executeQuery()) {
                        while (rs.next()) {
                            city = new City();
                            city.setId(rs.getInt("id"));
                            city.setName(rs.getString("name"));
                            city.setCountryCode(rs.getString("countrycode"));
                            city.setPopulation(rs.getInt("population"));

                            cities.add(city);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return cities;
    }
}
