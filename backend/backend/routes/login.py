from flask import Blueprint,jsonify,request
import sqlalchemy as db
from sqlalchemy import *

from backend.database.db import User

login_bp = Blueprint("login",__name__, url_prefix="/login")

@login_bp.route("", methods=["Post"])
def login_post():

    username = request.headers.get('username', default=None)
    password = request.headers.get('password', default=None)

    if None in [username, password]:
        return jsonify({'error': 'Missing parameter'}), 400
    if "" in [username, password]:
        return jsonify({'error': 'empty parameter'}), 400


    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()

    json_data = []
    results = session.query(User).filter(User.username == username,User.password == password)
    for result in results:
        json_data.append({'username':result.username})
    if json_data == []:
        return jsonify({'error':'wrong username or password'}),400
    return jsonify({'status': 'login correct'}), 200