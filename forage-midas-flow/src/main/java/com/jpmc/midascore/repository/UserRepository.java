package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.UserRecord;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserRecord, Long> {
    UserRecord findById(long id);
    
    // Yeh new line add karein:
    UserRecord findByName(String name);
}