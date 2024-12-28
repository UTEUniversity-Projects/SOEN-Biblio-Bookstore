package com.biblio.dao;

import com.biblio.entity.Staff;

import java.util.List;

public interface IStaffDAO {

    List<Staff> findAll();

    Staff findById(Long id);

    Staff findByAccountId(Long id);

    void addStaff(Staff staff);

    void updateStaff(Staff staff);

    void deleteStaff(Long id);

    void activateStaff(Staff staff);

    void deactivateStaff(Staff staff);

    Staff findByUsername(String username);

}
