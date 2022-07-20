from flask import Blueprint,jsonify,request
from math import radians, cos, sin, asin, sqrt
import sqlalchemy as db
from sqlalchemy import *

from backend.database.db import User, Dog, Chat

chat_bp = Blueprint("chat",__name__, url_prefix="/chat")

@chat_bp.route("",methods=["POST"])
def chat_post():
    username = request.headers.get('username', default=None)
    usernameTarget = request.headers.get('usernameTarget', default=None)
    message = request.headers.get('message', default=None)

    if None in [username, usernameTarget, message]:
        return jsonify({'error': 'Missing parameter'}), 400
    if "" in [username, usernameTarget, message]:
        return jsonify({'error': 'empty parameter'}), 400

    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()

    session.add(Chat(sending=username, reciving=usernameTarget,message=message))
    session.commit()
    return jsonify({'status': 'message send'}), 200

@chat_bp.route("",methods=["GET"])
def chat_get():
    username = request.headers.get('username', default=None)

    if None in [username]:
        return jsonify({'error': 'Missing parameter'}), 400
    if "" in [username]:
        return jsonify({'error': 'empty parameter'}), 400

    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()
    json_data = []
    results = session.query(Chat).filter(or_(Chat.sending == username, Chat.reciving == username)).order_by(desc(Chat.dateTime))

    listName= []
    for result in results:
        picture = ""
        user = result.sending
        if user == username:
            user = result.reciving
            results2 = session.query(User).filter(User.username==result.reciving)
            for result2 in results2:
                picture = result2.picture
        else:
            results2 = session.query(User).filter(User.username==result.sending)
            for result2 in results2:
                picture = result2.picture

        if user not in listName:
            json_data.append({
                'username': user,
                'dateTime': result.dateTime,
                'message': result.message,
                'picture': picture
            })
            listName.append(user)

    return jsonify(json_data)

@chat_bp.route("/<username>",methods=["GET"])
def chat_get_user(username):
    currentUser = request.headers.get('currentUser', default=None)

    if None in [currentUser]:
        return jsonify({'error': 'Missing parameter'}), 400
    if "" in [currentUser]:
        return jsonify({'error': 'empty parameter'}), 400

    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()
    json_data = []
    results = session.query(Chat).filter(or_(and_(Chat.sending == currentUser,Chat.reciving == username),and_(Chat.sending == username,Chat.reciving == currentUser)))
    for result in results:
        own = False
        if result.sending == currentUser:
            own = True
        json_data.append({
            'own': own,
            'message': result.message,
            'date': result.dateTime

        })

    return jsonify(json_data)
