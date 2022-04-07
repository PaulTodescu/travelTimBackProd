package com.travelTim.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketDAO extends JpaRepository<TicketEntity, Long> {

    Optional<TicketEntity> findTicketEntityById(Long id);

    Optional<TicketEntity> findTicketEntityByName(String name);

    Optional<TicketEntity> findTicketEntityByNameAndPrice(String name, Double price);

}
