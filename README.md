# TrenD
중앙정보기술인재개발원 2차 프로젝트
<br><br>

### 프로젝트 링크
### [TrenD 방문하기](http://15.164.220.232/)
<br>

### 소개
실시간 검색어를 목록에서 확인하고 관심있는 주제를 선택하여 이야기를 나눌 수 있는 커뮤니티입니다.  
Google Trends 데이터를 이용해 실시간 정보를 불러오며 사용자가 원하는 주제를 직접 등록하여 의견을 공유할 수도 있습니다.  
의견이 일정 갯수 이상 등록이 되면 성별, 나이, 지역에 대한 통계가 출력됩니다.<br><br>

### 개발 기간
22.12.18 - 23.01.17 (30일)<br><br>

### 프로젝트 구조도
<img width="max" alt="KakaoTalk_Photo_2024-01-15-12-32-31" src="https://github.com/Meimeidays/trenD/assets/55777781/89bb7582-c0da-422b-a557-fa3f12473500">

### DB 설계
![TrenD-Database](https://github.com/JunHyeokSeo/trenD/assets/55777781/d6d4da99-90f7-4e04-88e0-f8dcd3d51233)
<br><br>

### 주요기능 설명
| 기능 | 담당자 | 상세설명 |
| --- | --- | --- |
| 실시간 검색어 크롤링 | 함혜선| REST API 형태로 Serp api를 사용하여 Google Trends 페이지 크롤링 </br> OPENAI API 연결하여 챗봇 구현</br>(답변 내용을 반응형 stream 구현/server side event 기술 사용)  |
| OAuth2.0 기반 소셜로그인 | 백대현 |  로그인 구현 및 smtp 프로토콜을 이용하여 임시 비밀번호 전송 및 OAuth2.0 naver 로그인 구현|
| 마이페이지 | 정소옥 | REST API 형태로 게시글 및 댓글 목록을 갱신<br> 접속자가 관리자(admin)일 경우 전체 목록을 조회, 유저일 경우 해당 유저가 작성한 목록만 출력하도록 구현<br> JPA Repository에서 제공하는 Page Interface를 활용한 게시판 목록 페이징 구현<br> 각 목록에서 특정 항목 클릭시 해당 항목의 상세페이지로 이동 |
| 목록 페이지 및 게시판 내부 검색 &nbsp;&nbsp;&nbsp;| 여인범 &nbsp;&nbsp;| REST API 형태로 게시판별 데이터 목록 및 조건에 해당하는 데이터 조회 구현</br>JPA Repository에서 제공하는 Page Interface를 활용한 게시판 목록 페이징 구현 |
| 게시글 CRUD 및 통합 검색 | 김선홍 | Google Trends 리스트 조회 시 글 자동 등록</br>REST API 적용하여 검색결과를 한 페이지 내에서 트렌드/커뮤니티 개별 페이징 구현 |
| 통계 페이지 | 빈상욱 | 각 게시판(키워드) 별 접속 횟수 및 관심도를 워드클라우드를 통해 시각적으로 구현<br>각 게시판을 방문한 유저의 성별/지역/연령대 정보를 담은 그래프 출력 및 REST API 적용
| 댓글 CRUD | 서준혁 | REST API 형태로 댓글 CRUD 구현</br>댓글 목록 페이징 구현|
<br><br>

### 담당작업
1. 메인페이지 구글 트렌드 실시간 데이터 처리 (기여도 100%)<br>
-Rest API 방식으로 구현<br>
-Serp api (Google Trends 페이지 크롤링 하는 API)로 Google Trend에서 실시간 데이터를 가져 와서 최신 기사를 출력하는 기능 구현<br>
![스크린샷 2024-02-18 130942](https://github.com/hhenyy/trenD/assets/141230104/8873b16e-d1c6-4065-9277-496c96ef0d8c)
<br>

2. openAI Api를 이용한 대화형 챗봇 구현 (기여도 100%)<br>
-답변 내용을 반응형 stream 구현: webflux (반응형 및 비동기적인 웹 애플리케이션 개발을 지원하는 모듈)과, <br>
 reactor 라이브러리를 이용하여 reactive(반응형) 기능 구현<br>
![스크린샷 2024-02-18 131002](https://github.com/hhenyy/trenD/assets/141230104/9d7fd008-1fda-41e2-815f-c2c8c0d6ee26)


<br>

### 목표 달성 여부 및 느낀점 
Spring boot 환경설정과 intelliJ를 사용하여 REST API방식, JPA로 이번 프로젝트를 진행 하여 다양한 개발 환경을 시도 하는 것에서 <br>
좋은 평가를 받았고, 팀원들과의 의사소통에서도 처음부터 끝까지 함께 팀 회의를 통해서 설계, 기획을 했으며, <br>
또 프로젝트 진행 상황을 서로 공유 하면서 프로젝트를 진행하여 잘 마무리를 하였습니다.
