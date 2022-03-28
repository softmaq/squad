package com.squad.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.ff4j.FF4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.squad.api.domain.Squad;
import com.squad.api.dto.SquadDTO;
import com.squad.api.service.SquadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/squads")
public class SquadController {

	@Autowired(required=true)
	@Qualifier("getFF4j")
	public FF4j ff4j;
	
	@Autowired 
	private SquadService service;
	
	@PostMapping
	public ResponseEntity<Squad> save(@Valid @RequestBody SquadDTO squadDTO) {
		ModelMapper modelMapper = new ModelMapper();
		Squad squad = modelMapper.map(squadDTO, Squad.class);
		Squad squadSaved = service.save(squad);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(squadSaved.getSquadId()).toUri();
		log.info("entity={} method={} event={} message={}","Squad","save","saved",String.format("New Squad: %s",squad));
		return ResponseEntity.created(uri).body(squadSaved);
	}

	@PostMapping("new")
	public ResponseEntity<Squad> saveNew(@Valid @RequestBody SquadDTO squadDTO) {
		var squad = new Squad();
		BeanUtils.copyProperties(squadDTO, squad);
		Squad squadSaved = service.save(squad);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(squadSaved.getSquadId()).toUri();
		return ResponseEntity.created(uri).body(squadSaved);
	}

	@GetMapping("{id}")
	public ResponseEntity<SquadDTO> getById(@PathVariable("id") Integer id)    {
		var squadDTO = new SquadDTO();
		var squad = service.findById(id);
		BeanUtils.copyProperties(squad, squadDTO);
		return ResponseEntity.ok(squadDTO);
	}

	@GetMapping()
	public ResponseEntity<List<Squad>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	
}
