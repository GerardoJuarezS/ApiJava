package io.bootify.api_ventas.rest;

import io.bootify.api_ventas.model.EntityReservationDTO;
import io.bootify.api_ventas.service.EntityReservationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/entityReservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityReservationResource {

    private final EntityReservationService entityReservationService;

    public EntityReservationResource(final EntityReservationService entityReservationService) {
        this.entityReservationService = entityReservationService;
    }

    @GetMapping
    public ResponseEntity<List<EntityReservationDTO>> getAllEntityReservations() {
        return ResponseEntity.ok(entityReservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityReservationDTO> getEntityReservation(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(entityReservationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEntityReservation(
            @RequestBody @Valid final EntityReservationDTO entityReservationDTO) {
        final Long createdId = entityReservationService.create(entityReservationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEntityReservation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EntityReservationDTO entityReservationDTO) {
        entityReservationService.update(id, entityReservationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEntityReservation(@PathVariable(name = "id") final Long id) {
        entityReservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
