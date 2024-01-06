package com.td.TrenD.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RePagingVO extends TrendReVO{
	private int page;
	private int pageListSize;
	private int itemPerPage;
}
