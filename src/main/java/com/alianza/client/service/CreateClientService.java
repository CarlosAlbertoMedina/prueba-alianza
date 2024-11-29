package com.alianza.client.service;

import com.alianza.client.adapter.out.ClientRepository;
import com.alianza.client.domain.model.Client;
import com.alianza.client.port.in.CreateClientUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CreateClientService implements CreateClientUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateClientService.class);

    private ClientRepository clientRepository;

    @Autowired
    public CreateClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        logger.info("Starting the creation process for client: {}", client.getBusinessId());

        StringBuilder validationErrors = new StringBuilder();
        if (client.getSharedKey() == null || client.getSharedKey().trim().isEmpty()) {
            validationErrors.append("Shared Key is required. ");
        }

        if (client.getBusinessId() == null || client.getBusinessId().trim().isEmpty()) {
            validationErrors.append("Business ID is required. ");
        }
        if (client.getEmail() == null || client.getEmail().trim().isEmpty()) {
            validationErrors.append("Email is required. ");
        } else if (!client.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            validationErrors.append("Email should be valid. ");
        }

        if (client.getPhoneNumber() == null || client.getPhoneNumber().trim().isEmpty()) {
            validationErrors.append("Phone number is required. ");
        } else if (client.getPhoneNumber().length() != 10) {
            validationErrors.append("Phone number must be 10 digits long. ");
        }

        if (client.getStartDate() == null) {
            validationErrors.append("Start date is required. ");
        }

        if (!validationErrors.isEmpty()) {
            logger.error("Validation failed for client: {}", validationErrors.toString());
            throw new IllegalArgumentException("Client validation failed: " + validationErrors.toString());
        }

            Client savedClient = clientRepository.save(client);
            return savedClient;
    }
}
