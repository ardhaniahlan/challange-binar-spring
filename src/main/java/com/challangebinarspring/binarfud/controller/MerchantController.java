package com.challangebinarspring.binarfud.controller;

import com.challangebinarspring.binarfud.entity.Merchant;
import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.repository.MerchantRepository;
import com.challangebinarspring.binarfud.repository.OrderRepository;
import com.challangebinarspring.binarfud.service.MerchantService;
import com.challangebinarspring.binarfud.service.OrderService;
import com.challangebinarspring.binarfud.utils.SimpleStringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/merchant")
public class MerchantController {

    @Autowired
    public SimpleStringUtils simpleStringUtils ;

    @Autowired
    public MerchantService merchantService;

    @Autowired
    public MerchantRepository merchantRepository;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody Merchant request){
        return new ResponseEntity<Map>(merchantService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Merchant request){
        return new ResponseEntity<Map>(merchantService.update(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody Merchant request){
        return new ResponseEntity<Map>(merchantService.delete(request.getId()), HttpStatus.OK);
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id){
        return new ResponseEntity<Map>(merchantService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = {"/list-merchant", "/list-merchant/"})
    public ResponseEntity<Map> listQuizHeaderSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String merchantName,
            @RequestParam(required = false) String merchantLocation,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

        Specification<Merchant> spec =
                ((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (merchantName != null && !merchantName.isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("merchantName"), merchantName));
                    }
                    if (merchantLocation != null && !merchantLocation.isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("merchantLocation"), merchantLocation));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });

        Page<Merchant> list = merchantRepository.findAll(spec, show_data);

        Map map = new HashMap();
        map.put("data",list);
        return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
    }

}
