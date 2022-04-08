package com.squad.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.api.domain.Squad;
import com.squad.api.exception.SquadJaExisteException;
import com.squad.api.exception.SquadNaoEncontradaException;
import com.squad.api.repository.SquadRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SquadService {
	
	private static final String FEATURE_VALID_SQUAD_NAME_EXIST = "validSquadName";
	private static final String MSG_LOG = "entity={} method={} mesage={}";
	
	@Autowired(required = true)
	FF4j ff4j;

	@Autowired
	SquadRepository repository;

	public Squad findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new SquadNaoEncontradaException(Long.valueOf(id)));
	}

	public List<Squad> findAll() {
		return repository.findAll();
	}

	@Transactional
	public Squad save(Squad squad) {

		log.info("entity={} method={} event={} message={}", this.getClass().getName(), "save", "saving","salvando squad '" + squad.getSquadName() + "'");
        if (!ff4j.exist(FEATURE_VALID_SQUAD_NAME_EXIST)) {
        	log.info(MSG_LOG, String.format("Criando feature %s = false",FEATURE_VALID_SQUAD_NAME_EXIST));
            ff4j.createFeature(new Feature(FEATURE_VALID_SQUAD_NAME_EXIST, false));
        }
		
		
		if (ff4j.check(FEATURE_VALID_SQUAD_NAME_EXIST)) {
			var squadName = squad.getSquadName();

			if (!repository.findBySquadName(squadName).isEmpty()) {
				throw new SquadJaExisteException(squadName);
			}
		}

		Squad squadSaved = repository.save(squad);
		log.info("entity={} method={} event={} message={}", this.getClass().getName(), "save", "saved",squadSaved);
		
		return squadSaved;
	}

}
