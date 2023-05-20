# Blog 프로젝트 (강의)

<div align="center">
  <img width="60%" src="https://github.com/aozp73/aozp73/assets/122352251/008b21bb-aba2-4272-af58-0fd81cbd0697"/>
</div> </br>
&nbsp; ① 해당 프로젝트는 2주간 진행하는 Blog 강의를 들으면서 상세한 개념 학습과 더불어 진행되었음 </br>
&nbsp; ② 강의 전 개인적으로 혼자 진행해보고 싶은 욕심에 혼자 진행한 프로젝트는 따로 Git Main에 첨부하였음</br></br>
&nbsp;&nbsp;📝 Git Repository&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp; https://github.com/aozp73/blog_lecture_step3 </br>
</br></br>

## 만들면서 느낀 점 / 어려웠던 부분
&nbsp;&nbsp;※ 프로필 사진 기능 외 나머지 기능을 혼자 독학으로 구현하고자 목표를 설정하면서 해당 기능은 제외하고 구현

#### MVC 각 layer의 책임
  - 개념적으론 Controller는 인증, 권한, 유효성 검사 / Service는 트랜잭션, 비즈니스, 권한 처리 / Repository는 JPA나 Mybatis를 활용하여 DB와 통신한다고 배웠다.
  - 실제로 뭔가를 만들고, 유지 보수와 디버깅 과정에 대해서 생각해 볼 기회를 가질 수 있었고, 각 layer의 책임을 분리하는 것이 얼마나 중요할지 체감할 수 있었다.
  - 이번 프로젝트는 다른 여러 포트폴리오의 결과물들에 비해 간단한 편이기 때문에 에러 메시지에서 지체한 부분은 적었다. 
  - Controller와 Service, View의 책임을 구분하지 않은 상태에서 코드가 복잡했다면 유지 보수와 디버깅에 많은 시간을 할애했을 것이다.
  - 어느 부분에서 문제가 생겼는지, 정확하고 올바르게 이해하려면 최초 코드 작성 때부터 생각해야 될 부분이다.
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
### 1단계 (완료)
- 회원가입, 로그인, 글쓰기, 글목록보기, 글상세보기, 글삭제, 글수정, 섬네일 보기
### 2단계 (완료)
- 댓글쓰기, 댓글목록보기, 댓글삭제, 프로필 사진추가 및 변경하기, 프로필 사진보기
### 3단계 (완료)
- 좋아요하기, 좋아요취소, 좋아요보기
### 4단계 (완료)
- 아이디중복체크, 회원수정하기
</br>

## 기능상세 (기술블로그 첨부)
&nbsp;&nbsp;※ 각 Topic 진행 후 Test 진행, 같이 첨부하였음
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
<img width="60%" src="https://github.com/aozp73/aozp73/assets/122352251/ff6f6616-d96f-4b1c-a1d6-cf8b05469de7"/></br></br>

#### 글쓰기 (summernote) 
<img width="60%" src="https://github.com/aozp73/aozp73/assets/122352251/7b17b230-2ecd-408d-8b43-fa3e3ce6213a"/></br></br>

#### 글 수정/삭제, 좋아요, 썸네일
<img width="60%" src="https://github.com/aozp73/aozp73/assets/122352251/457fdbd2-8a96-4e8f-997b-01c02d900b8a"/></br></br>

#### 개인정보 수정/로그아웃
<img width="60%" src="https://github.com/aozp73/aozp73/assets/122352251/25b5cc41-867e-4c5c-8832-c11e7227a529"/></br></br>
</br>

## 모델링
<img width="80%" src="https://github.com/aozp73/aozp73/assets/122352251/75e376c2-8b16-4507-971b-bed66915cf39"/>

### 1단계 (완료)
- User
- Board
### 2단계 (완료)
- Reply
### 3단계 (완료)
- Love
</br>
