package com.challangebinarspring.binarfud.controller;

import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.repository.OrderRepository;
import com.challangebinarspring.binarfud.service.OrderService;
import com.challangebinarspring.binarfud.utils.Response;
import com.challangebinarspring.binarfud.utils.SimpleStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    @Autowired
    public SimpleStringUtils simpleStringUtils ;

    @Autowired
    public OrderService orderService;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public Response response;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody Order request){
        try{
            return new ResponseEntity<Map>(orderService.save(request), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Map>(response.errorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Order request){
        try{
            return new ResponseEntity<Map>(orderService.update(request), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Map>(response.errorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody Order request){
        try{
            return new ResponseEntity<Map>(orderService.delete(request), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Map>(response.errorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<Map>(orderService.getById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Map>(response.errorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/list-order", "/list-order/"})
    public ResponseEntity<Map> listQuizHeaderSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String destinationAddress,
            @RequestParam(required = false) String orderTime,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

        Specification<Order> spec =
                ((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (destinationAddress != null && !destinationAddress.isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("destinationAddress"), destinationAddress));
                    }
                    if (orderTime != null && !orderTime.isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("orderTime"), orderTime));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });

        Page<Order> list = orderRepository.findAll(spec, show_data);

        Map map = new HashMap();
        map.put("data",list);
        return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
    }
}
