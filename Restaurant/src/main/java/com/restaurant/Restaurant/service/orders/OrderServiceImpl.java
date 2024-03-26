package com.restaurant.Restaurant.service.orders;

import com.restaurant.Restaurant.entity.ClientEntity;
import com.restaurant.Restaurant.entity.OrderEntity;
import com.restaurant.Restaurant.entity.ProductEntity;
import com.restaurant.Restaurant.mapper.OrderEntityToDtoMapper;
import com.restaurant.Restaurant.models.dto.OrderDTO;
import com.restaurant.Restaurant.repository.ClientRepository;
import com.restaurant.Restaurant.repository.IOrderRepository;
import com.restaurant.Restaurant.repository.IProductRepositoryJPA;
import com.restaurant.Restaurant.validator.OrderValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class OrderServiceImpl implements IOrderService{

    private final IOrderRepository orderRepository;

    private final OrderEntityToDtoMapper mapper;

    IProductRepositoryJPA productRepository;

    ClientRepository clientRepository;

    private final OrderValidator validator;

    public OrderServiceImpl (IOrderRepository orderRepository, OrderEntityToDtoMapper mapper,
                             IProductRepositoryJPA productRepository, ClientRepository clientRepository, OrderValidator validator)
    {
        this.orderRepository = orderRepository;
        this.mapper= mapper;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.validator = validator;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        validator.verifyFields(orderDTO);

        ProductEntity product = productRepository.findByUuid(String.valueOf(orderDTO.getProductUuid()));
        ClientEntity client = clientRepository.findByDocument(String.valueOf(orderDTO.getClientDocument()));

        validator.verifyProductUuidExists(product);
        validator.verifyClientExists(client);

        double subTotal =  product.getPrice() * orderDTO.getQuantity();
        double tax = subTotal * 0.16;
        double grandTotal = subTotal + tax;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUuid(UUID.randomUUID().toString());
        orderEntity.setCreationDateTime(LocalDateTime.now());
        orderEntity.setClientDocument(product.getUuid());
        orderEntity.setProductUuid(client.getDocument());
        orderEntity.setQuantity(orderDTO.getQuantity());
        orderEntity.setExtraInformation(orderDTO.getExtraInformation());
        orderEntity.setSubTotal(subTotal);
        orderEntity.setTax(tax);
        orderEntity.setGrandTotal(grandTotal);

        return mapper.convert(orderRepository.save(orderEntity));
    }

    @Override
    public OrderDTO updateOrderDelivered(String uuid, LocalDateTime timeStamp) {

        OrderEntity orderEntity = orderRepository.findByUuid(uuid);

        validator.uuidValidFormat(uuid);
        validator.verifyOrderExists(orderEntity);

        orderEntity.setDelivered(true);
        orderEntity.setDeliveredDate(timeStamp);
        orderRepository.save(orderEntity);
        return mapper.convert(orderEntity);
    }

}
