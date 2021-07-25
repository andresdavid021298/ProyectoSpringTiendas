package com.example.demo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CityDAO;
import com.example.demo.entity.City;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("cities")
public class CityREST {

	@Autowired
	private CityDAO cityDao;

	@GetMapping
	public ResponseEntity<List<City>> listAllCities() {
		List<City> allCities = cityDao.findAll();
		return ResponseEntity.ok(allCities);
	}

	@PostMapping(value = "add_city")
	public ResponseEntity<City> addCity(@RequestBody City nueva_ciudad) {
		City nuevaCiudad = cityDao.save(nueva_ciudad);
		return ResponseEntity.ok(nuevaCiudad);
	}

	@DeleteMapping(value = "delete_city/{id}")
	public ResponseEntity<Void> deleteCity(@PathVariable("id") int id_city) {
		cityDao.deleteById(id_city);
		return ResponseEntity.ok(null);
	}

	@RequestMapping(value = "{id}")
	public ResponseEntity<City> findCityById(@PathVariable("id") int id) {
		Optional<City> cityOptional = cityDao.findById(id);
		if (cityOptional.isPresent()) {
			return ResponseEntity.ok(cityOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping
	public ResponseEntity<City> updateCity(@RequestBody City city) {
		Optional<City> cityOptional = cityDao.findById(city.getId());
		if (cityOptional.isPresent()) {
			City cityUpdate = cityOptional.get();
			cityUpdate = city;
			cityDao.save(cityUpdate);
			return ResponseEntity.ok(cityUpdate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
