package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;

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

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserVO userVO;

	private int trNo;

	private Integer trReRef;

	private Integer trReLev;

	private Timestamp trReDate;

	private Timestamp trReUpdate;

	private Character trReDelYn;

	private String trReContent;

	public void synchronizeRefWithTrReNo() {
		this.trReRef = this.trReNo;
	}
}
