package com.restaurant.Restaurant.service.orders;

import com.restaurant.Restaurant.entity.ClientEntity;
import com.restaurant.Restaurant.entity.OrderEntity;
import com.restaurant.Restaurant.entity.ProductEntity;
import com.restaurant.Restaurant.exception.impl.InvalidOrIncompleteDataException;
import com.restaurant.Restaurant.mapper.OrderEntityToDtoMapper;
import com.restaurant.Restaurant.models.dto.OrderDTO;
import com.restaurant.Restaurant.repository.ClientRepository;
import com.restaurant.Restaurant.repository.IOrderRepository;
import com.restaurant.Restaurant.repository.IProductRepositoryJPA;
import com.restaurant.Restaurant.validator.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    OrderDTO orderDTO;
    OrderDTO expectedDtoResult;
    OrderEntity orderEntity;
    ProductEntity product;
    ClientEntity client;

    private OrderServiceImpl orderService;

    @Mock
    private IOrderRepository orderRepository;
    @Mock
    private OrderEntityToDtoMapper mapper;
    @Mock
    IProductRepositoryJPA productRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    private OrderValidator validator;


    @BeforeEach
    public void setUp(){

         orderDTO = OrderDTO.builder()
                .clientDocument("CC-12345")
                .productUuid("256309c6-5b24-499e-9ccb-6e69b781690a")
                .quantity(2)
                .extraInformation("Hamburguer with french fries")
                .build();


        orderEntity = OrderEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .creationDateTime(LocalDateTime.parse("2024-03-11T20:03:31.5885433"))
                .clientDocument("CC-12345")
                .productUuid("256309c6-5b24-499e-9ccb-6e69b781690a")
                .quantity(2)
                .extraInformation("Hamburguer with french fries")
                .subTotal( 42016.82)
                .tax(6722.6912)
                .grandTotal(48739.5112)
                .delivered(false)
                .deliveredDate(null)
                .build();

         product = ProductEntity.builder()
                 .uuid("256309c6-5b24-499e-9ccb-6e69b781690a")
                 .fantasyName("BURGER DOUBLE")
                 .category(ProductEntity.Category.HAMBURGERS_AND_HOTDOGS)
                 .description("Hamburger double with french fries")
                 .price(20000.00)
                 .available(true)
                 .build();

         client = new ClientEntity();
                client.setDocument("CC-12345");
                client.setName("Andres Legro");
                client.setEmail("andres@gmail.com");
                client.setPhone("3101234567");
                client.setDeliveryAddress("Calle 15 # 45-78");

             expectedDtoResult = OrderDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .creationDateTime(orderEntity.getCreationDateTime())
                .clientDocument(orderDTO.getClientDocument())
                .productUuid(orderDTO.getProductUuid())
                .quantity(2)
                .extraInformation(orderDTO.getExtraInformation())
                .subTotal(42016.82)
                .tax(6722.6912)
                .grandTotal(48739.5112)
                .delivered(false)
                .deliveredDate(null)
                .build();


        orderService =  new OrderServiceImpl(orderRepository, mapper, productRepository, clientRepository, validator);

    }

    @Test
    void shouldCreateANewOrderSuccessfully(){

        Mockito.when(productRepository.findByUuid(Mockito.anyString())).thenReturn(product);
        Mockito.when(clientRepository.findByDocument(Mockito.anyString())).thenReturn(client);

        Mockito.doNothing().when(validator).verifyFields(any());
        Mockito.doNothing().when(validator).verifyProductUuidExists(any());
        Mockito.doNothing().when(validator).verifyClientExists(any());

        Mockito.doReturn(expectedDtoResult).when(mapper).convert(any(OrderEntity.class));

        Mockito.when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        var result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(expectedDtoResult,result);
    }


    @Test
    void shouldUpdateAOrderSuccessfully(){

        expectedDtoResult = OrderDTO.builder()
                .uuid(UUID.randomUUID().toString())
                .creationDateTime(orderEntity.getCreationDateTime())
                .clientDocument(orderDTO.getClientDocument())
                .productUuid(orderDTO.getProductUuid())
                .quantity(2)
                .extraInformation(orderDTO.getExtraInformation())
                .subTotal(42016.82)
                .tax(6722.6912)
                .grandTotal(48739.5112)
                .delivered(false)
                .deliveredDate( LocalDateTime.parse("2024-03-11T15:05:00"))
                .build();

        Mockito.when(orderRepository.findByUuid(Mockito.anyString())).thenReturn(orderEntity);

        Mockito.doNothing().when(validator).uuidValidFormat(any());
        Mockito.doNothing().when(validator).verifyOrderExists(any());

        Mockito.doReturn(expectedDtoResult).when(mapper).convert(any(OrderEntity.class));
        Mockito.when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        var result = orderService.updateOrderDelivered
                (expectedDtoResult.getUuid(), LocalDateTime.parse("2024-03-11T15:05:00"));

        assertNotNull(result);
        assertEquals(expectedDtoResult, result);

    }

    @Test
    void shouldThrowExceptionWhenHasNoCompleteFieldsOnRequestBody() {
        orderDTO = OrderDTO.builder()
                .clientDocument(null)
                .productUuid("256309c6-5b24-499e-9ccb-6e69b781690a")
                .quantity(2)
                .extraInformation("Hamburguer with french fries")
                .build();

        Mockito.doThrow(InvalidOrIncompleteDataException.class).when(validator).verifyFields(orderDTO);

        assertThrows(InvalidOrIncompleteDataException.class, () -> {
            orderService.createOrder(orderDTO);
        });

    }


}


