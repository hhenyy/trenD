package com.td.TrenD.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RePagingVO extends TrendReVO{
	private int page;
	private int recordSize;
	private int pageSize;
}
