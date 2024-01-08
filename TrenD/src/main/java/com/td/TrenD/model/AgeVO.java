package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "age_code")
public class AgeVO {
	@Id
	@Column(name = "ageCd", nullable = false)
	private String ageCd;

	@Column(name = "ageNm")
	private String ageNm;

	@Column(name = "ageDelYn")
	private Character ageDelYn;

	@Column(name = "idx_age")
	private String idx_age;
}
