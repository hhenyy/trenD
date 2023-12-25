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

	@ManyToOne
	@JoinColumn(name = "cateCd", referencedColumnName = "cateCd" , insertable = false, updatable = false)
	private CategoryVO categoryVO;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int trNo;
	private String userId;
	private String cateCd;
	private String trSubject;
	private String trContent;
	private int trReadCount;

	@CreationTimestamp
	private Date trDate;

	@UpdateTimestamp
	private Date trUpdate;
	private char trDelYn = '\0';
	// DB 데이터 null값인 경우 기본값 공백문자로 설정
}
