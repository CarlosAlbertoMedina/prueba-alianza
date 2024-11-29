package com.alianza.tests;

import com.alianza.client.adapter.in.ClientRestController;
import com.alianza.client.adapter.in.dto.ClientDTO;
import com.alianza.client.adapter.in.mapper.ClientMapper;
import com.alianza.client.domain.model.Client;
import com.alianza.client.port.in.CreateClientUseCase;
import com.alianza.client.port.in.ListClientsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClientRestControllerTest {

    @Mock
    private CreateClientUseCase createClientUseCase;

    @Mock
    private ListClientsUseCase listClientsUseCase;

    @InjectMocks
    private ClientRestController clientRestController;

    private ClientDTO clientDto;

    @Mock
    private ClientMapper clientMapper;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @BeforeEach
    void setUp() {
        clientDto = new ClientDTO();
        clientDto.setSharedKey("SK12345");
        clientDto.setBusinessId("Juliana Gutierrez");
        clientDto.setEmail("jgutierres@gmail.com");
        clientDto.setPhoneNumber("3219876543");
        clientDto.setDataAdded("20/05/2019");
        clientDto.setStartDate("20/05/2019");
        clientDto.setEndDate("20/05/2019");
    }

    @Test
    void testCreateClient() {
        when(createClientUseCase.createClient(clientMapper.clientDTOToClient(clientDto))).thenReturn(clientMapper.clientDTOToClient(clientDto));
        ClientDTO createdClient = clientRestController.createClient(clientDto);
        assertNotNull(createdClient);
        assertEquals(clientDto.getBusinessId(), createdClient.getBusinessId());
        verify(createClientUseCase, times(1)).createClient(clientMapper.clientDTOToClient(clientDto));
    }

    @Test
    void testListClients() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Client> clientPage = mock(Page.class);
        when(listClientsUseCase.listClients(pageRequest, null)).thenReturn(clientPage);
        Page<Client> result = clientRestController.listClients(0, 10, null);
        assertNotNull(result);
        verify(listClientsUseCase, times(1)).listClients(pageRequest, null);
    }

}
