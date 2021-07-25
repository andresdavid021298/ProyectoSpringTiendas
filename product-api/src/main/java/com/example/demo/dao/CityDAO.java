package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.City;

public interface CityDAO extends JpaRepository<City, Integer>{

}
