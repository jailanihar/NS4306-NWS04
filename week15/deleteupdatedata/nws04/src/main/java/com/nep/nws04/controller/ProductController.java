package com.nep.nws04.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nep.nws04.model.Product;
import com.nep.nws04.repository.ProductRepository;

@Controller
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
	@RequestMapping(value = "/")
	public String index(ModelMap modelMap) {
		List<Product> products =
			(List<Product>) productRepository.findAll();
		modelMap.put("products", products);
		return "index";
	}
	
	@RequestMapping(value = "/products")
	public String listOfProducts(ModelMap modelMap) {
		ArrayList<String> products = new ArrayList<String>();
		products.add("Samsung Galaxy S9");
		products.add("iPhone X");
		products.add("HTC One X");
		products.add("Oppo R9");
		modelMap.put("products", products);
		return "listofproducts";
	}
	
	@RequestMapping(value = "/add")
	public String add() {
		return "addproduct";
	}
	
	@RequestMapping(value = "/add/product")
	public String addProduct(
			@RequestParam(required=true) String name,
			@RequestParam(required=true) String description,
			@RequestParam(required=true) double price,
			@RequestParam(required=true) boolean instock
			) {
		Product p = 
			new Product(name, description, price, instock);
		productRepository.save(p);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/product/{id}")
	public String showProduct(
			@PathVariable long id,
			ModelMap modelMap
			) {
		Optional<Product> product =
			productRepository.findById(id);
		modelMap.put("product", product.get());
		return "product";
	}
	
	@RequestMapping(value = "/edit/product/{id}")
	public String editProduct(
			@PathVariable long id,
			@RequestParam(required=true) String name,
			@RequestParam(required=true) String description,
			@RequestParam(required=true) double price,
			@RequestParam(required=true) boolean instock
			) {
		Product product =
				productRepository.findById(id).get();
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setInStock(instock);
		productRepository.save(product);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/delete/product/{id}")
	public String deleteProduct(
			@PathVariable long id
			) {
		Product product =
				productRepository.findById(id).get();
		productRepository.delete(product);
		return "redirect:/";
	}
	
}





