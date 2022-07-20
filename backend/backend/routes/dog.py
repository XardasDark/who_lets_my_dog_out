from flask import Blueprint,jsonify,request
import sqlalchemy as db
from sqlalchemy import *

from backend.database.db import Dog

dog_bp = Blueprint("dog",__name__, url_prefix="/dog")


@dog_bp.route("",methods=["GET"])
def Dog_get():
    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()

    id_dog = request.headers.get('id',default=None)

    json_data = []
    results = session.query(Dog)

    if id_dog:
        results = results.filter(Dog.id.contains(id_dog))
    else:
        return jsonify({'error': 'missing Argument'}), 400

    for result in results:
        json_data.append({
            'name': result.name,
            'breed': result.breed,
            'age': result.age,
            'picture': result.picture
        })
    return jsonify(json_data)



@dog_bp.route("", methods=["POST"])
def dog_post():
    username = request.headers.get('username',default=None)
    name = request.headers.get('name',default=None)
    age = request.headers.get('age',default=None)
    picture = request.headers.get('picture',default="https://i.imgur.com/rQRn4Hj.jpg")
    breed = request.headers.get('breed',default=None)

    if None in[username,name,age,breed]:
        return jsonify({'error': 'Missing parameter'}), 400
    if "" in[username,name,breed]:
        return  jsonify({'error':'empty parameter'}),400


    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()

    session.add(Dog(name=name, breed=breed, age=age, picture=picture,
                    owner=username))
    session.commit()

    return jsonify({'status': 'dog created'}), 200

@dog_bp.route("",methods=["PUT"])
def dog_put():
    id = request.headers.get('id', default=None)

    if None in[id]:
        return jsonify({'error': 'Missing parameter'}), 400


    engine = create_engine("mariadb+mariadbconnector://root:root@localhost:3306/wholetsmydogout")
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()

    json_data = []
    results = session.query(Dog)

    results = results.filter(Dog.id.contains(id))

    for result in results:
        json_data.append({
            'name': result.name,
            'breed': result.breed,
            'age': result.age,
            'picture': result.picture
        })
    if json_data == []:
        return jsonify({'error': 'cant find dog with ID'}), 400

    name = request.headers.get('name', default=json_data[0]['name'])
    age = request.headers.get('age', default=json_data[0]['age'])
    picture = request.headers.get('picture',default=json_data[0]['picture'])
    breed = request.headers.get('breed', default=json_data[0]['breed'])

    session.query(Dog).filter(Dog.id == id).update({Dog.name: name, Dog.age: age, Dog.picture: picture, Dog.breed: breed})

    session.commit()
    return jsonify({'status': 'dog changed'}), 200
