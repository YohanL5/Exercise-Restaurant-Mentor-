package com.restaurant.Restaurant.controller;
import com.restaurant.Restaurant.models.dto.ClientDTO;
import com.restaurant.Restaurant.models.dto.errorDto.ClientErrorDTO;
import com.restaurant.Restaurant.service.clients.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/clients")
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(this.clientService.createClient(clientDTO));

        } catch (Exception e) {
            ClientErrorDTO clientErrorDTO = new ClientErrorDTO();
            clientErrorDTO.setMessage(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(clientErrorDTO.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getClients() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.clientService.getClients());
        } catch (Exception e) {
            ClientErrorDTO clientErrorDTO = new ClientErrorDTO();
            clientErrorDTO.setMessage(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(clientErrorDTO.getMessage());
        }
    }

    @GetMapping("/{document}")
    public ResponseEntity<?>getClient( @PathVariable String document){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.clientService.getClient(document));
        } catch (Exception e) {
            ClientErrorDTO clientErrorDTO = new ClientErrorDTO();
            clientErrorDTO.setMessage(e.getMessage());
            return  ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(clientErrorDTO.getMessage());
        }
    }

    @PutMapping("/{document}")
    public ResponseEntity<?>UpdateClient(@PathVariable String document, @RequestBody ClientDTO clientDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(this.clientService.updateClient(document, clientDTO));
        } catch (Exception e) {
            ClientErrorDTO clientErrorDTO = new ClientErrorDTO();
            clientErrorDTO.setMessage(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(clientErrorDTO.getMessage());
        }
    }
    @DeleteMapping("/{document}")
    public void deleteClient(@PathVariable String document){
        clientService.deleteClient(document);
    }
}