package com.raydana.notes.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raydana.notes.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:username)")
	public User loadUserByUsername(@Param("username") String username);
}
