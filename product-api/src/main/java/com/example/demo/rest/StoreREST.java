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

import com.example.demo.dao.StoreDAO;
import com.example.demo.entity.Store;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("stores")
public class StoreREST {

	@Autowired
	private StoreDAO storeDao;

	@GetMapping
	public ResponseEntity<List<Store>> findAllStores() {
		List<Store> list_stores = storeDao.findAll();
		return ResponseEntity.ok(list_stores);
	}

	@RequestMapping(value = "id_city/{id_city}")
	public ResponseEntity<List<Store>> findByCity(@PathVariable("id_city") int id_city) {
		Optional<List<Store>> list_stores = storeDao.findStoreByIdCity(id_city);
		if (list_stores.isPresent()) {
			return ResponseEntity.ok(list_stores.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "id/{id_store}")
	public ResponseEntity<Store> findById(@PathVariable("id_store") int id) {
		Optional<Store> store = storeDao.findById(id);
		if (store.isPresent()) {
			return ResponseEntity.ok(store.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping("name/{name_store}")
	public ResponseEntity<Store> findByName(@PathVariable("name_store") String name) {
		Optional<Store> store = storeDao.findStoreByName(name);
		if (store.isPresent()) {
			return ResponseEntity.ok(store.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "name_city/{name_city}")
	public ResponseEntity<List<Store>> findByNameCity(@PathVariable("name_city") String name_city) {
		Optional<List<Store>> list_stores = storeDao.findStoreByNameCity(name_city);
		if (list_stores.isPresent()) {
			return ResponseEntity.ok(list_stores.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "add_store")
	public ResponseEntity<Store> addStore(@RequestBody Store new_store) {
		Store ns = storeDao.save(new_store);
		return ResponseEntity.ok(ns);
	}

	@DeleteMapping(value = "delete_store/{id}")
	public ResponseEntity<Void> deleteStore(@PathVariable("id") int id_store) {
		storeDao.deleteById(id_store);
		return ResponseEntity.ok(null);
	}

	@PutMapping(value = "update_store")
	public ResponseEntity<Store> updateStore(@RequestBody Store store) {
		Optional<Store> optionalStore = storeDao.findById(store.getId());
		if (optionalStore.isPresent()) {
			Store storeUpdate = optionalStore.get();
			storeUpdate = store;
			storeDao.save(storeUpdate);
			return ResponseEntity.ok(storeUpdate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
