# Blog 프로젝트 (강의)

<div align="center">
  <img width="60%" src="https://github.com/aozp73/aozp73/assets/122352251/008b21bb-aba2-4272-af58-0fd81cbd0697"/>
</div> </br> </br>
&nbsp; ① 해당 프로젝트는 2주간 진행하는 Blog 강의를 들으면서 상세한 개념 학습과 더불어 진행되었음 </br>
&nbsp; ② 강의 전 개인적으로 혼자 진행해보고 싶은 욕심에 진행한 개인 블로그 프로젝트는 Git Repository에서 진행</br></br>
&nbsp;&nbsp;📝 Git Repository&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp; https://github.com/aozp73/blog </br>
</br></br>

## 만들면서 배운 점
#### HTTP Status
- 200(OK), 201(Created), 400(Bad Request), 401(Unauthorized), 403(Forbidden), 500(Internal Server Error) 등
- 위와 같은 상태코드를 Front에 응답하여 프로토콜에 따라 효율적인 결과 처리 및 인지 가능
#### CustomException 처리 (유효성/인증/권한)
- 정상적인 접근 또는 PostMan을 통한 비정상적인 접근 시 유효성/인증/권한 처리를 해야 함
- CustomException 처리를 하여, 어떤 과정으로 이런 처리를 할 수 있는지 학습
- 해당 프로젝트 이후엔 Security, AOP, 리플렉션을 활용하여 처리하는 방법을 학습하였음
#### Session 활용
- Session을 활용하여 어떤 의미에선 RestFul한 상태로 이전에 왔던 어떤 사용자인지 기록
- 인증 및 권한 처리에 활용할 수 있었으며, View에선 if문을 통해 로그인한 유저에게만 구별하여 아이콘 등을 출력 가능
#### BackEnd - FrontEnd 협업
- Front에서는 최대한 화면을 그리는 것에 집중 해야함
- 따라서, 비지니스 로직같은 경우 최대한 Back단에서 처리하여 넘겨줘야 함
#### Mybatis CRUD 및 Join Query
- 기본적인 CRUD를 생성하여 진행
- 여러 Table을 조회하여 뿌려야 할 경우 inner join, outer join 활용
- 한방 Query & primitive한 Query로 여러번 조회하는 것을 배웠는데 모듈적인 측면에서는 primitive한 Query를 사용
#### DTO 생성
- Request, Response 통신 시 DTO를 활용하여 데이터를 주고 받음
- 여러 화면에서 공통적으로 데이터를 뿌리더라도 무조건 DTO는 별개로 생성
- 추후에 요구사항이 어떻게 바뀔지 예측할 수 없으므로, 특정 화면에서 필요한 데이터가 달라질 가능성이 많음
</br>

## 기술스택
- JDK 11
- Springboot 2.7.8
- Mybatis
- Test-h2 DB
- MySQL
- JSP
</br>

## 기능정리
&nbsp; ※ 4~5단계에 페이징, 검색, 아이디 중복체크는 강의 시간 부족으로 진행 x </br>
&nbsp;&nbsp;&nbsp;&nbsp; 혼자 독학하여 진행, (개인) 블로그 프로젝트에 반영

### 1단계 (완료)
- 회원가입, 로그인, 글쓰기, 글목록보기, 글상세보기, 글삭제, 글수정, 섬네일 보기
### 2단계 (완료)
- 댓글쓰기, 댓글목록보기, 댓글삭제, 프로필 사진추가 및 변경하기, 프로필 사진보기
### 3단계 (완료)
- 좋아요하기, 좋아요취소, 좋아요보기


</br>

## 기능상세 (기술블로그 첨부)
&nbsp;&nbsp;※ 각 Topic 진행 후 Test 진행, 해당되는 기술 포스팅에 포함
#### 1단계
- 화면구현 : JSP, JQuery, BootStrap, Grid, Flex 등을 활용하여 진행 (https://blog.naver.com/aozp73/223107329485)
- 유효성/인증/권한처리 : CustomException을 만들어 비정상적인 접근시 안내문구 출력 (https://blog.naver.com/aozp73/223107332914)
- 로그인 : 요청 DTO생성 및 통합테스트 진행, 로그인시 Session에 해당 Object 저장 (https://blog.naver.com/aozp73/223107334056)
- 게시글 작성 : Session 체크하여 로그인 유저가 아니라면 CustomException처리 (https://blog.naver.com/aozp73/223107338510)
- 게시글 목록 : 테스트 시 ObjectMapper로 결과 확인, Query에 InnerJoin활용 (https://blog.naver.com/aozp73/223107340589)
- 게시글 상세 : DTO는 요구사항이 달라질 수 있으므로 재사용하지 않음 (https://blog.naver.com/aozp73/223107343107)
- 게시글 삭제 : DELETE는 Request Body가 없으므로 유효성 검사 x (https://blog.naver.com/aozp73/223107344071)
- 게시글 수정 : AJAX 통신을 활용, JSON으로 데이터 받아서 진행 (https://blog.naver.com/aozp73/223107348863)
- 섬네일 : 해당 프로젝트에선 Base64를 직접 파싱 x, JSoup 활용 (https://blog.naver.com/aozp73/223107350532)
#### 2단계
- 댓글 쓰기 : 단계별 진행, 새로운 Reply Table 생성 후 MyBatis 기본 CRUD 작성 (https://blog.naver.com/aozp73/223107354637)
- 댓글 목록보기 : 한방 Query, Primitive Query 학습. Privimitve Query로 모듈화하기 (https://blog.naver.com/aozp73/223107358897)
- 댓글 삭제 : AJAX 통신과 부분 ReLoading(id값을 타겟으로 remove) 학습 (https://blog.naver.com/aozp73/223107359773)
- 프로필 사진 : UUID를 활용하여 하드디스크에 Base64 저장, 경로 View에 반환 (https://blog.naver.com/aozp73/223107361778)
#### 3단계
- 좋아요 : addClass, removeClass를 활용하여 DB조회 후 좋아요 하기/취 구현 (https://blog.naver.com/aozp73/223107365360)
</br>

## 기능캡쳐
#### 프로필사진 변경 
<img width="50%" src="https://github.com/aozp73/aozp73/assets/122352251/ff6f6616-d96f-4b1c-a1d6-cf8b05469de7"/></br></br>

#### 글쓰기 (summernote) 
<img width="50%" src="https://github.com/aozp73/aozp73/assets/122352251/7b17b230-2ecd-408d-8b43-fa3e3ce6213a"/></br></br>

#### 글 수정/삭제, 좋아요, 썸네일
<img width="50%" src="https://github.com/aozp73/aozp73/assets/122352251/457fdbd2-8a96-4e8f-997b-01c02d900b8a"/></br></br>

#### 개인정보 수정/로그아웃
<img width="50%" src="https://github.com/aozp73/aozp73/assets/122352251/25b5cc41-867e-4c5c-8832-c11e7227a529"/></br></br>
</br>

## 모델링
<div align="center">
<img width="60%" src="https://github.com/aozp73/aozp73/assets/122352251/81493c20-8d8e-423a-92b0-a9dafabe02bc"/>
</div>


### 1단계 (완료)
- User
- Board
### 2단계 (완료)
- Reply
### 3단계 (완료)
- Love
</br>
