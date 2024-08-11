package org.nirz.reservationApp.respository;



import java.util.Optional;

import org.nirz.reservationApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByPhoneAndPassword(long phone, String password);

	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findByToken(String token);
}