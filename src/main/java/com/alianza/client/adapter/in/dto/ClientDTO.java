package com.alianza.client.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO (Data Transfer Object) que representa la información de un cliente.
 * Contiene los datos personales y de contacto del cliente, así como las fechas relevantes.
 */

@Data
public class ClientDTO {

    @Schema(description = "Clave compartida para la autenticación o identificación del cliente.",
            example = "12345abcde")
    private String sharedKey;

    @Schema(description = "Identificador del negocio al que está asociado el cliente.",
            example = "BIZ12345")
    private String businessId;

    @Schema(description = "Correo electrónico del cliente.",
            example = "cliente@empresa.com")
    private String email;

    @Schema(description = "Número de teléfono del cliente.",
            example = "1234567890")
    private String phoneNumber;

    @Schema(description = "Fecha en que los datos del cliente fueron añadidos al sistema.",
            example = "2024-11-29")
    private String dataAdded;

    @Schema(description = "Fecha en que comenzó la relación con el cliente.",
            example = "2024-11-01")
    private String startDate;

    @Schema(description = "Fecha en que finaliza la relación con el cliente, si aplica.",
            example = "2024-12-31")
    private String endDate;

}