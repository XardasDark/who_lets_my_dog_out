# Einrichtung der Entwicklungsumgebung:

- MariaDB installieren

-- zur besseren Kompatibilität bitte Nutzer="root" und Passwort="root" erstellen

-- Datenbank mit namen wholetsmydogout anlegen

- Projekt clonen

- Projektordner in der Konsole öffnen

- Falls nicht vorhanden virtualenv installieren

- Virtual Environment anlegen

    - Linux:
        $ python3 -m venv venv

    - Windows:
        $ python -m venv venv
- Virtual Enviroment starten
    - Linux:
        $ source venv/bin/activate
    - Windows:
        $ .\\venv\\Scripts\\activate.bat
- Nun sollte vor dem Komandozeilenpromt (venv) erscheinen

- Installieren der notwendigen Pakete
    $ pip3 install Flask

    $ pip3 install SQLAlchemy

    $ pip3 install mariadb SQLAlchemy

# Starten der Anwendung:

- In der Konsole ins Hauptverzeichnis des Projekts wechseln

- Virtual Enviroment starten

    - Linux:
        $ source venv/bin/activate
    - Windows:
        $ .\\venv\\Scripts\\activate.bat
- Setzen von 2 Enviroment Variablen
    - Linux:
        $ export FLASK_APP=backend

        $ export FLASK_ENV=development
    - Windows:
        $ set FLASK_APP=backend

        $ set FLASK_ENV=development
- Nun kann die Anwendung wie folgt genutzt werden
    $ flask run
