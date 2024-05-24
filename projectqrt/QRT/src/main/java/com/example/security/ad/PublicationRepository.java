package com.example.security.ad;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findByPriceLessThan(double price);
    List<Publication> findByPriceGreaterThan(double price);




}

