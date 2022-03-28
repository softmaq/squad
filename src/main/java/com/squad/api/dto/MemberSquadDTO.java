package com.squad.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.squad.api.domain.Squad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of ="memberId")
public class MemberSquadDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer memberId;
	
	@NotBlank
	private String memberName;
	
	@NotBlank
	private String jobTitle;
	
	@JsonIgnore
	private Squad squad;

}
