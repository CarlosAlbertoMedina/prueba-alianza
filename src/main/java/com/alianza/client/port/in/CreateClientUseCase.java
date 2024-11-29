package com.alianza.client.port.in;

import com.alianza.client.domain.model.Client;

public interface CreateClientUseCase {
    Client createClient(Client client);
}
