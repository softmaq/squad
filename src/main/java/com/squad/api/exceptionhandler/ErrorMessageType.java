package com.squad.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorMessageType {

	SQUAD_NAO_ENCONTRADA("squad-nao-encontrada","Recurso não encontrado"),
	SQUAD_JA_EXISTE("squad-ja-existe","Recurso já existe"),
	MEMBER_NAO_ENCONTRADO("member-nao-encontrado","Recurso não encontrado");
	
	
	private String title;
	private String uri;
	
	private ErrorMessageType(String path, String title) {
		this.uri = "localhost://squad" + path;
		this.title = title;
	}
}
