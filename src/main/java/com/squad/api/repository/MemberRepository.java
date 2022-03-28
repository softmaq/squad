package com.squad.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.squad.api.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	 
	@Query(value="SELECT m FROM Member m JOIN FETCH m.squad s WHERE s.squadId = :squadId")
	List<Member> findAllMemberBySquadId(@Param("squadId") Integer squadId);

}
