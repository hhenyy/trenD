package com.td.TrenD.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "category_code")
public class CategoryVO {

	@Id
	private String cateCd;
	private String cateNm;
	private char cateDelYn;
	private String idx_category;

}