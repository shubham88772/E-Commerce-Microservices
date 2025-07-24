package com.productservice.service.Impl;

import com.productservice.dto.ProductRequest;
import com.productservice.dto.ProductResponse;
import com.productservice.entity.Product;
import com.productservice.exception.ResourceNotFoundException;
import com.productservice.repository.ProductRepository;
import com.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = modelMapper.map(request, Product.class);
        Product saved = repository.save(product);
        return modelMapper.map(saved, ProductResponse.class);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = repository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getByCategory(String category) {
        List<Product> products = repository.findByCategory(category);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }
}
