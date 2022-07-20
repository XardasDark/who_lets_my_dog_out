from flask import Blueprint,jsonify,request
from math import radians, cos, sin, asin, sqrt
import sqlalchemy as db
from sqlalchemy import *

from backend.database.db import User, Dog

user_bp = Blueprint("user",__name__, url_prefix="/user")

@user_bp.route("",methods=["GET"])
def user_get():
    username = request.headers.get('username', default=None)
    range = request.headers.get('range', default=None)

    if None in [username, range]:
        return jsonify({'error': 'Missing parameter'}), 400
    if "" in [username, range]:
        return jsonify({'error': 'empty parameter'}), 400

    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()
    json_data = []
    latitude = 0
    longitude = 0
    results = session.query(User).filter(User.username == username)
    for result in results:
        latitude = result.latitude
        longitude = result.longitude

    results = session.query(User).filter(User.username != username, User.dogWalker == True)
    for result in results:
        dist = distance(latitude, result.latitude, longitude, result.longitude)
        if dist <= float(range):
            json_data.append({
                'username': result.username,
                'picture': result.picture,
                'distance': round(dist, 2)
            })

    return jsonify(json_data)

@user_bp.route("/<username>", methods=["GET"])
def user_get_id(username):
    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()

    dog_data = []
    results = session.query(Dog).filter(Dog.owner == username)
    for result in results:
        dog_data.append({
            'id':result.id,
            'name':result.name
        })

    json_data = []
    results = session.query(User).filter(User.username == username)
    for result in results:
        json_data.append({
            'firstname': result.firstname,
            'lastname': result.lastname,
            'birthdate': result.birthday,
            'email': result.email,
            'picture': result.picture,
            'dogWalker': result.dogWalker,
            'latitude': float(result.latitude),
            'longitude': float(result.longitude),
            'dogs':dog_data
        })
    if json_data == []:
        return jsonify({'error': 'cant find user'}), 400
    return jsonify(json_data)

@user_bp.route("",methods=["POST"])
def user_post():
    username = request.headers.get('username', default=None)
    firstname = request.headers.get('firstname', default=None)
    lastname = request.headers.get('lastname', default=None)
    email = request.headers.get('email', default=None)
    password = request.headers.get('password', default=None)
    birthday = request.headers.get('birthday', default=None)
    picture = request.headers.get('picture', default="https://i.imgur.com/cqKSBdW.jpg")
    dogWalker = request.headers.get('dogWalker', default=None)
    latitude = request.headers.get('latitude', default=None)
    longitude = request.headers.get('longitude', default=None)

    if None in [username, firstname, lastname, email, password, birthday, dogWalker, latitude, longitude]:
        return jsonify({'error': 'Missing parameter'}), 400
    if "" in [username, firstname, lastname, email, password, birthday]:
        return jsonify({'error': 'empty parameter'}), 400

    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()

    session.add(User(username=username, firstname=firstname, lastname=lastname, birthday=birthday,
                      picture=picture, email=email, dogWalker=bool(dogWalker), password=password, longitude=longitude, latitude=latitude))
    session.commit()
    return jsonify({'status': 'user created'}), 200

@user_bp.route("",methods=["PUT"])
def user_put():
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
    results = session.query(User).filter(User.username == username)
    for result in results:
        json_data.append({
            'firstname': result.firstname,
            'lastname': result.lastname,
            'birthdate': result.birthday,
            'email': result.email,
            'picture': result.picture,
            'dogWalker': result.dogWalker,
            'latitude': float(result.latitude),
            'longitude': float(result.longitude),
        })
    if json_data == []:
        return jsonify({'error': 'cant find user'}), 400

    firstname = request.headers.get('firstname', default=json_data[0]['firstname'])
    lastname = request.headers.get('lastname', default=json_data[0]['lastname'])
    email = request.headers.get('email', default=json_data[0]['email'])
    birthday = request.headers.get('birthday', default=json_data[0]['birthdate'])
    picture = request.headers.get('picture',default=json_data[0]['picture'])
    dogWalker = request.headers.get('dogWalker', default=json_data[0]['dogWalker'])
    latitude = request.headers.get('latitude', default=json_data[0]['latitude'])
    longitude = request.headers.get('longitude', default=json_data[0]['longitude'])

    session.query(User).filter(User.username == username).update({User.firstname: firstname, User.lastname: lastname,
                                                                  User.email: email, User.birthday: birthday,
                                                                  User.picture: picture, User.dogWalker: bool(dogWalker),
                                                                  User.latitude: latitude, User.longitude: longitude})
    session.commit()

    return jsonify({'status': 'user changed'}), 200


def distance(lat1, lat2, lon1, lon2):
    # The math module contains a function named
    # radians which converts from degrees to radians.
    lon1 = radians(lon1)
    lon2 = radians(lon2)
    lat1 = radians(lat1)
    lat2 = radians(lat2)

    # Haversine formula
    dlon = lon2 - lon1
    dlat = lat2 - lat1
    a = sin(dlat / 2) ** 2 + cos(lat1) * cos(lat2) * sin(dlon / 2) ** 2

    c = 2 * asin(sqrt(a))

    # Radius of earth in kilometers. Use 3956 for miles
    r = 6371

    # calculate the result
    return (c * r)
