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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ProductsDAO;
import com.example.demo.entity.Product;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("products")
public class productsREST {

	@Autowired
	private ProductsDAO productDao;

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {

		List<Product> todos_productos = productDao.findAll();
		return ResponseEntity.ok(todos_productos);

	}

	@RequestMapping(value = "{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable("productId") Long id) {
		Optional<Product> optionalProduct = productDao.findById(id);
		if (optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@RequestMapping(value = "letter/{productName}")
	public ResponseEntity<List<Product>> findProductByName(@PathVariable("productName") String product_name) {
		Optional<List<Product>> optionalProduct = productDao.searchByLetter(product_name);
		if (optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@RequestMapping(value = "name/{productName}")
	public ResponseEntity<Product> searchByLetter(@PathVariable("productName") String product_name) {
		Optional<Product> optionalProduct = productDao.findProductByName(product_name);
		if (optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@RequestMapping(value = "id_store/{id_s}")
	public ResponseEntity<List<Product>> listProductsByStore(@PathVariable("id_s") int id_store) {
		List<Product> list_produts = productDao.searchByStore(id_store);
		return ResponseEntity.ok(list_produts);
	}

	@PostMapping
	public ResponseEntity<Product> insertProduct(@RequestBody Product newProduct) {
		Product p = productDao.save(newProduct);
		return ResponseEntity.ok(p);
	}

	@DeleteMapping(value = "{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long id) {
		productDao.deleteById(id);
		return ResponseEntity.ok(null);
	}

	@DeleteMapping(value = "discontinue/{id_store}/{id_product}")
	public ResponseEntity<Void> discontinueProduct(@PathVariable("id_store") int id_store, @PathVariable("id_product") int id_product){
		productDao.discontinueProduct(id_product, id_store);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		Optional<Product> optionalProduct = productDao.findById(product.getId());
		if (optionalProduct.isPresent()) {
			Product product_update = optionalProduct.get();
			product_update.setName(product.getName());
			productDao.save(product_update);
			return ResponseEntity.ok(product_update);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
