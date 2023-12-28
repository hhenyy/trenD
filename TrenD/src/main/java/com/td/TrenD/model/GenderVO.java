package com.td.TrenD.model;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="gender_code")
public class GenderVO {
	@Id
	private String genCd;
	private String genNm;
	private char genDelYn;
	private String idx_gender;
}
