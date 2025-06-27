package com.escom.backend.domain.dto.prescription;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FillPrescriptionDTO(
  @NotBlank(message = "La firma del farmacéutico es obligatoria")
  String firma_farmaceutico,

  @NotNull(message = "La fecha de surtido es obligatoria")
  LocalDate fecha_surtido
) {}
