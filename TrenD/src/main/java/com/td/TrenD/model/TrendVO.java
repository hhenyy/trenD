package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;
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

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserVO userVO;

	@ManyToOne
	@JoinColumn(name = "cateCd")
	private CategoryVO categoryVO;

	@Column(name = "trSubject")
	private String trSubject;

	@Column(name = "trContent")
	private String trContent;

	@Column(name = "trReadCount")
	private Integer trReadCount;

	@Column(name = "trDate")
	private Date trDate;

	@Column(name = "trUpdate")
	private Date trUpdate;

	@Column(name = "trDelYn")
	private Character trDelYn;
}