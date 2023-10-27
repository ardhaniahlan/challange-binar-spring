package com.challangebinarspring.binarfud.controller;

import com.challangebinarspring.binarfud.entity.User;
import com.challangebinarspring.binarfud.repository.UserRepository;
import com.challangebinarspring.binarfud.service.UserService;
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
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    public SimpleStringUtils simpleStringUtils ;

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody User request){
        return new ResponseEntity<Map>(userService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody User request){
        return new ResponseEntity<Map>(userService.update(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody User request){
        return new ResponseEntity<Map>(userService.delete(request.getId()), HttpStatus.OK);
    }

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") Long id){
        return new ResponseEntity<Map>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = {"/list", "/list/"})
    public ResponseEntity<Map> listQuizHeader(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {

        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);
        Page<User> list = null;

        if (username != null && !username.isEmpty()) {
            list = userRepository.getByLikeUsername("%" + username + "%", show_data);
        } else {
            list = userRepository.getALlPage(show_data);
        }
        Map map = new HashMap();
        map.put("data",list);
        return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = {"/list-user", "/list-user/"})
    public ResponseEntity<Map> listQuizHeaderSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

        Specification<User> spec =
                ((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (username != null && !username.isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("username"), username));
                    }
                    if (email != null && !email.isEmpty()) {
                        predicates.add(criteriaBuilder.equal(root.get("email"), email));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });

        Page<User> list = userRepository.findAll(spec, show_data);

        Map map = new HashMap();
        map.put("data",list);
        return new ResponseEntity<Map>(map, new HttpHeaders(), HttpStatus.OK);
    }

}
