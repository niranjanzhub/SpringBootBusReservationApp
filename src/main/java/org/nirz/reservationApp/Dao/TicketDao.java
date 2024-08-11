package org.nirz.reservationApp.Dao;




import org.nirz.reservationApp.model.Ticket;
import org.nirz.reservationApp.respository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDao {
	@Autowired
	private TicketRepository ticketRepository;

	public Ticket saveTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
}