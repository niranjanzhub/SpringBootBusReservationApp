package org.nirz.reservationApp.respository;



import java.time.LocalDate;
import java.util.List;

import org.nirz.reservationApp.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusRepository extends JpaRepository<Bus, Integer>{
	
//	List<Bus> findByFromAndToAndDateOfDeparture(String from, String to, LocalDate date);
//	List<Bus> findByFromAndTo(String from, String to);
//	
//	List<Bus> findByFrom(String from);
//	List<Bus> findByTo(String to);
//	
	
	@Query("select b from Bus b where b.admin.id=?1")
	List<Bus> findByAdminId(int id);

	@Query("select b from Bus b where b.from=?1 and b.to=?2 and b.dateOfDeparture=?3")
	List<Bus> findBuses(String from, String to, LocalDate dateOfDeparture);
}


