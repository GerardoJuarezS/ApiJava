package io.bootify.api_ventas.repos;

import io.bootify.api_ventas.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
