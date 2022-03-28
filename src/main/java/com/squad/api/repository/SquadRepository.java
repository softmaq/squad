package com.squad.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.squad.api.domain.Squad;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Integer>{

	@Query(value= "select distinct s.squadName from Squad s where s.squadName = ?1 ")
	Optional<Squad> findBySquadName(String squadName);

}
