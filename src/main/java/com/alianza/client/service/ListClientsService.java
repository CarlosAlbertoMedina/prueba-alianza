package com.alianza.client.service;

import com.alianza.client.adapter.out.ClientRepository;
import com.alianza.client.domain.model.Client;
import com.alianza.client.port.in.ListClientsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ListClientsService implements ListClientsUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ListClientsService.class);

    private final ClientRepository clientRepository;

    @Autowired
    public ListClientsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Page<Client> listClients(PageRequest pageRequest, String searchQuery) {
        logger.info("Listing clients with page request: {} and search query: {}", pageRequest, searchQuery);

        try {
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                logger.info("No search query provided. Fetching all clients.");
                return clientRepository.findAll(pageRequest);
            } else {
                logger.info("Searching clients with query: {}", searchQuery);
                return clientRepository.findBySearchQuery(pageRequest, searchQuery);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching clients", e);
            throw new RuntimeException("Failed to fetch clients", e);
        }
    }
}

