package com.td.TrenD.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "location_code")
public class LocationVO {
	@Id
	private String locCd;
	private String locNm;
	private char locDelYn;
	private String idx_location;
}
