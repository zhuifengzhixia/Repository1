package com.example.ssmcrud.service;

import com.example.ssmcrud.dto.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Integer checkUserLogin(User user);
}
