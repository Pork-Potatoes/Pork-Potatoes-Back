# 🎓 신촌대 맛집전공 - 백엔드 🎓
<img src = "https://github.com/Pork-Potatoes/Pork-Potatoes-Front/raw/main/food-major/src/assets/banner.png"/>

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FPork-Potatoes%2FPork-Potatoes-Back&count_bg=%23E0755F&title_bg=%239E5D50&icon=&icon_color=%23E0755F&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

## 🍏 백엔드 팀원 소개
| [김민주](https://github.com/MINJU-KIMmm)                                                                                             | [김시연](https://github.com/siyeonkm)                                                                       | [박현아](https://github.com/hak2711)                                                                                                                 | [이서정](https://github.com/seojunglee)                                                                     |
|--------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| <img src = "https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/KimMinju.jpeg" width="100%"/>                                   | <img src = "https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/KimSiyeon.jpeg" width="98%"/>         | <img src = "https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/ParkHyunah.jpeg" width="100%"/>                                                 | <img src = "https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/LeeSeojung.jpeg" width="85%"/>        |
| [리뷰] 리뷰 검색, 리뷰 포스트</br> [메뉴] 메뉴 열람, 임시 메뉴 저장</br> [폴더] pin 추가 및 삭제</br> [신고] 신고기능</br> [기타] 리드미 작성 등. | [유저] 소셜로그인, 프로필수정, 유저 정보 수정, 학교 인증, 유저 탈퇴 </br> [폴더] 폴더 리스트로 반환</br> [DB] 리뷰 DB 구축 등. | [레스토랑] 리뷰 작성시 맛집 이름 검색, 가게 정보 및 검색 결과 반환</br>[폴더] 폴더에 가게 추가, 폴더 반환, 폴더 수정 및 삭제 기능</br> [서버] 배포 등. | [리뷰] 리뷰 개별 반환, hot 리뷰, 리뷰 포스트, 최신 리뷰, 마이페이지 리뷰 열람</br> [DB] 레스토랑 DB 구축 등. |
## 🍎 프론트엔드 팀원 소개
| [이해린](https://github.com/dazzlynnnn)                                                            | [이윤지](https://github.com/L-Yunji)                                                                | [정드림](https://github.com/dream0214)                                                               |
|:----------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|
| <img src = "https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/LeeHaerin.jpeg" width="70%"/> | <img src = "https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/LeeYunji.jpeg" width="70%"/> | <img src = "https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/JungDream.jpeg" width="70%"/> |
| [마이페이지], [리스트페이지], <br/>헤더 컴포넌트, 리뷰 컴포넌트,</br>식당 컴포넌트 구현                | [메인페이지], 지도, 플로팅버튼 구현                                                                 | [메인페이지] [로그인팝업],</br> [검색결과 페이지] 구현                                                    |


-------------------
## 🍊 개요
'맛집전공'은 해당 대학가의 대학생들이 맛집에 대한 리뷰를 작성하는 대학가 맛집 커뮤니티 서비스입니다. 대학 인증을 받은 사용자는 직접 맛집 리뷰를 작성할 수 있고 그렇지 않은 사용자는 맛집 리뷰를 열람할 수 있습니다. 학교 인증을 통해 소속감과 리뷰에 대한 신뢰도를 높일 수 있고 또한 코로나로 인해 타격을 받았을 대학가 상권 부흥의 효과를 기대해볼 수 있습니다.
## 🍊 핵심 용어
### 사용자
사용자(User)는 소셜로그인을 통해 회원가입을 진행하게 되며 학교 이메일 인증을 통해 리뷰 작성 권한을 부여받습니다.
### 식당
식당(Restaurant)은 사용자가 리뷰할 수 있는 맛집을 의미합니다. 식당에 대한 기본 정보로 위치, 오픈 시간, 전화번호, SNS 계정 등을 제공하며 사용자가 쓴 리뷰의 별점을 통해 평점이 계산됩니다. 사용자는 식당을 즐겨찾기하여 가게리스트(Folder)로 관리하게 되며 이를 통해 앞으로 가고 싶은 맛집 혹은 즐겨 가는 맛집을 한번에 모아볼 수 있습니다.
### 메뉴
메뉴(Menu)는 사용자가 리뷰를 작성할 때나 리뷰를 검색할 때 더욱 편리하게 접근할 수 있도록 DB화하여 제공합니다. 이때 더 많은 데이터를 구축하기 위해 메뉴가 없을 경우 사용자로부터 메뉴를 직접 입력받아 임시 메뉴에 저장되게 되며(TempMenu) 관리자의 승인 후 정식 메뉴로 등록되게 됩니다.
### 리뷰
리뷰(Review)는 학교인증이 완료된 사용자만 작성할 수 있으며 리뷰 좋아요 개수를 통해 오늘의 핫 리뷰로 선정되게 됩니다. 맛집 리뷰 서비스인 만큼, 맛집 리뷰의 형식에 맞도록 사진 중심으로 게시글이 노출됩니다. 또한 일반 대학교 커뮤니티의 맛집 게시판과는 다르게 리뷰 작성 양식이 제공되기 때문에 양식 파괴로 인한 리뷰 열람의 불편함을 덜어줍니다.


## 🍊 기술 스택

    
[![React](https://img.shields.io/badge/React-61DAFB?style=round-square&logo=React&logoColor=black)](https://ko.reactjs.org/) [![Spring](https://img.shields.io/badge/Spring-6DB33F?style=round-square&logo=Spring&logoColor=white)](https://spring.io/) <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/></a> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/></a> <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=Amazon%20AWS&logoColor=white"/></a> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat-square&logo=AmazonS3&logoColor=white"/></a> <img src="https://img.shields.io/badge/GitHub -181717?style=flat-square&logo=GitHub&logoColor=white"/></a> 

<img src="https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/돼지감자-기술스택구조-001 (1).png"/>

## 🍊 라이브러리
1. lombok
2. spring web
3. spring data jpa
4. oauth2
5. spring boot test
6. spring session jdbc
7. spring security test

## 🍊 프로젝트 구조

### 설명
1. main/java/[프로젝트명]/config ▶️ Config
2. main/java/[프로젝트명]/domain ▶️ Entity, Repository
3. main/java/[프로젝트명]/service ▶️ Service
4. main/java/[프로젝트명]/web ▶️ Dto, Controller
5. main/java/[프로젝트명]/Application.java
6. main/resources/application.properties
7. main/resources/static/images ▶️ 리뷰에 들어가는 이미지
8. main/resources/static/uploads ▶️ 프로필 사진

### 폴더 
<pre>
<code>
└── 🗂 main
    ├── 🗂 java
    │   └── 🗂 com
    │       └── 🗂 matzipuniv
    │           └── 🗂 sinchon
    │               ├── 📑 Application.java
    │               ├── 🗂 config
    │               │   ├── 📑 CustomOAuth2UserService.java
    │               │   ├── 📑 JpaConfig.java
    │               │   ├── 📑 LoginUser.java
    │               │   ├── 📑 LoginUserArgumentResolver.java
    │               │   ├── 📑 OAuthAttributes.java
    │               │   ├── 📑 Role.java
    │               │   ├── 📑 SecurityConfig.java
    │               │   ├── 📑 SessionUser.java
    │               │   └── 📑 WebConfig.java
    │               ├── 🗂 domain
    │               │   ├── 📑 Addition.java
    │               │   ├── 📑 AdditionRepository.java
    │               │   ├── 📑 BaseTimeEntity.java
    │               │   ├── 📑 Folder.java
    │               │   ├── 📑 FolderRepository.java
    │               │   ├── 📑 Image.java
    │               │   ├── 📑 ImageRepository.java
    │               │   ├── 📑 Menu.java
    │               │   ├── 📑 MenuRepository.java
    │               │   ├── 📑 Pin.java
    │               │   ├── 📑 PinRepository.java
    │               │   ├── 📑 Report.java
    │               │   ├── 📑 ReportRepository.java
    │               │   ├── 📑 Restaurant.java
    │               │   ├── 📑 RestaurantRepository.java
    │               │   ├── 📑 Review.java
    │               │   ├── 📑 ReviewRepository.java
    │               │   ├── 📑 TempMenu.java
    │               │   ├── 📑 TempMenuRepository.java
    │               │   ├── 📑 User.java
    │               │   └── 📑 UserRepository.java
    │               ├── 🗂 service
    │               │   ├── 📑 FolderService.java
    │               │   ├── 📑 ImageService.java
    │               │   ├── 📑 MenuService.java
    │               │   ├── 📑 PinService.java
    │               │   ├── 📑 ReportService.java
    │               │   ├── 📑 RestaurantService.java
    │               │   ├── 📑 ReviewService.java
    │               │   ├── 📑 S3UploaderProfile.java
    │               │   ├── 📑 S3UploaderReview.java
    │               │   ├── 📑 TempMenuService.java
    │               │   └── 📑 UserService.java
    │               └── 🗂 web
    │                   ├── 📑 FolderApiController.java
    │                   ├── 📑 ImageApiController.java
    │                   ├── 📑 MenuController.java
    │                   ├── 📑 PinApiController.java
    │                   ├── 📑 ReportApiController.java
    │                   ├── 📑 RestaurantApiController.java
    │                   ├── 📑 ReviewApiController.java
    │                   ├── 📑 TempMenuController.java
    │                   ├── 📑 UserApiController.java
    │                   └── 🗂 dto
    │                       ├── 📑 AdditionResponseDto.java
    │                       ├── 📑 FolderResponseDto.java
    │                       ├── 📑 FolderSaveRequestDto.java
    │                       ├── 📑 ImageResponseDto.java
    │                       ├── 📑 MenuDto.java
    │                       ├── 📑 PinResponseDto.java
    │                       ├── 📑 ReportDto.java
    │                       ├── 📑 RestaurantListResponseDto.java
    │                       ├── 📑 RestaurantResponseDto.java
    │                       ├── 📑 ReviewListResponseDto.java
    │                       ├── 📑 ReviewRequestDto.java
    │                       ├── 📑 ReviewResponseDto.java
    │                       ├── 📑 TempMenuDto.java
    │                       ├── 📑 UserResponseDto.java
    │                       └── 📑 UserUpdateRequestDto.java
    └── 🗂 resources
        ├── 📑 application.properties
        └── 🗂 static
            ├── 🗂 images
            └── 🗂 uploads
</code>
</pre>


## 🍊 데이터베이스 설계도(E-R diagram)
<img src = "https://github.com/MINJU-KIMmm/GitHubTest/blob/main/image/porkProfile/matzip-univ-db.png"/>

## 🍊 API 명세서
### [🔗 Link](https://www.notion.so/API-bd2954deae834891889daaf5085d8853)


