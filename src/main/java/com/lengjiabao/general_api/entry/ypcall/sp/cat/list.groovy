package com.lengjiabao.general_api.entry.ypcall.sp.cat

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;

SimpleAjax ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/cat_list', [pid:pid]);

if(!ret_obj.available) {
	return GeneralPub.comfail(ret_obj.messages[0]);
}

def retobj = [];
for(x in ret_obj.data) {
	if(x['id'].toString()=='17') {
		continue;
	}
	def icon = x['cat_icon'];
	//icon = 'o_1c7qe82p2pm7sv5g9l1fd2v4f9.png';
	if(!icon) {
		icon = 'o_1c7qe82p2pm7sv5g9l1fd2v4f9.png';
	}
	//补充前缀
	//icon = 'http://pe1d.static.pdr365.com/' + icon;
	
	retobj.add([id:x['id'],name:x['cat_name_cn'],info:x['cat_info'],icon:icon]);
}

//取需要推荐首页显示的美容项目
def cat_id = 17;
def ss = 0;	//取启用状态的
ret_obj = JC.internal.call(SimpleAjax, 'reserve', '/sp/cat_sps', [pid:pid,cat_id:cat_id,ss:ss]);
def index_sps = [];
if(ret_obj.available) {
	for(x in ret_obj.data) {
		def id = x['id'];
		def spno = x['spno'];
		def spname = x['spname'];
		def spinfo = x['spinfo'];
		def spcontent = x['spcontent'];
		def imgurl = x['imgurl'];
		def price = x['price'];
		def duratime = x['duratime'];
		def duraunit = x['duraunit'];
		
		//补充前缀
		imgurl = 'http://pe1s.static.pdr365.com/' + imgurl;
		
		def data_model = [id:id,spno:spno,spname:spname,spinfo:spinfo,spcontent:spcontent,imgurl:imgurl,price:price,duratime:duratime,duraunit:duraunit];
		index_sps.add(data_model);
	}
}

return GeneralPub.success([retobj, index_sps]);

