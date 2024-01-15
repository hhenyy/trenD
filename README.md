# TrenD
중앙정보기술인재개발원 2차 프로젝트
<br><br>

### 프로젝트 링크
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

### 담당작업
