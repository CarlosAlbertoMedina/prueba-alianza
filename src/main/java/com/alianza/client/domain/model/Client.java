package com.alianza.client.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.Date;

/**
 *
 * @author Carlos Medina
 * @version 1.0
 * @since 2024-11-28
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    /**
     * ID único del cliente generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Clave compartida que sirve para identificar al cliente de manera única.
     * Debe ser una cadena no vacía.
     */
    @NotNull(message = "Shared Key is required.")
    @Size(min = 1, message = "Shared Key cannot be empty.")
    private String sharedKey;

    /**
     * Business ID
     * Se trata del nombre de la empresa o del cliente en formato texto.
     * No puede ser vacío.
     */
    @NotNull(message = "Business ID is required.")
    @Size(min = 1, message = "Business ID cannot be empty.")
    @Column(unique = true, nullable = false)
    private String businessId;

    /**
     * Dirección de correo electrónico del cliente.
     * Se valida que el formato sea correcto.
     */
    @Email(message = "Email should be valid.")
    @NotNull(message = "Email is required.")
    private String email;

    /**
     * Número de teléfono del cliente.
     * Se valida que sea un número de 10 dígitos.
     */
    @NotNull(message = "Phone is required.")
    @Column(nullable = false, length = 10)
    private String phoneNumber;

    /**
     * Fecha en la que la información del cliente fue añadida.
     */
    private Date dataAdded;

    /**
     * Fecha de inicio de la relación con el cliente.
     * Esta fecha es obligatoria.
     */
    @NotNull(message = "Start Date is required.")
    private Date startDate;

    /**
     * Fecha de finalización de la relación con el cliente (si corresponde).
     * Este campo es opcional.
     */
    private Date endDate;
}