package com.challangebinarspring.binarfud.controller;

import com.challangebinarspring.binarfud.entity.Product;
import com.challangebinarspring.binarfud.repository.ProductRepository;
import com.challangebinarspring.binarfud.service.ProductService;
import com.challangebinarspring.binarfud.utils.SimpleStringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequestMapping("/v1/product")
public class ProductController {
    @Autowired
    public SimpleStringUtils simpleStringUtils ;

    @Autowired
    public ProductService productService;

    @Autowired
    public ProductRepository productRepository;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody Product request){
        return new ResponseEntity<Map>(productService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Product request){
        return new ResponseEntity<Map>(productService.update(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody Product request){
        return new ResponseEntity<Map>(productService.delete(request.getId()), HttpStatus.OK);
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id){
        return new ResponseEntity<Map>(productService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = {"/list-order-detail", "/list-order-detail/"})
    public ResponseEntity<Map> listQuizHeaderSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

        Specification<Product> spec =
                ((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (productName != null && !productName.isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("productName"), productName));
                    }
                    if (price != null) {
                        predicates.add(criteriaBuilder.equal(root.get("price"), price));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });

        Page<Product> list = productRepository.findAll(spec, show_data);

        Map map = new HashMap();
        map.put("data",list);
        return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
    }
}
