package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gender_code")
public class GenderVO {
	@Id
	@Column(name = "genCd", nullable = false)
	private String genCd;

	@Column(name = "genNm")
	private String genNm;

	@Column(name = "genDelYn")
	private Character genDelYn;

	@Column(name = "idx_gender")
	private String idx_gender;
}
