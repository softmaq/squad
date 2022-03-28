package com.squad.api.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ff4j.FF4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.api.domain.Squad;
import com.squad.api.exception.SquadJaExisteException;
import com.squad.api.exception.SquadNaoEncontradaException;
import com.squad.api.repository.SquadRepository;


@RunWith(MockitoJUnitRunner.class)
public class SquadServiceTest {

	@InjectMocks
	SquadService service;
	
	@Mock 
	SquadRepository repository;
	
	@Mock
	FF4j ff4j;

	private Squad squad = null;
	private List<Squad> squadList = new ArrayList<Squad>();
	public String squadName = "Squad Teste";

	@Before
	public void setup() {
		squad = Squad.builder().squadId(1).squadName("Squad Teste").build();
		squadList.add(squad);
		
	}
	
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testFindById_WhenValidId() {
		when(repository.findById(squad.getSquadId())).thenReturn(Optional.of(squad));

		service.findById(1);
		
		verify(repository).findById(1);
	}
	
	@Test
	public void testFindById_WhenInvalidId() {
		thrown.expect(SquadNaoEncontradaException.class);
		service.findById(0);
	}
	
	
	@Test
	public void testFindAll_WhenListIsPresent() {
		when(repository.findAll()).thenReturn(squadList);

		service.findAll();
		
		verify(repository).findAll();
		assertTrue(squadList.size() > 0);
	}

	@Test
	public void testFindAll_WhenListIsEmpty() {
		squadList.clear();
		
		when(repository.findAll()).thenReturn(squadList);
		
		service.findAll();

		verify(repository).findAll();
		assertTrue(squadList.size() == 0);
	}
	
	
	@Test
	public void testSave_WhenValidSquad_WithFeatureToogleOn() {

		when(ff4j.exist(any())).thenReturn(false);
		when(ff4j.check(any())).thenReturn(true);
		when(repository.findBySquadName(any(String.class))).thenReturn(Optional.empty());
		when(repository.save(squad)).thenReturn(squad);
		
		service.save(squad);
		
		verify(repository).findBySquadName(any(String.class));
		verify(repository).save(squad);
	}

	@Test
	public void testSave_WhenSquadExist_WithFeatureToogleOn() {

		when(ff4j.exist(any())).thenReturn(true);
		when(ff4j.check(any())).thenReturn(true);
		when(repository.findBySquadName(any(String.class))).thenReturn(Optional.of(squad));

		thrown.expect(SquadJaExisteException.class);
		service.save(squad);
	}
	
	
	@Test
	public void testSave_WhenValidSquad_WithFetureToogleOff() {

		when(ff4j.exist(any())).thenReturn(true);
		when(ff4j.check(any(String.class))).thenReturn(false);
		when(repository.save(squad)).thenReturn(squad);
		
		service.save(squad);
		
		verify(repository,times(0)).findBySquadName(any(String.class));
		verify(repository,times(1)).save(squad);
	}
	
}

