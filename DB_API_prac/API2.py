from flask import Flask,request,jsonify,current_app
from flask.json import JSONEncoder
from sqlalchemy import create_engine, Text

class CustomJSONEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, set):
            return list(obj)