package org.launchcode.service;

import org.launchcode.models.User;
import org.launchcode.models.dto.UserRegDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegDTO reg);
}