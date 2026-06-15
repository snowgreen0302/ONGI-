# ONGI - AI 기반 기부 단체 추천 앱

## 1. 프로젝트 소개

ONGI는 사용자의 관심 분야와 지역을 기반으로 적절한 기부 단체를 추천해주는 Android 애플리케이션입니다.

공공데이터 API를 활용하여 전국의 기부 단체 정보를 제공하며, AI 상담 기능을 통해 사용자가 자연어로 원하는 기부 방향을 입력하면 적합한 단체를 추천받을 수 있습니다.

---

## 2. 개발 환경

- IDE : Android Studio
- Language : Java
- Target SDK : Android SDK 34
- UI : XML Layout
- AI : Gemini API
- Storage : SharedPreferences
- API : 공공데이터포털 API

---

## 3. 주요 기능

### ① 기부 단체 모아보기

- 공공데이터 API를 통해 기부 단체 정보를 수집
- RecyclerView를 이용한 Grid 형태의 카드 UI 제공
- 단체명, 카테고리, 지역 검색 기능 제공
- 카테고리 및 지역 필터링 기능 제공

---

### ② 단체 상세 페이지

- 단체 상세 정보 조회
- 홈페이지 바로가기
- 전화 걸기
- 주소 복사

---

### ③ 최근 본 단체

- SharedPreferences를 이용하여 최근 조회한 단체 저장
- Home 화면 및 MY 페이지에서 최근 본 단체 확인 가능

---

### ④ AI 상담 기능

사용자가 자연어로 질문하면 AI가 질문을 분석하여 적절한 기부 단체를 추천합니다.

예시)

- "서울에서 교육 기부를 하고 싶어요."
- "환경 보호 관련 단체를 추천해주세요."
- "아동 후원 단체를 알고 싶어요."

AI가 질문에서

- 지역
- 카테고리

를 추출하여 적절한 단체를 추천합니다.

---

## 4. 화면 구성

### Home

- 최근 본 단체 표시
- AI 상담 바로가기 버튼

### Browse

- 전체 기부 단체 목록 조회
- 검색 기능
- 카테고리 및 지역 필터

### Detail

- 단체 상세 정보
- 홈페이지 이동
- 전화 걸기
- 주소 복사

### AI 상담

- 자연어 질문 입력
- AI 응답 출력
- 추천 단체 카드 표시
- 다른 단체 추천 기능

### MY Page

- 최근 본 단체 확인

---

## 5. 프로젝트 구조

```text
com.example.ongi

├── MainActivity
├── DetailActivity
├── SplashActivity

├── HomeFragment
├── BrowseFragment
├── AIFragment
├── MyFragment
├── RankingFragment

├── adapter
│   └── DonationAdapter

├── model
│   └── Donation

├── DonationRepository
└── AIResponse
```

### Layout

```text
res/layout

├── activity_main.xml
├── activity_detail.xml
├── activity_my_page.xml
├── activity_splash.xml

├── fragment_home.xml
├── fragment_browse.xml
├── fragment_ai.xml
├── fragment_my.xml
├── fragment_ranking.xml

└── item_donation.xml
```

### Drawable

```text
res/drawable

├── bg_bottom_nav.xml
├── category_badge.xml
├── home_circle_bg.xml
├── ic_launcher_background.xml
├── ic_launcher_foreground.xml
├── nav_selected_bg.xml
└── logo.png
```

### Menu

```text
res/menu

└── bottom_menu.xml
```

---

## 6. 기대 효과

사용자가 쉽고 편하게 자신에게 맞는 기부 단체를 찾을 수 있으며,

AI 상담 기능을 통해 관심 분야와 지역에 적합한 기부 단체를 추천받아 기부 참여를 활성화할 수 있다.

또한 공공데이터와 AI 기술을 결합하여 기부 문화 활성화에 기여할 수 있다.