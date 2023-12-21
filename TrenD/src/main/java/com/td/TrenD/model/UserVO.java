package com.td.TrenD.model;

import lombok.Data;
import java.util.Date;

@Data
public class UserVO {
	private String userId;
	private String userPw;
	private String userName;
	private String genCd;
	private String ageCd;
	private String locCd;
	private String userEmail;
	private char userAuth;
	private String userKey;
	private Date userDate;
	private Date userUpdate;
	private char userDelYn;
}
