package com.squad.api.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SquadNaoEncontradaException extends NegocioException {

	private static final String MSG_SQUAD_ID_NAO_FOI_ENCONTRADA = "A Squad com id %d não foi encontrada!";
	private static final long serialVersionUID = 1L;
	private static final String ENTITY = "Squad";

	public SquadNaoEncontradaException(String mensagem) {
		super(mensagem);
		log.warn("message={}", mensagem);
	}

	public SquadNaoEncontradaException(Long id) {
		super(String.format(MSG_SQUAD_ID_NAO_FOI_ENCONTRADA,id));
		log.warn("entity={} method={} squadId={} message={}",ENTITY,"findById", id, String.format(MSG_SQUAD_ID_NAO_FOI_ENCONTRADA,id));
		
	}
	
}
