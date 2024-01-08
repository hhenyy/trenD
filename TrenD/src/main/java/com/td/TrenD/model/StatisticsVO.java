package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statistics")
public class StatisticsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "staNo", nullable = false)
	private int staNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserVO userVO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trNo")
	private TrendVO trendVO;
}

