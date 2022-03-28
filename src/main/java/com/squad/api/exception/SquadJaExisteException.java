package com.squad.api.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SquadJaExisteException extends NegocioException {

	private static final String MSG_JA_EXISTE = "O nome de Squad '%s' já está cadastrado!";
	private static final String ENTITY = "Squad";
	
	private static final long serialVersionUID = 1L;

	public SquadJaExisteException(String squadName) {
		super(String.format(MSG_JA_EXISTE,squadName));
		log.warn("entity={} message={}",ENTITY, String.format(MSG_JA_EXISTE,squadName));
	}
	
}
