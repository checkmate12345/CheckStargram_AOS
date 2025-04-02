# 📱 CheckStagram✔️

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green" />
  <img src="https://img.shields.io/badge/Language-Kotlin-blue" />
  <img src="https://img.shields.io/badge/AI-BERT | YOLOv8 | ResNet18-purple" />
</p>

## 📌 프로젝트 개요

- **프로젝트명** : CheckStagram✔️  
- **팀명** : ♟️Checkmate  
- **진행 기간** : 2025.03.04 ~ 2025.03.24  
- **핵심 키워드** : 콘텐츠 사전검열, 부적절 콘텐츠 필터링, 인플루언서 리스크 관리

## 🧠 기획 배경

- SNS는 셀럽의 퍼스널 브랜딩에 필수적인 수단이 되었지만, 부적절한 콘텐츠는 치명적 리스크를 초래
- **선정성, 음주, 흡연, 혐오 발언 등은 논란을 넘어서 이미지 실추, 계약 해지, 소송으로 이어짐**
- 대부분 수동 검수 시스템에 의존하고 있어 **실시간 대응 및 사전 필터링의 필요성** 증가
- **자동 콘텐츠 점검 시스템의 필요성 제기**

---

## 💡 프로젝트 소개

> **Check First, Before You Upload Your Instagram**

CheckStagram✔️은 **셀럽 및 소속사를 위한 AI 기반 SNS 콘텐츠 사전 검토 솔루션**입니다.

- 인스타그램과 유사한 UI 제공
- 게시물 업로드 전 **AI 기반 이미지/텍스트 분석**
- 위험 요소 감지 시 **사전 경고 및 삭제 안내**

---

## 📲 핵심 기능

1. **로그인 및 피드 탐색**
2. **다중 이미지·영상 게시물 업로드 기능**
3. **검사 항목(담배, 주류, 브랜드 등) 설정**
4. **YOLOv8/ResNet 기반 객체 탐지**
5. **BERT 기반 자연어 필터링**
6. **콘텐츠 별 위험요소 요약 및 강조 표시**

<p align="center">
  <img width="612" alt="스크린샷 2025-04-01 오전 10 19 02" src="https://github.com/user-attachments/assets/a1e9524f-f70b-4c07-8eb2-62c7c04c56be" width="300"/>

  <img width="612" alt="스크린샷 2025-04-01 오전 10 19 07" src="https://github.com/user-attachments/assets/c7d32a51-225c-45ba-88ed-4ac62ea2618c" width="300"/>

</p>

---

## 🧪 AI 모델링

### 🎯 자연어 처리 (NLP)
- **모델** : BERT (사전 학습된 한국어 모델 활용)
- **기능** : 여성 비하, 폭력 조장, 정치·사회적 민감 발언 탐지
- **데이터** : 커뮤니티, 뉴스 댓글 기반 수집 및 전처리

### 🎯 컴퓨터 비전 (CV)
- **YOLOv8** : 주류/담배/브랜드 객체 탐지
- **ResNet18** : 미세 객체 분류 (브랜드 가방 등)
- **데이터 수집** : 직접 수집 및 어노테이션 수행

---

## 🧰 사용 기술

| 영역 | 기술 스택 |
|------|-----------|
| Android | Kotlin, Android Studio, MVVM, Clean Architecture, Jetpack, Lottie |
| Backend | Node.js (Express), MongoDB, AWS EC2 & S3 |
| AI 모델 서버 | Python (FastAPI), YOLOv8, ResNet18, BERT |
| 배포 | AWS (EC2, S3), Git, GitHub |

---

## 🧑‍🎤 사용자 페르소나 & 시연 예시

- **상황 1**: 게시물에 맥주 이미지 포함 → "주류 노출 감지" 표시 후 경고
- **상황 2**: “여성은 감정적이라 중요한 결정을 못 내려” → BERT 모델로 혐오 표현 감지
- **상황 3**: MiuMiu 계약된 셀럽이 Dior 가방 착용 → 브랜드 노출 탐지 후 강조 표시

---

## 🗂️ 디렉토리 구조 (Android App)

CheckStagram✔️의 안드로이드 프로젝트는 **MVVM + Clean Architecture** 기반으로 구성되어 있으며, 역할별 책임 분리가 잘 되어 있습니다.

```
app/
├── sampledata/                 # 샘플 JSON 데이터
├── manifests/                  # AndroidManifest.xml
├── kotlin+java/
│   └── com.checkmate.checkstagram/
│       ├── data/               # Repository, RemoteDataSource 등 데이터 계층
│       ├── di/                 # Hilt 의존성 주입 모듈 정의
│       ├── domain/             # UseCase, 비즈니스 로직 처리 계층
│       ├── presentation/       # UI 계층
│       │   ├── adapter/        # RecyclerView Adapter 등
│       │   ├── base/           # BaseActivity, BaseFragment 등
│       │   ├── model/          # ViewModel과 UI 사이의 데이터 모델
│       │   ├── ui/             # 화면 UI 관련 (Activity, Fragment 등)
│       │   └── viewmodel/      # 각 화면에 대응되는 ViewModel
│       ├── util/               # 공용 유틸리티 함수 모음
│       └── HiltApplication.kt  # Hilt 초기화 설정을 위한 Application 클래스
```
---

## 👨‍👩‍👧‍👦 팀원 소개

| 이름 | 역할 | 주요 담당 업무 |
|------|------|----------------|
| **심현지** | 팀장 / 디자인 | 전체 프로젝트 디자인 및 UI/UX 설계 |
| **김정섭** | 데이터 수집 및 전처리 | 텍스트 및 이미지 기반 AI 학습 데이터 수집 및 전처리 |
| **이종민** | Android / 자연어처리 | Android 앱 개발, BERT 기반 NLP 모델 설계 및 적용 |
| **이초인** | 백엔드 / 컴퓨터 비전 | Express 기반 서버 및 YOLOv8, ResNet18 모델 개발 |

---


