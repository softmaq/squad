package com.squad.api.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.api.domain.Member;
import com.squad.api.domain.Squad;
import com.squad.api.exception.MemberNaoEncontradoException;
import com.squad.api.exception.SquadNaoEncontradaException;
import com.squad.api.repository.MemberRepository;
import com.squad.api.repository.SquadRepository;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {

	@Mock
	private MemberRepository repository;
	
	@Mock
	private SquadRepository squadRepository;
	
	@InjectMocks
	private MemberService service;
	
	private Member member = null;
	private Squad squad = null;
	private List<Member> members = new ArrayList<>();

	@Before
	public void setup() {
		squad = Squad.builder().squadId(1).squadName("Squad Teste").build();
		member = Member.builder().memberId(1).memberName("Membro Teste").jobTitle("TL").squad(squad).build();
		members.add(member);
	}
	
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException thrown = ExpectedException.none();	
	
	@Test
	public void testSave_WhenMemberIsValid() {

		when(squadRepository.findById(squad.getSquadId())).thenReturn(Optional.of(squad));
		when(repository.save(member)).thenReturn(member);
		
		service.save(member);
		
		verify(squadRepository,times(1)).findById(squad.getSquadId());
		verify(repository,times(1)).save(member);
		
	}
	
	@Test
	public void testSave_WhenMemberSquadIdInvalid() {
		thrown.expect(SquadNaoEncontradaException.class);
		service.save(member);
	}

	@Test
	public void testFindById_WhenMemberIdValid() {
		
		var memberId = member.getMemberId();
		
		when(repository.findById(memberId)).thenReturn(Optional.of(member));
		
		service.findById(memberId);
		
		verify(repository,times(1)).findById(memberId);
		
	}

	@Test
	public void testFindById_WhenMemberIdInValid() {
		var memberId = member.getMemberId();
		thrown.expect(MemberNaoEncontradoException.class);
		service.findById(memberId);
	}
	
	@Test
	public void testFindAllMemberBySquadId_WhenSquadIdExist() {
		
		var squadId = squad.getSquadId();
		
		when(repository.findAllMemberBySquadId(squadId)).thenReturn(members);
		
		service.findMembersBySquadId(squadId);
		
		verify(repository,times(1)).findAllMemberBySquadId(squadId);
	}

	@Test
	public void testFindAllMember_WhenMembersExist() {
		
		when(repository.findAll()).thenReturn(members);
		
		service.findAll();
		
		verify(repository,times(1)).findAll();
	}
	
	
}
