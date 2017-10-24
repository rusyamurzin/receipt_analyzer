# -*- coding: utf-8 -*-
from flask import render_template, request, jsonify
from flask_restful import Resource
import json

from app import app, api, models
from .forms import LoginForm
from .FtsRequest import FtsRequest

APIv1 = "/api/v1.0"

@app.route(APIv1 + '/login')
def login():
    form = LoginForm()
    return render_template('login.json', form = form)

# Password from json not used because it doesn't exist in current User model
@app.route(APIv1 + '/receipt')
def getReceipt():
	# Get JSON from GET request
	json = request.get_json(force = True)
	# Parse QR-string
	qrArgs = json['qr'].split("&")
	fn = qrArgs[2][3:]
	fd = qrArgs[3][2:]
	fp = qrArgs[4][3:]
	# Get phone/pass from fts account
	# user = User.query.filter_by(username = json['login'], password = json['password']).first_or_404()
	user = models.User.query.filter_by(username = json['login']).first_or_404()

	receipt = FtsRequest().getReceipt(fn, fd, fp, user.phone, user.fts_key)
	return jsonify(receipt)

class UserList(Resource):
	def get(self):
		pass

	def post(self):
		pass

class Users(Resource):
	def get(self, id):
		pass

	def put(self, id):
		pass

class UserInfo(Resource):
	def get(self):
		pass

api.add_resource(UserList, APIv1 + '/users', endpoint = 'users')
api.add_resource(Users, APIv1 + '/users/<int:id>', endpoint = 'user')