package com.td.TrenD.dao;

import com.td.TrenD.model.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserVO, String> {
}
