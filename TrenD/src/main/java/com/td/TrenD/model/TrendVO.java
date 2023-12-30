package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trend_tbl")
public class TrendVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trNo", nullable = false)
	private int trNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserVO userVO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cateCd")
	private CategoryVO categoryVO;

	@Column(name = "trSubject")
	private String trSubject;

	@Column(name = "trContent")
	private String trContent;

	@Column(name = "trReadCount")
	private Integer trReadCount;

	@Column(name = "trDate")
	private Timestamp trDate;

	@Column(name = "trUpdate")
	private Timestamp trUpdate;

	@Column(name = "trDelYn")
	private Character trDelYn;
}