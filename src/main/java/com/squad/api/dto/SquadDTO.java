package com.squad.api.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="squadId")
public class SquadDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer squadId;
	
	
	private String squadName;

}
