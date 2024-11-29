package com.alianza.client.adapter.out;

import com.alianza.client.domain.model.Client;

import com.alianza.client.port.out.ClientRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class ClientRepository implements ClientRepositoryPort {

    private JpaClientRepository jpaClientRepository;

    @Autowired
    public ClientRepository(JpaClientRepository jpaClientRepository) {
        this.jpaClientRepository = jpaClientRepository;
    }

    @Override
    public Client save(Client client) {
        try {
            return jpaClientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = "Business ID '" + client.getBusinessId() + "' already exists.";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, null);
        }
    }

    @Override
    public Page<Client> findAll(PageRequest pageRequest) {
        return jpaClientRepository.findAll(pageRequest);
    }

    @Override
    public Page<Client> findBySearchQuery(PageRequest pageRequest, String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return jpaClientRepository.findAll(pageRequest);
        }
        return jpaClientRepository.findBySearchQuery(searchQuery, pageRequest);
    }

}

