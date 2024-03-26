package com.restaurant.Restaurant.service.clients;

import com.restaurant.Restaurant.entity.ClientEntity;
import com.restaurant.Restaurant.exception.impl.ClientNotFoundException;
import com.restaurant.Restaurant.mapper.ClientEntityToDtoMapper;
import com.restaurant.Restaurant.models.dto.ClientDTO;
import com.restaurant.Restaurant.repository.ClientRepository;
import com.restaurant.Restaurant.service.clients.ClientService;
import com.restaurant.Restaurant.validator.ClientValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    private ClientDTO clientDTO;
    private ClientEntity clientEntity;

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientEntityToDtoMapper clientEntityToDtoMapper;

    @Mock
    private ClientValidator clientValidator;

    @BeforeEach
    public void setUp() {
        clientEntity = ClientEntity.builder()
                .name("Santiago Mosquera")
                .document("12345678")
                .email("correo@correo.com")
                .phone("3116321000")
                .deliveryAddress("Calle 96B")
                .build();

        clientDTO = ClientDTO.builder()
                .name("Santiago Mosquera")
                .document("12345678")
                .email("correo@correo.com")
                .phone("3116321000")
                .deliveryAddress("Calle 96B")
                .build();
    }

    @Test
    void shouldGetClients() {
        List<ClientEntity> clients = new ArrayList<>();
        clients.add(clientEntity);

        when(clientRepository.findAll()).thenReturn(clients);
        when(clientEntityToDtoMapper.convert(clientEntity)).thenReturn(clientDTO);

        List<ClientDTO> response = clientService.getClients();
        assertEquals(1, response.size());
        assertEquals(clientDTO, response.getFirst());

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void shouldGetClientsByDocument() {
        clientEntity.setDocument("12345678");
        Mockito.lenient().doNothing().when(clientValidator).validateDocumentFormat(clientEntity.getDocument());
        Mockito.when(clientRepository.findByDocument(clientEntity.getDocument())).thenReturn(clientEntity);
        Mockito.when(clientEntityToDtoMapper.convert(clientEntity)).thenReturn(clientDTO);
        var response = clientService.getClient(clientEntity.getDocument());

        verify(clientValidator, times(1)).validateDocumentFormat(clientEntity.getDocument());
        verify(clientRepository, times(1)).findByDocument(clientEntity.getDocument());
        assertEquals(clientDTO, response);
    }

    @Test
    void shouldDeleteClient() {
        Mockito.doNothing().when(clientValidator).validateDocumentFormat(clientDTO.getDocument());
        Mockito.when(clientRepository.findByDocument(clientDTO.getDocument())).thenReturn(clientEntity);
        clientService.deleteClient(clientDTO.getDocument());
    }

    // Test Exception.
    @Test
    void shouldClientNotFoundException() {
        Mockito.when(clientRepository.findByDocument(clientEntity.getDocument())).thenReturn(null);
        assertThrows(ClientNotFoundException.class, () -> clientService.getClient(clientEntity.getDocument()));
        assertThrows(ClientNotFoundException.class, () -> clientService.deleteClient(clientEntity.getDocument()));
        assertThrows(ClientNotFoundException.class, () -> clientService.updateClient(clientDTO.getDocument(), clientDTO));
    }
}