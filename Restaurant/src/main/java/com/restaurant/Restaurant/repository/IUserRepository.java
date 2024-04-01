package com.restaurant.Restaurant.repository;

import com.restaurant.Restaurant.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<ClientEntity,Long> {
    ClientEntity findByName(String name);
}
