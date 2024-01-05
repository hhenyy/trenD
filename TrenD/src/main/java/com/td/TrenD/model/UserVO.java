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
@Table(name = "user")
public class UserVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId", nullable = false)
	private String userId;

	@Column(name = "userPw")
	private String userPw;

	@Column(name = "userName")
	private String userName;


	private String genCd;
	private String ageCd;
	private String locCd;

	@Column(name = "userEmail")
	private String userEmail;

	@Column(name = "userAuth")
	private Character userAuth;

	@Column(name = "userKey")
	private String userKey;

	@Column(name = "userDate")
	private Date userDate;

	@Column(name = "userUpdate")
	private Date userUpdate;

	@Column(name = "userDelYn")
	private Character userDelYn;
}
