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

	@Column(name = "trNo")
	private int trNo;

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserVO userVO;

	@Column(name = "trReRef")
	private Integer trReRef;

	@Column(name = "trReLev")
	private Integer trReLev;

	@Column(name = "trReDate")
	private Timestamp trReDate;

	@Column(name = "trReUpdate")
	private Timestamp trReUpdate;

	@Column(name = "trReDelYn")
	private Character trReDelYn;

	@Column(name = "trReContent")
	private String trReContent;
}
