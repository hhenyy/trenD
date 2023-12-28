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
@Table(name = "trendRe")
public class TrendReVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trReNo", nullable = false)
	private int trReNo;

	@Column(name = "trNo")
	private int trNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserVO userVO;

	@Column(name = "trReRef")
	private Integer trReRef;

	@Column(name = "trReLev")
	private Integer trReLev;

	@Column(name = "trReDate")
	private Date trReDate;

	@Column(name = "trReUpdate")
	private Date trReUpdate;

	@Column(name = "trReDelYn")
	private Character trReDelYn;

	@Column(name = "trReContent")
	private String trReContent;
}
