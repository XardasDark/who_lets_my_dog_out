import sqlalchemy as db
import datetime
from sqlalchemy import *
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship, backref

Base = declarative_base()

class User(Base):
    __tablename__ = 'User'

    username = Column(String(length=30), primary_key=True)
    firstname = Column(String(length=30))
    lastname = Column(String(length=30))
    birthday = Column(String(length=12))
    picture = Column(String(length=3000))
    email = Column(String(length=60))
    dogWalker = Column(Boolean)
    password = Column(String(length=30))
    longitude = Column(NUMERIC(9, 6))
    latitude = Column(NUMERIC(8, 6))

class Dog(Base):
    __tablename__ = 'Dog'

    id = Column(Integer, primary_key=True)
    name = Column(String(length=30))
    breed = Column(String(length=30))
    age = Column(Integer)
    picture = Column(String(length=3000))
    owner = Column(String(length=30), ForeignKey("User.username"))

    user = relationship("User")

class Chat(Base):
    __tablename__ = 'Chat'
    id = Column(Integer, primary_key=True)
    dateTime = Column(DateTime(timezone=True), server_default=func.now())
    message = Column(String(length=3000))

    sending = Column(String(length=30), ForeignKey("User.username"))
    user_sending = relationship("User", backref=backref("sending",uselist=False), foreign_keys=[sending])
    reciving = Column(String(length=30), ForeignKey("User.username"))
    user_reciving = relationship("User", backref=backref("reciving", uselist=False), foreign_keys=[reciving])


def init_db():
    engine = db.create_engine("mariadb+mariadbconnector://doguser:dogpassword@localhost:3306/wholetsmydogout")
    Base.metadata.drop_all(engine)
    Base.metadata.create_all(engine)

    create_sample(engine)

    print("Database created")

def create_sample(engine):
    Session = db.orm.sessionmaker()
    Session.configure(bind=engine)
    session = Session()

    #add sample users
    users =[]
    users.append(User(username="ncage",firstname ="Nicolas", lastname = "Cage", birthday = "1964-01-07",picture="https://upload.wikimedia.org/wikipedia/commons/f/f3/Nicolas_Cage_-_66%C3%A8me_Festival_de_Venise_%28Mostra%29.jpg"
                    ,email ="nic.cage@best-in-the-world.com",dogWalker = True, password="ncagePW",longitude=8.905562, latitude=52.295314))
    users.append(User(username="kspacey", firstname="Kevin", lastname="Spacey", birthday="1959-07-26",
                      picture="https://upload.wikimedia.org/wikipedia/commons/1/1c/Kevin_Spacey%2C_May_2013.jpg"
                      , email="k.spacey@best-in-the-world.com", dogWalker=True, password="kspaceyPW", longitude=8.912451, latitude=52.282848))
    users.append(User(username="tkohlmeier", firstname="Timo", lastname="Kohlmeier", birthday="1998-08-11",
                      picture="https://i.imgur.com/cqKSBdW.jpg"
                      , email="t.kohlmeier@email.com", dogWalker=True, password="tkohlmeierPW", longitude=8.988248, latitude=52.244973))
    for user in users:
        session.add(user)

    #add sample dogs
    dogs =[]
    dogs.append(Dog(name="Tom", breed="Pudel", age=5, picture="https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.santevet.de%2Fuploads%2Fimages%2Fde_DE%2Frassen%2Fpudel.jpeg&imgrefurl=https%3A%2F%2Fwww.santevet.de%2Frasse%2Fpudel&tbnid=XurUp7d2TERwfM&vet=12ahUKEwjroIaZtYn4AhXB0KQKHbMNAwMQMygDegUIARDiAQ..i&docid=kF1KdzVqisBkgM&w=640&h=427&q=pudel%20pic&client=firefox-b-d&ved=2ahUKEwjroIaZtYn4AhXB0KQKHbMNAwMQMygDegUIARDiAQ",
                    owner="ncage"))
    dogs.append(Dog(name="Jerry", breed="Pudel", age=5, picture="https://www.google.com/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fc%2Fc7%2FMiniature_Poodle_stacked.jpg&imgrefurl=https%3A%2F%2Fde.wikipedia.org%2Fwiki%2FPudel&tbnid=YllRJnn8NyR8pM&vet=12ahUKEwjzpo_-tYn4AhUCtKQKHZxjAzMQMygEegUIARDkAQ..i&docid=RdO5Ix9YPtbcfM&w=2639&h=2515&q=pudel%20pic&client=firefox-b-d&ved=2ahUKEwjzpo_-tYn4AhUCtKQKHZxjAzMQMygEegUIARDkAQ",
                    owner="ncage"))
    dogs.append(Dog(name="Mini", breed="Terrier Mix", age=10, picture="https://celebritypets.net/wp-content/uploads/2021/11/kevin-spacey-dog-mini-195x195.jpg",
                    owner="kspacey"))
    for dog in dogs:
        session.add(dog)

    #add sample chats
    chats=[]
    chats.append(Chat(dateTime=datetime.datetime(2022, 6, 1, 12, 10, 5), message="Hi Kevin",sending="ncage",reciving="kspacey"))
    chats.append(Chat(dateTime=datetime.datetime(2022, 6, 1, 12, 15, 10),message="Hi Nicolas", sending="kspacey", reciving="ncage"))
    chats.append(Chat(dateTime=datetime.datetime(2022, 6, 1, 12, 16, 17),message="Kannst du Sonntag mit meinem Hund rausgehen ?", sending="ncage", reciving="kspacey"))
    chats.append(Chat(dateTime=datetime.datetime(2022, 6, 1, 12, 19, 5),message="Gerne gehst du dafür Mittwoch mit meinem ?", sending="kspacey", reciving="ncage"))
    chats.append(Chat(dateTime=datetime.datetime(2022, 6, 1, 12, 21, 30),message="Ich bin da nämlich leider auf der arbeit", sending="kspacey", reciving="ncage"))
    chats.append(Chat(dateTime=datetime.datetime(2022, 6, 3, 12, 21, 30), message="Hi Nik ich bräuchte mal deine Hilfe",sending="tkohlmeier", reciving="ncage"))
    for chat in chats:
        session.add(chat)
    session.commit()


