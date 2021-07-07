import os
import pymysql
from flask import Flask,jsonify,request, current_app
from flask_restx import Api, Resource
from dotenv import load_dotenv

'''
id는 데이터베이스에서 자동으로 하나씩 증가하게 설정
지금 post에서 넣어주는 id는 데이터베이스 연결하면서 제거하기
Modify, delete에 있는 id -> 데이터베이스 아이디 넘겨주는 방법 생각해보기
'''

app = Flask(__name__)
api = Api(app)

load_dotenv()
db = pymysql.connect(host=os.getenv('MYSQL_HOST'),
                    port=int(os.getenv('MYSQL_PORT')),
                    user=os.getenv('MYSQL_USER'),
                    password=os.getenv('MYSQL_PASSWORD'),
                    db=os.getenv('MYSQL_DATABASE'),
                    charset=os.getenv('MYSQL_CHARSET'),
                    cursorclass=pymysql.cursors.DictCursor)

todos = {}

#DB에 존재하는 모든 todolist 불러오기
def get_todolist():
    curs = db.cursor()
    curs.execute("SELECT * FROM todo;")
    todos = curs.fetchall()

    if len(todos) == 0:
        return 'null'
    else:
        return todos

#DB에 새로운 todo 넣기
def insert_todo(todo):
        curs=db.cursor()
        curs.execute("INSERT INTO todo(todo_contents) VALUES ('"+todo+"');")
        db.commit()         #DB에 바로 저장하기 위함

#DB에 존재하는 데이터 값 변경
def update_todo(before_cont,new_cont):
    curs=db.cursor()
    curs.execute("UPDATE todo SET todo_contents = '"+new_cont+"' WHERE todo_contents ='"+before_cont+"'")
    db.commit()

#DB에 존재하는 todo삭제
def delete_todo(todo_cont):
    curs=db.cursor()
    curs.execute("DELETE FROM todo WHERE todo_contents = '"+todo_cont+"'")
    db.commit()


#메인 todo list 추가, 목록 확인 (DB 저장된 내용 확인 O)
@api.route('/todolist',methods=['GET','POST'])
class TodoList(Resource):
    def get(self):
        return get_todolist()      #DB 정보 읽어와서 return

    def post(self):
        new_todo = request.json.get('todo')  # 값을 읽었음! (str)
        insert_todo(new_todo)       #DB에 읽어온 데이터 값 넣기

        return  get_todolist()

# #todolist item에 있는 수정버튼 클릭 시 목록 수정
@api.route('/todolist/<string:contents>/modify')
class TodoModify(Resource):
    def put(self, contents):
        new_cont = request.json.get('todo')
        update_todo(contents,new_cont)

        return get_todolist()

# #todolist item에 있는 삭제버튼 클릭 시 목록 삭제
@api.route('/todolist/<string:contents>/delete')
class TodoDelete(Resource):
    def delete(self, contents):
        delete_todo(contents)
        return{
            "delete" : "success"
        }

if __name__ == "__main__":
    app.run(host ='0.0.0.0',port=8000)
