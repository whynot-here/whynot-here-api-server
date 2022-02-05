# DB 초안

사용자 인증/인가 제외하고 초기 CRUD API개발 선행 결정



<br />

## 1. 스키마

![image](https://user-images.githubusercontent.com/42775225/150680599-f04bbbbf-b3dc-457a-95bc-21468f86af33.png)



- job : 분야(직군) 관리 테이블
  - job_post : job과 post의 중간 테이블
- post : 모집 공고 관리 테이블
  - post_favorite : 좋아요 관리 테이블
  - post_apply : 지원자 관리 테이블
- user : 사용자 관리 테이블



<br />

## 2. 리뷰

1. 사용자 인증/인가 제외하고 초기 CRUD API개발 선행 결정

2. user테이블에 분야(직군) 정보는 넣지 않기로 결정

   - 공고 신청 시 직군을 선택하기로 결정

      ==> post_apply 테이블에 분야(직군) 정보 추가

   - 사용자 직군 수집은 추후 필요하다고 결정되면 추가

3. comment 테이블 추가 필요





<br />

#### 참석자

<table> 	
  <tr>    
    <td><img src="https://avatars.githubusercontent.com/u/42775225?v=4" width=100px alt="상진"/></td> 	    
    <td><img src="https://avatars.githubusercontent.com/u/37795866?v=4" width=100px alt="석재"/></td> 	
  </tr>	
  <tr>	    
    <td><a href="https://github.com/osj3474" target="_blank">상진</a></td> 	    
    <td><a href="https://github.com/doljae" target="_blank">석재</a></td> 	
  </tr>  
</table>

