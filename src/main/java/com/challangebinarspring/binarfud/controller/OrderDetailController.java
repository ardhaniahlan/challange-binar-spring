package com.challangebinarspring.binarfud.controller;

import com.challangebinarspring.binarfud.entity.OrderDetail;
import com.challangebinarspring.binarfud.repository.OrderDetailRepository;
import com.challangebinarspring.binarfud.service.OrderDetailService;
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
@RequestMapping("/v1/order-detail")
public class OrderDetailController {
    @Autowired
    public SimpleStringUtils simpleStringUtils ;

    @Autowired
    public OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailRepository orderDetailRepository;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody OrderDetail request){
        return new ResponseEntity<Map>(orderDetailService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody OrderDetail request){
        return new ResponseEntity<Map>(orderDetailService.update(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody OrderDetail request){
        return new ResponseEntity<Map>(orderDetailService.delete(request.getId()), HttpStatus.OK);
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id){
        return new ResponseEntity<Map>(orderDetailService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = {"/list-order-detail", "/list-order-detail/"})
    public ResponseEntity<Map> listQuizHeaderSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Double totalHarga,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

        Specification<OrderDetail> spec =
                ((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (quantity != null) {
                        predicates.add(criteriaBuilder.equal(root.get("quantity"), quantity));
                    }
                    if (totalHarga != null) {
                        predicates.add(criteriaBuilder.equal(root.get("totalHarga"), totalHarga));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });

        Page<OrderDetail> list = orderDetailRepository.findAll(spec, show_data);

        Map map = new HashMap();
        map.put("data",list);
        return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
    }

}
