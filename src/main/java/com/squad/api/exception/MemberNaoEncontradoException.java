package com.squad.api.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberNaoEncontradoException extends NegocioException {

	private static final String MSG_MEMBER_ID_NAO_FOI_ENCONTRADO = "O Member com id %d não foi encontrado!";
	private static final String ENTITY = "Member";
	private static final long serialVersionUID = 1L;

	public MemberNaoEncontradoException(String mensagem) {
		super(mensagem);
		log.warn(" entity={} message={}", ENTITY, mensagem);
	}

	public MemberNaoEncontradoException(Long id) {
		super(String.format(MSG_MEMBER_ID_NAO_FOI_ENCONTRADO,id));
		log.warn("entity={} method={} meberId={} message={}",ENTITY,"findById", id, String.format(MSG_MEMBER_ID_NAO_FOI_ENCONTRADO,id));
	}
	
}
