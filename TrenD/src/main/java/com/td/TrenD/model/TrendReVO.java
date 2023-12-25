package com.td.TrenD.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "trendre")
@Entity
public class TrendReVO {

	@Id
	private int trReNo;

	@ManyToOne
	@JoinColumn(name = "trNo")
	private TrendVO trNo;

	private String userId;
	private int trReRef;
	private int trReLev;
	private Date trReDate;
	private Date trReUpdate;
	private char trReDelYn;
}
