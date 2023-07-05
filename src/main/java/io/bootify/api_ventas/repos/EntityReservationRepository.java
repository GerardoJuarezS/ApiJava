package io.bootify.api_ventas.repos;

import io.bootify.api_ventas.domain.EntityReservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntityReservationRepository extends JpaRepository<EntityReservation, Long> {
}
