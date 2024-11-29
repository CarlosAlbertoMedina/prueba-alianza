package com.alianza.client.adapter.in.mapper;

import com.alianza.client.adapter.in.dto.ClientDTO;
import com.alianza.client.domain.model.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
public class ClientMapper {


    public ClientDTO clientToClientDTO(Client client) {
        if (client == null) {
            return null;
        }
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setSharedKey(client.getSharedKey());
        clientDTO.setBusinessId(client.getBusinessId());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPhoneNumber(client.getPhoneNumber());
        clientDTO.setDataAdded(this.convertDateToString(client.getDataAdded()));
        clientDTO.setStartDate(this.convertDateToString(client.getStartDate()));
        clientDTO.setEndDate(this.convertDateToString(client.getEndDate()));
        return clientDTO;
    }

    public Client clientDTOToClient(ClientDTO clientDTO) {
        if (clientDTO == null) {
            return null;
        }
        Client client = new Client();
        client.setSharedKey(generateSharedKey(clientDTO.getBusinessId()));
        client.setBusinessId(clientDTO.getBusinessId());
        client.setEmail(clientDTO.getEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setDataAdded(this.convertStringToDate(clientDTO.getDataAdded()));
        client.setStartDate(this.convertStringToDate(clientDTO.getStartDate()));
        client.setEndDate(this.convertStringToDate(clientDTO.getEndDate()));
        return client;
    }

    /**
     * Convierte un objeto  String  a Date en formato "yyyy-MM-dd".
     *
     * @param dateString El objeto String a convertir.
     * @return La fecha en formato Date.
     */
    public Date convertStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Convierte un objeto Date a String en formato "yyyy-MM-dd".
     *
     * @param date El objeto Date a convertir.
     * @return La fecha en formato String.
     */
    public String convertDateToString(Date date) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    private String generateSharedKey(String businessId) {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        return businessId.toLowerCase().replace(" ", "") + uuid;
    }

}
