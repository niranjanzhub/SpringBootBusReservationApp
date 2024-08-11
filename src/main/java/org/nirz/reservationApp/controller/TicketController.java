package org.nirz.reservationApp.controller;




import org.nirz.reservationApp.Dto.TicketResponse;
import org.nirz.reservationApp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketController {
	@Autowired
	private TicketService ticketService;

	@PostMapping("/{userId}/{busId}/{numberOfSeats}/{email}")
	public TicketResponse bookTicket( @PathVariable int userId,@PathVariable int busId,
			@PathVariable int numberOfSeats,@PathVariable String email) {
		return ticketService.bookTicket(userId, busId, numberOfSeats,email);
	}
	
	
//	@PostMapping("/send-ticket-email")
//    public ResponseEntity<String> sendTicketEmail(@RequestBody TicketResponse ticketResponse) {
//        try {
//            emailService.sendTicketEmail(ticketResponse);
//            return ResponseEntity.ok("Ticket email sent successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send ticket email.");
//        }
//    }
	
}