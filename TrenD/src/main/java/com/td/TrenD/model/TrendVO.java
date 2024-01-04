package com.td.TrenD.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

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
	private Timestamp trDate;

	@UpdateTimestamp
	private Timestamp trUpdate;
	private char trDelYn;

	@ManyToOne
	@JoinColumn(name = "cateCd", insertable = false, updatable = false)
	private CategoryVO category;

}