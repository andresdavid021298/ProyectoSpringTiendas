package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Store;

public interface StoreDAO extends JpaRepository<Store, Integer> {

	@Query(value = "SELECT * FROM store WHERE id_city=?1", nativeQuery = true)
	Optional<List<Store>> findStoreByIdCity(int id_city);
	
	@Query(value = "SELECT * FROM store WHERE name_store=?1", nativeQuery = true)
	Optional<Store> findStoreByName(String name_store);
	
	@Query(value = "SELECT * FROM store AS s JOIN city AS c ON s.id_city=c.id_city WHERE c.name_city=?1", nativeQuery = true)
	Optional<List<Store>> findStoreByNameCity(String name_city);
}
