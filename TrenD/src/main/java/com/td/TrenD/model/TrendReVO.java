package com.td.TrenD.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "trendRe")
public class TrendReVO {
	@Id
	private int trReNo;
	private int trNo;
	private String userId;
	private int trReRef;
	private int trReLev;

	@CreationTimestamp
	private Date trReDate;

	@UpdateTimestamp
	private Date trReUpdate;
	private char trReDelYn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trNo", insertable = false, updatable = false)
	private TrendVO trend;
}
