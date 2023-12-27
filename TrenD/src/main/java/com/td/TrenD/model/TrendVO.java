package com.td.TrenD.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "trend_tbl")
public class TrendVO {
	@Id
	private int trNo;
	private String userId;
	private String trSubject;
	private String trContent;
	private int trReadCount;

	@CreationTimestamp
	private Date trDate;

	@UpdateTimestamp
	private Date trUpdate;
	private char trDelYn;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cateCd")
	private CategoryVO category;

}