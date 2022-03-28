package com.squad.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.squad.api.domain.Member;
import com.squad.api.dto.MemberDTO;
import com.squad.api.dto.MemberSquadDTO;
import com.squad.api.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@PostMapping
	public ResponseEntity<Member> save(@Valid @RequestBody MemberDTO memberDTO) {
		
		var member = new Member();
		BeanUtils.copyProperties(memberDTO, member);
		var memberSaved = service.save(member);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(memberSaved.getMemberId()).toUri();
		return ResponseEntity.created(uri).body(memberSaved);		
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<MemberDTO> getById(@PathVariable("id") Integer id)    {
		var memberDTO = new MemberDTO();
		var member = service.findById(id);
		BeanUtils.copyProperties(member, memberDTO);
		return ResponseEntity.ok(memberDTO);
	}
	
	
	@GetMapping("/squads/{id}")
	public ResponseEntity<List<MemberSquadDTO>> findMembersBySquadId(@PathVariable Integer id) {
		
		return ResponseEntity.ok().body(service.findMembersBySquadId(id));
		
	}
	
	@GetMapping()
	public List<Member> findAll() {
		return service.findAll();
	}

}
