package com.restaurant.Restaurant.service.clients;

import com.restaurant.Restaurant.models.dto.ClientDTO;

import java.util.List;

public interface IClientService {
    List<ClientDTO> getClients();

    ClientDTO getClient(String document);
    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO updateClient(String document, ClientDTO clientDTO);
    void deleteClient(String  document);
}

