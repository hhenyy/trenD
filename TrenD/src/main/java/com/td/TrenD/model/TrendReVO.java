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

	@Column(name = "trNo", insertable = false, updatable = false)
	private int trNo;

	@ManyToOne(cascade = CascadeType.ALL)
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

	public void synchronizeRefWithTrReNo() {
		this.trReRef = this.trReNo;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "trNo")
	private TrendVO trendVO;

	public String getCateCd() {
		return trendVO.getCategoryVO().getCateCd();
	}
}
