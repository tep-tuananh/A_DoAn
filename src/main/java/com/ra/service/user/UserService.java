package com.ra.service.user;

import com.ra.models.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
   Optional<User> findByUserName(String userName);
   User register (User user);
   User findById(Long id);
   User save(User user);
   Page<User> getAllUser(String searchName, Integer pageNo);
}
