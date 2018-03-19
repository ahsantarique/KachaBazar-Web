
package com.kachabazar.shop;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, String> {


    Shop findByUserId(String userId);

}