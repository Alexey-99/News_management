package com.mjc.school.service.user;

import com.mjc.school.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean create(User user);

}
