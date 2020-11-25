package com.gasparbarancelli.email.repository;

import com.gasparbarancelli.email.model.Email;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<Email, Long> {
}
