package com.alianza.client.port.out;

import com.alianza.client.domain.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ClientRepositoryPort {

    Client save(Client client);

    Page<Client> findAll(PageRequest pageRequest);

    Page<Client> findBySearchQuery(PageRequest pageRequest, String searchQuery);
}
