package io.bootify.api_ventas.service;

import io.bootify.api_ventas.domain.EntityReservation;
import io.bootify.api_ventas.domain.User;
import io.bootify.api_ventas.model.EntityReservationDTO;
import io.bootify.api_ventas.repos.EntityReservationRepository;
import io.bootify.api_ventas.repos.UserRepository;
import io.bootify.api_ventas.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EntityReservationService {

    private final EntityReservationRepository entityReservationRepository;
    private final UserRepository userRepository;

    public EntityReservationService(final EntityReservationRepository entityReservationRepository,
            final UserRepository userRepository) {
        this.entityReservationRepository = entityReservationRepository;
        this.userRepository = userRepository;
    }

    public List<EntityReservationDTO> findAll() {
        final List<EntityReservation> entityReservations = entityReservationRepository.findAll(Sort.by("id"));
        return entityReservations.stream()
                .map(entityReservation -> mapToDTO(entityReservation, new EntityReservationDTO()))
                .toList();
    }

    public EntityReservationDTO get(final Long id) {
        return entityReservationRepository.findById(id)
                .map(entityReservation -> mapToDTO(entityReservation, new EntityReservationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EntityReservationDTO entityReservationDTO) {
        final EntityReservation entityReservation = new EntityReservation();
        mapToEntity(entityReservationDTO, entityReservation);
        return entityReservationRepository.save(entityReservation).getId();
    }

    public void update(final Long id, final EntityReservationDTO entityReservationDTO) {
        final EntityReservation entityReservation = entityReservationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(entityReservationDTO, entityReservation);
        entityReservationRepository.save(entityReservation);
    }

    public void delete(final Long id) {
        entityReservationRepository.deleteById(id);
    }

    private EntityReservationDTO mapToDTO(final EntityReservation entityReservation,
            final EntityReservationDTO entityReservationDTO) {
        entityReservationDTO.setId(entityReservation.getId());
        entityReservationDTO.setReservationDate(entityReservation.getReservationDate());
        entityReservationDTO.setStartTime(entityReservation.getStartTime());
        entityReservationDTO.setEndTime(entityReservation.getEndTime());
        entityReservationDTO.setUser(entityReservation.getUser() == null ? null : entityReservation.getUser().getId());
        return entityReservationDTO;
    }

    private EntityReservation mapToEntity(final EntityReservationDTO entityReservationDTO,
            final EntityReservation entityReservation) {
        entityReservation.setReservationDate(entityReservationDTO.getReservationDate());
        entityReservation.setStartTime(entityReservationDTO.getStartTime());
        entityReservation.setEndTime(entityReservationDTO.getEndTime());
        final User user = entityReservationDTO.getUser() == null ? null : userRepository.findById(entityReservationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        entityReservation.setUser(user);
        return entityReservation;
    }

}
