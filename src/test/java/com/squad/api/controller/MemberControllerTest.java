package com.squad.api.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.ff4j.FF4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squad.api.domain.Member;
import com.squad.api.domain.Squad;
import com.squad.api.dto.MemberDTO;
import com.squad.api.dto.MemberSquadDTO;
import com.squad.api.service.MemberService;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc()
public class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MemberService service;
	
	@MockBean(name="ff4j")
	private FF4j ff4j;
	
	private static final String url = "/members";
	private static final String local = "http://localhost";	
	private static final String locationSave = local+url;	
	
	@Test
	public void saveMember_WhenMemberValid_ExpectedCreated() throws Exception {	

		Squad squad = Squad.builder().squadId(1).squadName("Squad Test").build();
		MemberDTO memberDTO = MemberDTO.builder()
				.memberId(1).memberName("Member Teste").squad(squad).jobTitle("DEV").build();
		Member member = Member.builder().build();
		BeanUtils.copyProperties(memberDTO, member);
		
		given(service.save(member)).willReturn(member);
		
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(Obj2Json(memberDTO)))
		.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(header().string("location",containsString(locationSave)))
		.andReturn();
		
		verify(service,times(1)).save(member);
	}	

	@Test
	public void getMemberById_WhenMemberIdValid_ExpectedOk() throws Exception {	

		Squad squad = Squad.builder().squadId(1).squadName("Squad Test").build();
		MemberDTO memberDTO = MemberDTO.builder()
				.memberId(1).memberName("Member Teste").squad(squad).jobTitle("DEV").build();
		Member member = Member.builder().build();
		BeanUtils.copyProperties(memberDTO, member);
		
		given(service.findById(1)).willReturn(member);
		
		mockMvc.perform(get(url+"/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
		verify(service,times(1)).findById(1);
	}	
	

	@Test
	public void getMembersBySquadId_WhenSquadIdValid_ExpectedOk() throws Exception {	

		MemberSquadDTO memberSquadDTO = MemberSquadDTO.builder()
				.memberId(1).memberName("Member Teste").jobTitle("DEV").build();
		List<MemberSquadDTO> members = new ArrayList<>();
		members.add(memberSquadDTO);
		
		given(service.findMembersBySquadId(1)).willReturn(members);
		
		mockMvc.perform(get(url+"/squads/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
		verify(service,times(1)).findMembersBySquadId(1);
	}	
	

	@Test
	public void getAllMembers_WhenMembersExist_ExpectedOk() throws Exception {	

		Squad squad = Squad.builder().squadId(1).squadName("Squad Test").build();
		MemberDTO memberDTO = MemberDTO.builder()
				.memberId(1).memberName("Member Teste").squad(squad).jobTitle("DEV").build();
		Member member = Member.builder().build();
		BeanUtils.copyProperties(memberDTO, member);
		List<Member> members = new ArrayList<>();
		members.add(member);
		
		given(service.findAll()).willReturn(members);
		
		mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
		
		verify(service,times(1)).findAll();
	}	
	
	
	private String Obj2Json(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch ( Exception e) {
			throw new RuntimeException(e);
		}
	}	
}
