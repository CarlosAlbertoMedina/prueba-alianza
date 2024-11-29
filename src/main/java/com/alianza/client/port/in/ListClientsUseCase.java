package com.alianza.client.port.in;

import com.alianza.client.domain.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ListClientsUseCase {
    Page<Client> listClients(PageRequest pageRequest, String searchQuery);
}
