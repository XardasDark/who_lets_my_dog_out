from flask import Flask

from backend.database.db import init_db
from backend.routes.dog import dog_bp
from backend.routes.login import login_bp
from backend.routes.user import user_bp
from backend.routes.chat import chat_bp


def create_app():
    app = Flask(__name__)
    #init_db()
    app.register_blueprint(login_bp)
    app.register_blueprint(user_bp)
    app.register_blueprint(dog_bp)
    app.register_blueprint(chat_bp)
    return app
