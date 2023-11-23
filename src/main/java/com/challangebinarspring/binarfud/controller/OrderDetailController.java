package com.challangebinarspring.binarfud.controller;

import com.challangebinarspring.binarfud.entity.OrderDetail;
import com.challangebinarspring.binarfud.repository.OrderDetailRepository;
import com.challangebinarspring.binarfud.service.OrderDetailService;
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
@RequestMapping("/v1/order-detail")
public class OrderDetailController {
    @Autowired
    public SimpleStringUtils simpleStringUtils ;

    @Autowired
    public OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailRepository orderDetailRepository;

    @Autowired
    public Response response;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody OrderDetail request){
        try{
            return new ResponseEntity<Map>(orderDetailService.save(request), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Map>(response.errorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody OrderDetail request){
        try{
            return new ResponseEntity<Map>(orderDetailService.update(request), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Map>(response.errorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody OrderDetail request){
        try{
            return new ResponseEntity<Map>(orderDetailService.delete(request), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Map>(response.errorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id){
        try{
            return new ResponseEntity<Map>(orderDetailService.getById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Map>(response.errorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
