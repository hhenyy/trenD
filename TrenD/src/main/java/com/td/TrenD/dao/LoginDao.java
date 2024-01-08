package com.td.TrenD.dao;

import com.td.TrenD.model.AgeVO;
import com.td.TrenD.model.GenderVO;
import com.td.TrenD.model.LocationVO;
import com.td.TrenD.model.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoginDao extends JpaRepository<UserVO, String> {

    // JPQL
    @Query("select a from AgeVO a where a.ageDelYn = 'n' order by a.idx_age")
    // 기본 findAll 은 기본 메소드 이기 때문에 해당 UserVO만 쓸 수 있고 자기가 임의로 해둔 거는 자신의 내용
    public List<AgeVO> findSelectAgeAll();

    @Query("select g from GenderVO g where g.genDelYn = 'n' order by g.idx_gender")
    public List<GenderVO> findSelectGenderAll();

    @Query("select loc from LocationVO loc where loc.locDelYn = 'n' order by loc.idx_location")
    public List<LocationVO> findSelectLocAll();

    @Query(value = "select count(*) from user where userId = :userId and userDelYn = 'n'" , nativeQuery = true)
    public int checkId(@Param("userId") String userId);

    @Query(value = "select count(*) from user where userName = :userName and userDelYn = 'n'" , nativeQuery = true)
    public int checkNick(@Param("userName") String userName);

    public UserVO findByUserId(String userId);

    @Modifying
    @Query(value ="update UserVO SET userDelYn = 'y', userUpdate = CURRENT_TIMESTAMP WHERE userId = :userId")
    public void deleteUser(@Param("userId") String userId);

    Optional<UserVO> findByUserNameAndUserEmail(String userName, String userEmail);

}
