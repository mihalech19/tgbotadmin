package com.mihalech19.tgbotadmin.Interfaces;

import com.mihalech19.tgbotadmin.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
