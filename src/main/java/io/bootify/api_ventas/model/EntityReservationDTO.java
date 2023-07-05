package io.bootify.api_ventas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EntityReservationDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String reservationDate;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime startTime;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime endTime;

    private Long user;

}
