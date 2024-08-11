package org.nirz.reservationApp.respository;


import org.nirz.reservationApp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}