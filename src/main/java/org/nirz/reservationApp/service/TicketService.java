package org.nirz.reservationApp.service;



import java.util.Optional;

import org.nirz.reservationApp.Dao.BusDao;
import org.nirz.reservationApp.Dao.TicketDao;
import org.nirz.reservationApp.Dao.UserDao;
import org.nirz.reservationApp.Dto.TicketResponse;
import org.nirz.reservationApp.Exception.BusNotFoundException;
import org.nirz.reservationApp.Exception.UserNotFoundException;
import org.nirz.reservationApp.model.Bus;
import org.nirz.reservationApp.model.Ticket;
import org.nirz.reservationApp.model.User;
import org.nirz.reservationApp.util.AccountStatus;
import org.nirz.reservationApp.util.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private BusDao busDao;
	@Autowired
	private UserDao userDao;

	public TicketResponse bookTicket(int userId, int busId, int numberOfSeats, String email) {
		Optional<Bus> recBus = busDao.findById(busId);
		Optional<User> recUser = userDao.findById(userId);
		if (recBus.isEmpty())
			throw new BusNotFoundException("Cannot Book Ticket as Bus Id is Invalid");
		if (recUser.isEmpty())
			throw new UserNotFoundException("Cannot Book Ticket as User Id is Invalid");
		User dbUser = recUser.get();
		if (dbUser.getStatus().equals(AccountStatus.IN_ACTIVE.toString()))
			throw new IllegalStateException("Please Activate Your Account, Then book tickets");
		Bus dbBus = recBus.get();
		if (dbBus.getAvailableSeats() < numberOfSeats)
			throw new IllegalArgumentException("Insufficient seats");
		Ticket ticket = new Ticket();
		ticket.setCost(numberOfSeats * dbBus.getCostPerSeat());
		ticket.setStatus(TicketStatus.BOOKED.toString());
		ticket.setBus(dbBus);// Assigning Bus to ticket
		ticket.setUser(dbUser);// Assigning User to ticket
		ticket.setNumberOfSeatsBooked(numberOfSeats);
		dbBus.getBookedTickets().add(ticket);// Adding ticket to bus
		dbUser.getTickets().add(ticket);// Adding Ticket to User
		dbBus.setAvailableSeats(dbBus.getAvailableSeats() - numberOfSeats);
		userDao.saveUser(dbUser);// Updating User
		busDao.saveBus(dbBus);// Updating Bus
		ticket = ticketDao.saveTicket(ticket);
		//ticket email sending
		return mapToTicketResponse(ticket, dbBus, dbUser);
	}

	public TicketResponse mapToTicketResponse(Ticket ticket, Bus bus, User user) {
		TicketResponse ticketResponse = new TicketResponse();
		ticketResponse.setAge(user.getAge());
		ticketResponse.setBusName(bus.getName());
		ticketResponse.setBusNumber(bus.getBusNumber());
		ticketResponse.setCost(ticket.getCost());
		ticketResponse.setDateOfBooking(ticket.getDateOfBooking());
		ticketResponse.setDateOfDeparture(bus.getDateOfDeparture());
		ticketResponse.setDestination(bus.getTo());
		ticketResponse.setGender(user.getGender());
		ticketResponse.setId(ticket.getId());
		ticketResponse.setNumberOfSeatsBooked(ticket.getNumberOfSeatsBooked());
		ticketResponse.setPhone(user.getPhone());
		ticketResponse.setSource(bus.getFrom());
		ticketResponse.setStatus(ticket.getStatus());
		ticketResponse.setUsername(user.getName());
		ticketResponse.setEmail(user.getEmail());
		return ticketResponse;

	}
}