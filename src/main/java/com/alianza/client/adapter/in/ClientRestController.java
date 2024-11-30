package com.alianza.client.adapter.in;

import com.alianza.client.adapter.in.dto.ClientDTO;
import com.alianza.client.adapter.in.mapper.ClientMapper;
import com.alianza.client.domain.model.Client;
import com.alianza.client.port.in.CreateClientUseCase;
import com.alianza.client.port.in.ListClientsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    private CreateClientUseCase createClientUseCase;
    private ListClientsUseCase listClientsUseCase;
    private ClientMapper clientMapper;

    @Autowired
    public ClientRestController(CreateClientUseCase createClientUseCase, ListClientsUseCase listClientsUseCase, ClientMapper clientMapper) {
        this.createClientUseCase = createClientUseCase;
        this.listClientsUseCase = listClientsUseCase;
        this.clientMapper = clientMapper;
    }

    @Operation(
            summary = "Create a new client",
            description = "This API allows you to create a new client.",
            tags = {"Client Operations"}, // Aquí se agrupan bajo un mismo tag
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid client data")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.clientDTOToClient(clientDTO);
        Client createdClient = createClientUseCase.createClient(client);
        return clientMapper.clientToClientDTO(createdClient);
    }

    @Operation(
            summary = "List clients with pagination and optional search",
            description = "This API returns a paginated list of clients, with an optional search query.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of clients successfully retrieved"),
                    @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
            },
            tags = {"Client Operations"}  // Grouping it under the same tag
    )
    @GetMapping
    public Page<Client> listClients(
            @Parameter(description = "Page number", required = true) @RequestParam int page,
            @Parameter(description = "Page size", required = true) @RequestParam int size,
            @Parameter(description = "Optional search query") @RequestParam(required = false) String searchQuery,
            @Parameter(description = "Optional sharedKey filter") @RequestParam(required = false) String sharedKey,
            @Parameter(description = "Optional businessId filter") @RequestParam(required = false) String businessId,
            @Parameter(description = "Optional email filter") @RequestParam(required = false) String email,
            @Parameter(description = "Optional phone filter") @RequestParam(required = false) String phone) {

        // Llamar al caso de uso para obtener la lista de clientes
        Page<Client> clientsPage = listClientsUseCase.listClients(PageRequest.of(page, size), searchQuery);

        // Filtrar los resultados en memoria con lambdas si los filtros son proporcionados
        List<Client> filteredClients = clientsPage.getContent().stream()
                .filter(client -> (sharedKey == null || client.getSharedKey().contains(sharedKey)))
                .filter(client -> (businessId == null || client.getBusinessId().contains(businessId)))
                .filter(client -> (email == null || client.getEmail().contains(email)))
                .filter(client -> (phone == null || client.getPhoneNumber().contains(phone)))
                .collect(Collectors.toList());

        // Crear un nuevo Page con los clientes filtrados
        return new PageImpl<>(filteredClients, PageRequest.of(page, size), filteredClients.size());
    }

    @Operation(
            summary = "Generate CSV report of clients",
            description = "This API generates a CSV report containing client data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "CSV report generated"),
                    @ApiResponse(responseCode = "500", description = "Error generating CSV report")
            },
            tags = {"Client Operations"}  // Same tag as the previous one
    )
    @GetMapping(value = "/report.csv")
    public void generateCsvReport(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv");
        List<ClientDTO> clients = listClientsUseCase.listClients(PageRequest.of(0, 100), null)
                .map(client -> clientMapper.clientToClientDTO(client))
                .getContent();
        PrintWriter writer = response.getWriter();
        writer.append("sharedKey,businessId,email,phone,dataAdded,startDate,endDate\n");

        for (ClientDTO client : clients) {
            writer.append(String.join(",",
                    client.getSharedKey(),
                    client.getBusinessId(),
                    client.getEmail(),
                    client.getPhoneNumber(),
                    client.getDataAdded().toString(),
                    client.getStartDate().toString(),
                    client.getEndDate().toString()
            ));
            writer.append("\n");
        }
        writer.flush();
    }
}
