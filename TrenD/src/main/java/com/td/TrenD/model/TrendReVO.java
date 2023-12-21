package com.td.TrenD.model;

import lombok.Data;
import java.util.Date;

@Data
public class TrendReVO {
	private int trReNo;
	private int trNo;
	private String userId;
	private int trReRef;
	private int trReLev;
	private Date trReDate;
	private Date trReUpdate;
	private char trReDelYn;
}
