package com.squad.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.api.domain.Member;
import com.squad.api.dto.MemberSquadDTO;
import com.squad.api.exception.MemberNaoEncontradoException;
import com.squad.api.exception.SquadNaoEncontradaException;
import com.squad.api.repository.MemberRepository;
import com.squad.api.repository.SquadRepository;

@Service
public class MemberService {
	
	@Autowired
	private SquadRepository squadRepository;
	
	@Autowired
	private MemberRepository repository;
	
	@Transactional
	public Member save(Member member) {
		var squad = squadRepository.findById(member.getSquad().getSquadId()).orElseThrow(() -> new SquadNaoEncontradaException(Long.valueOf(member.getSquad().getSquadId())));
		member.setSquad(squad);
		return repository.save(member);
	}
	
	@Transactional
	public Member findById(Integer memberId) {
		return repository.findById(memberId).orElseThrow(() -> new MemberNaoEncontradoException(Long.valueOf(memberId)));
	}
	
	@Transactional
	public List<MemberSquadDTO> findMembersBySquadId(Integer squadId) {
		List<Member> members = repository.findAllMemberBySquadId(squadId);
		List<MemberSquadDTO> membersSquad = new ArrayList<>();
		
		for ( Member member : members ) {
			MemberSquadDTO memberSquadDTO = new MemberSquadDTO();
			BeanUtils.copyProperties(member, memberSquadDTO);
			membersSquad.add(memberSquadDTO);
		}
		return membersSquad;
	}
	
	@Transactional
	public List<Member> findAll() {
		return repository.findAll();
	}
	
}
