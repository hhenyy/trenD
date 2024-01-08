package com.td.TrenD.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_code")
public class CategoryVO {
	@Id
	@Column(name = "cateCd", nullable = false)
	private String cateCd;

	@Column(name = "cateNm")
	private String cateNm;

	@Column(name = "cateDelYn")
	private Character cateDelYn;

	@Column(name = "idx_category")
	private String idx_category;
}
