package com.squad.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "memberId")
@Table(name="TB_MEMBER")
@Entity
public class Member implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_MEMBER")
	private Integer memberId;

	@Column(name="DS_NAME")
	private String memberName;
	
	@Column(name="DS_JOB_TITLE")
	private String jobTitle;
	
	@ManyToOne
	@JoinColumn(name="id_squad")
	private Squad squad;
}
