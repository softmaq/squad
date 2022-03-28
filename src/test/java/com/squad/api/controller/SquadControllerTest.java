package com.squad.api.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.ff4j.FF4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squad.api.domain.Squad;
import com.squad.api.service.SquadService;

@RunWith(SpringRunner.class)
@WebMvcTest(SquadController.class)
@AutoConfigureMockMvc()
public class SquadControllerTest  {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SquadService service;
	
	@MockBean(name="ff4j")
	private FF4j ff4j;
	
	private static final String url = "/squads";
	private static final String local = "http://localhost";	
	private static final String locationSave = local+url;
	private static final String locationSaveNew = local+url+"/new";
	
	
	@Test
	public void saveSquad_WhenSquadValid_ExpectedCreated() throws Exception {	
				
		Squad squad = Squad.builder().squadId(1).squadName("Squad Test").build();
		
		given(service.save(squad)).willReturn(squad);
		
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(Obj2Json(squad)))
		.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(header().string("location",containsString(locationSave)))
		.andReturn();
		
		verify(service,times(1)).save(squad);
	}

	@Test
	public void saveNewSquad_WhenSquadValid_ExpectedCreated() throws Exception {	
				
		Squad squad = Squad.builder().squadId(1).squadName("Squad Test").build();
		
		given(service.save(squad)).willReturn(squad);
		
		mockMvc.perform(post(url+"/new").contentType(MediaType.APPLICATION_JSON).content(Obj2Json(squad)))
		.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(header().string("location",containsString(locationSaveNew)))
		.andReturn();
		
		verify(service,times(1)).save(squad);
	}
	
	
	private String Obj2Json(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch ( Exception e) {
			throw new RuntimeException(e);
		}
	}
}
