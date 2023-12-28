package com.td.TrenD.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="age_code")
public class AgeVO {
	@Id
	private String ageCd;
	private String ageNm;
	private char ageDelYn;
	private String idx_age;
}
