package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location_code")
public class LocationVO {
	@Id
	@Column(name = "locCd", nullable = false)
	private String locCd;

	@Column(name = "locNm")
	private String locNm;

	@Column(name = "locDelYn")
	private Character locDelYn;

	@Column(name = "idx_location")
	private String idx_location;
}