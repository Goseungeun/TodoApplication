# TodoApplication
RestFul API와 DB, Android Application을 연동하여 Todo list 관리하는 프로젝트\
RestFul API 구현과 DB 연동 실습 (host컴퓨터에서 동작하게 DB 서버 관리는 나중에 학습할 예정)
## Introduction
1. 어플리케이션 접속과 동시에 DB에 있는 모든 Todolist 리사이클러뷰로 화면에 출력
2. 상단에 Todo content 입력 후 저장 버튼 클릭 시, DB에 저장, 리사이클러뷰에 추가해서 화면에 출력
3. todo item 에서 연필 클릭 시 todo content 수정 가능
4. todo item 에서 휴지통 버튼 클릭 시 todo content 삭제

## Development Environment
- Android Studio
- python
- flask
- MySql

## Database
Mysql 사용해서 미리 구현하기 (hostcomputer)\
column은 todo_content 하나만 필요\
.env 파일에 들어가 생성한 Database 에 맞게 수정 필요

## API
API의 사용법은 다음 링크에서 확인할 수 있습니다.\
API는 python으로 작성되었으며, flask를 이용하였습니다.\
[API 문서](https://solar-spaceship-972254.postman.co/workspace/My-Workspace~1aa45cbd-07f8-4ff2-8a06-0e0a54c5357a/documentation/16710488-579394b1-bc49-423c-95e2-c649bee325ae)
