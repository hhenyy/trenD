package com.td.TrenD.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "trend_tbl")
public class TrendVO {

	@Id
	private int trNo;

	private String userId;
	private String cateCd;
	private String trSubject;
	private String trContent;
	private int trReadCount;
	private Date trDate;
	private Date trUpdate;
	private char trDelYn;
}