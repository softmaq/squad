package com.squad.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.squad.api.exception.MemberNaoEncontradoException;
import com.squad.api.exception.SquadJaExisteException;
import com.squad.api.exception.SquadNaoEncontradaException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SquadNaoEncontradaException.class)
	public ResponseEntity<Object> handleSquadNaoEncontradaException(SquadNaoEncontradaException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();
		ErrorMessageType errorMessageType = ErrorMessageType.SQUAD_NAO_ENCONTRADA;

		ErrorMessage body = createErrorMessageBuilder(status, errorMessageType, detail).build();		
		
		return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(SquadJaExisteException.class)
	public ResponseEntity<Object> handleSquadJaExisteException(SquadJaExisteException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		String detail = ex.getMessage();
		ErrorMessageType errorMessageType = ErrorMessageType.SQUAD_JA_EXISTE;

		ErrorMessage body = createErrorMessageBuilder(status, errorMessageType, detail).build();		
		return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(MemberNaoEncontradoException.class)
	public ResponseEntity<Object> handleMemberNaoEncontradoException(MemberNaoEncontradoException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();
		ErrorMessageType errorMessageType = ErrorMessageType.MEMBER_NAO_ENCONTRADO;
		ErrorMessage body = createErrorMessageBuilder(status, errorMessageType, detail).build();		

		return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
	}

	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if ( body == null ) {
			body =  ErrorMessage.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		} else if ( body instanceof String ) {
			body =  ErrorMessage.builder()
					.title((String) body)
					.status(status.value())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	
	private ErrorMessage.ErrorMessageBuilder createErrorMessageBuilder(HttpStatus status, ErrorMessageType errorMessageType, String detail) {
		return ErrorMessage.builder()
				.status(status.value())
				.type(errorMessageType.getUri())
				.title(errorMessageType.getTitle())
				.detail(detail);
	}
}
