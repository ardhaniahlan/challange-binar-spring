package com.challangebinarspring.binarfud.repository.oauth;


import com.challangebinarspring.binarfud.entity.oauth.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findOneByClientId(String clientId);

}

