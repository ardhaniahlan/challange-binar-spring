package com.challangebinarspring.binarfud.repository;

import com.challangebinarspring.binarfud.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(value = "select c from User c where c.id = :userId")
    public User getById(@Param("userId") Long userId);

    @Query(value = "select e from User e where LOWER(e.username) like LOWER(:nameParam)")
    public Page<User> getByLikeUsername(@Param("nameParam") String nameParam, Pageable pageable);

    @Query(value = "select e from User e ")
    public Page<User> getALlPage(Pageable pageable);

    //Store prosedure
    @Query(value = "select * from getemployee6()",nativeQuery = true)
    public List<Object> getListSP();

    @Modifying
    @Procedure("insert1")
    void saveEmployeeSP(@Param("resid") Long resid,@Param("rqnama") String rqnama);
    @Modifying
    @Procedure("update_employee")
    void updateEmployeeSP(@Param("resid") Long resid,@Param("rqnama") String rqnama);
    @Modifying
    @Procedure("deleted_employee1")
    void deleted_employee1(@Param("resid") Long resid);
    @Modifying
    @Procedure("getemployeebyid")
    Object getemployeebyid(@Param("resid") Long resid);
    @Modifying
    @Procedure("getemployee6")
    List<Object> getemployee6();

}
