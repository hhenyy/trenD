package com.td.TrenD.model;

import lombok.Data;
import java.util.Date;

@Data
public class TrendVO {
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
