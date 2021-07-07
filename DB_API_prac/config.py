db = {
    'user' : 'root',
    'password' : 'se973571sb!sql',
    'host' : 'localhost',
    'port' : 3306,
    'database' : 'todo_db'
}

DB_URL = f"mysql+mysqlconnector://{db['user']}:{db['password']}@{db['host']}:{db['port']}/{db['database']}?charset=utf8"