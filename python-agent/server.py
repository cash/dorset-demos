from flask import Flask, request
import json
import logging
from logging.handlers import RotatingFileHandler

app = Flask("astronomy")
file_handler = RotatingFileHandler('error.log', 'a', 1 * 1024 * 1024, 5)
file_handler.setFormatter(logging.Formatter('%(asctime)s %(levelname)s: %(message)s'))
app.logger.addHandler(file_handler)
app.logger.setLevel(logging.getLevelName('INFO'))


class AgentRequest(object):
    def __init__(self, text):
        self.text = text


class AgentMessages(object):
    success = 0
    no_response = 100
    bad_request = 101
    invalid_response = 102


class AgentResponse(object):
    def __init__(self, text='', code=AgentMessages.success):
        self.text = text
        self.code = code

    def export(self):
        return {"text": self.text, "code": self.code}


@app.route('/request', methods=['POST'])
def process():
    data = request.get_json(silent=False)
    app.logger.info("Handling request with text: " + data['text'])
    response = AgentResponse(text="hello world")
    return json.dumps(response.export())


@app.route('/ping', methods=['GET'])
def ping():
    return json.dumps("pong")


if __name__ == '__main__':
    app.run(debug=True)
