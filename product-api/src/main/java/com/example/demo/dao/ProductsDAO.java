package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Product;

public interface ProductsDAO extends JpaRepository<Product, Long> {

	// Consulta para buscar un producto por su nombre
	@Query(value = "SELECT * FROM products WHERE name = ?1", nativeQuery = true)
	Optional<Product> findProductByName(String product_name);

	// Consulta para buscar productos que tengan ciertas letras
	@Query(value = "SELECT * FROM products WHERE name LIKE %?1%", nativeQuery = true)
	Optional<List<Product>> searchByLetter(String product_name);

	// Consulta para listar los productos de una tienda
	@Query(value = "SELECT * FROM products AS p JOIN stores_products AS sp ON p.id=sp.fk_product WHERE fk_store= ?1", nativeQuery = true)
	List<Product> searchByStore(int id_store);

	// Consulta que permite descontinuar un producto de una tienda
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM stores_products WHERE fk_product = ?1 AND fk_store = ?2", nativeQuery = true)
	void discontinueProduct(int id_product, int id_store);
}
