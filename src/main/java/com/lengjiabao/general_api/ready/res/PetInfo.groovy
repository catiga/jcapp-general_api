package com.lengjiabao.general_api.ready.res

import com.jeancoder.app.sdk.JC

class PetInfo {

	def bearing = JC.request.param('bearing')?.trim();
	def bindNo = JC.request.param('bindNo')?.trim();
	def birth = JC.request.param('birth')?.trim();
	def cat = JC.request.param('cat')?.trim();
	def catcode = JC.request.param('catcode')?.trim();
	def character = JC.request.param('character')?.trim();
	def color = JC.request.param('color')?.trim();
	def food = JC.request.param('food')?.trim();
	def gender = JC.request.param('gender')?.trim();
	def headimg = JC.request.param('headimg')?.trim();
	def high = JC.request.param('high')?.trim();
	def name = JC.request.param('name')?.trim();
	def nursing = JC.request.param('nursing')?.trim();
	def remark = JC.request.param('remark')?.trim();
	def sicken = JC.request.param('sicken')?.trim();
	def sterilisation = JC.request.param('sterilisation')?.trim();
	
	def id = JC.request.param('id');
}
