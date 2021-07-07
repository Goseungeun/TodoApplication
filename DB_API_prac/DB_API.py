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

# todos = {}
# count = 1

def get_todolist():
    curs = db.cursor()
    curs.execute("SELECT todo_contents FROM todo;")
    todos = curs.fetchall()

    if len(todos) == 0:
        return "todo list does not exists"
    else:
        return todos

def insert_todo(todo):
        db.execute("INSERT INTO todo(contents) VALUES ('"+todo+"');")

def get_todoitem(todo_id):
    curs = db.cursor()
    sql = 'SELECT contents FROM todo WHERE id = :todo_id'
    curs.execute(sql)
    todo = curs.fetchone()

    return todo


#메인 todo list 추가, 목록 확인
@api.route('/todolist',methods=['GET','POST'])
class TodoList(Resource):
    def get(self):
        return get_todolist()      #DB 정보 읽어와서 return

    def post(self):
        new_todo = request.json.get('todo')  # 값을 읽었음! (str)
        print(type(new_todo))
        insert_todo(new_todo)
        # new_todo_id = insert_todo(new_todo)
        # new_todo = get_todoitem(new_todo_id)


        return {
            'todo' : new_todo
        }

# #todolist item에 있는 수정버튼 클릭 시 목록 수정
# @api.route('/todolist/<int:todo_id>/modify')
# class TodoModify(Resource):
#     def put(self, todo_id):
#         todos[todo_id] = request.json.get('data')
#         return{
#             #DB UPDATE
#             'todo_id' : todo_id,
#             'data' : todos[todo_id]
#         }

# #todolist item에 있는 삭제버튼 클릭 시 목록 삭제
# @api.route('/todolist/<int:todo_id>/delete')
# class TodoDelete(Resource):
#     def delete(self, todo_id):
#         del todos[todo_id]
#         return{
#             "delete" : "success"
#         }

if __name__ == "__main__":
    app.run()
