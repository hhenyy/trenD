package com.td.TrenD.dao;

import com.td.TrenD.model.StatisticsVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsDao extends JpaRepository<StatisticsVO,Integer> {
	//jpa에서 제공하는 인터페이스들은 자신을 상속 받는 인터페이스가 있다면 JPA가 구현체를 자동으로 생성하여 
	//런타임에 빈으로 등록합니다. 그래서 직접 구현 클래스를 작성하지 않아도 스프링이 해당 인터페이스를 구현한 클래스를 생성하여 빈으로 등록해줍니다.
	//스프링 데이터 JPA가 JpaRepository 인터페이스를 상속받는 리포지토리 인터페이스를 만나면, 
	//이를 구현한 클래스를 동적으로 생성하고 생성한 클래스를 빈으로 등록하여 의존성 주입을 해주기 때문 이다.
	
	//존재하는 모든 trNo값을 가져와(중복 제거) List에 저장하는 메소드
	@Query("select DISTINCT s.trendVO.trNo from StatisticsVO s")
	//jsql 작성 시에는 엔티티에서 탐색을 수행하기 때문에 테이블명이 아닌 해당 엔티티의 경로를 사용해야 한다
	List<Integer> getTrNo();

	//statistics 테이블의 trNo는 해당 게시글을 조회한 횟수를 의미. 
	//이때, 여러 개의 게시글이 같은 카테고리를 가지고 있을 가능성이 큼. 따라서 이 부분을 처리해야 함
//	@Query("select count(s) from com.td.TrenD.model.StatisticsVO s where s.trNo = :c")
//	int count(@Param("c") Integer c);
//
//	@Query("select s.staNo from StatisticsVO s left join s.trendVO t where t.cateCd= :categoryOpt")
//	List<Integer> count(@Param("categoryOpt") String categoryOpt);
	
	@Query("select count(s) from StatisticsVO s left join s.trendVO t where s.trendVO.categoryVO.cateCd= :categoryOpt")
	//jpql로 올바르게 조인하는 법. @JoinColumn 등을 통해 연관관계가 설정되어 있는 테이블을 조인하는 방법 사용 중
	//이때 연관관계의 주인(외래키를 가진 쪽)을 통해 조인해야 함
	//레프트 조인 규칙에 따라, 왼쪽엔 기준이 되는 엔티티를 위치시킴. 
	//조인의 대상이 되는 엔티티의 경우, 단순히 엔티티명을 입력하는 것이 아닌 s.trendVO처럼 연관관계의 주인 엔티티를 통해야 한다
	//또한 조건절에는 t.cateCd처럼 조인의 대상이 되는 엔티티의 컬럼을 사용할 수 없음. 해당 엔티티의 위치를 모르기 때문 아닐까?
	//다만 조인을 통한 select의 경우 양 테이블 모두의 컬럼이 출력되기에 그 정보를 이용하는 중
	//cateCd는 곧 t.cateCd를 의미함
	//jpql에선 *을 사용할 수 없다. 대신 엔티티 약어를 사용해야 함. 무조건
	int count(@Param("categoryOpt") String categoryOpt);
	
	
//	 @Query(value = "select ageNm from (select s.staNo, s.userId, c.cateNm " +
//             "from statistics s " +
//             "left join trend_tbl t on s.trNo = t.trNo " +
//             "left join category_code c on t.cateCd = c.cateCd) A " +
//             "left join user u on A.userId = u.userId " +
//             "left join age_code a on u.ageCd = a.ageCd " +
//             "where cateNm = :cateNm", nativeQuery = true)
//	List<String> countLocation(@Param("cateNm") String cateNm);
//	//세 개의 테이블을 조인하여 그 갯수를 세는 메소드가 필요
	 
	//네이티브 쿼리문을 사용하면 모든 연령대가 출력되어 String List에 저장된다만.....별로.
//	 select ageNm from (select  s.staNo,s.userId,cateNm from statistics s 
//			 left join trend_tbl t on s.trNo = t.trNo 
//			 left join category_code c on t.cateCd=c.cateCd) A
//			 left join user u on A.userId=u.userId 
//			 left join age_code a on u.ageCd=a.ageCd
//			 where cateNm='트렌드'; 
	//이 쿼리문을 JPA 시스템을 이용해 적용시키려면 어떻게 해야 할까? 
	
//	@Query("SELECT s FROM StatisticsVO s WHERE s.userVO.userId = :userId AND s.trNo = :trNo")
//    StatisticsVO findByUserIdAndTrNo(@Param("userId") String userId, @Param("trNo") int trNo);
	@Query("SELECT s FROM StatisticsVO s WHERE s.userVO.userId = :userId AND s.trendVO.trNo = :trNo")
    StatisticsVO findByUserIdAndTrNo(@Param("userId") String userId, @Param("trNo") int trNo);
	
	
}