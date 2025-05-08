package com.lengjiabao.general_api.entry.ex.admin.order.tcss

import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory

import java.text.SimpleDateFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;
def qk = JC.request.param('qk');
def token = JC.request.param('token');

JCLogger logger = JCLoggerFactory.getLogger("query ticket:");

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token_with_pid', [token:token,pid:pid]);
if(ret == null || !ret.available) {
	return GeneralPub.fail('user_not_login', '请登录后操作', null);
}

//重置pid
pid = ret.data.user.pid;

def qk_is_mobile = isPhone(qk);
def user_info_obj = null;
if(qk_is_mobile) {
	SimpleAjax user_info = JC.internal.call(SimpleAjax, 'crm', '/h5/p/info_by_mobile', [pid:pid, mobile:qk]);
	if(user_info && user_info.available && user_info.data) {
		user_info_obj = user_info.data;
	}
}

def query_result = [];
if(user_info_obj!=null) {
	//说明以手机号查询
	query_result = JC.internal.call('ticketingsys', '/api/orders', [pid:pid, apid:user_info_obj.ap_id]);
	try {
		query_result = JackSonBeanMapper.jsonToMap(query_result);
		if(query_result.code!=0) {
			return GeneralPub.fail(query_result.code + '', query_result.msg, null);
		}
		query_result = query_result.data;
	} catch(any) {
	}
} else if(qk.startsWith('901')) {
	//说明以交易编号查询
	SimpleAjax trade_ajax = JC.internal.call(SimpleAjax, 'trade', '/query/trade_by_num', [pid:pid, tnum:qk]);
	if(trade_ajax && trade_ajax.available) {
		def trade_buyerid = trade_ajax.data[0]['buyerid'];
		if(trade_buyerid) {
			//再根据这个用户的id来查
			query_result = JC.internal.call('ticketingsys', '/api/orders', [pid:pid, apid:user_info_obj.ap_id]);
			try {
				query_result = JackSonBeanMapper.jsonToMap(query_result);
				if(query_result.code!=0) {
					return GeneralPub.fail(query_result.code + '', query_result.msg, null);
				}
				query_result = query_result.data;
			} catch(any) {
			}
		}
	}
} else {
	//判断是取票码还是订单号
	def is_order = false;
	if(qk.length()>8) {
		def startymd = qk.substring(0, 8);
		SimpleDateFormat _sdf_ymd_ = new SimpleDateFormat('yyyyMMdd');
		if (startymd.startsWith("20")) {
			try {
				_sdf_ymd_.parse(startymd);
				is_order = true;
			} catch(not_order) {

			}
		}
	}
	logger.info("query ticket by flag is_order: {}, qk: {}", is_order, qk);
	if(is_order) {
		//以订单号查询
		query_result = JC.internal.call('ticketingsys', '/api/orders', [pid:pid, order_no:qk]);
		try {
			query_result = JackSonBeanMapper.jsonToMap(query_result);
			if(query_result.code!=0) {
				return GeneralPub.fail(query_result.code + '', query_result.msg, null);
			}
			query_result = query_result.data;
		} catch(any) {
		}
	} else {
		//以取票码查询
		// query_result = JC.internal.call(SimpleAjax, 'ticketingsys', '/ticketing/query_by_code_flag1', [flag_1:qk, pid:pid]);
		query_result = JC.internal.call(SimpleAjax, 'ticketingsys', '/ticketing/query_by_code', [get_code:qk, pid:pid]);
		if(query_result && query_result.available) {
			query_result.data[0]['seats'] = query_result.data[1];
			query_result = [query_result.data[0]];
		}
	}
}

//增加是否过场标志
if(query_result) {
	SimpleDateFormat _sdf_ymdhms_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');
	for(x in query_result) {
		def pass_flag = 0;	//默认未过场
		def play_time = x['plan_date'] + ' ' + x['plan_time'];
		try {
			pass_flag = _sdf_ymdhms_.parse(play_time).before(Calendar.getInstance().getTime());
		} catch(any) {
		}
		x['pass_flag'] = pass_flag;
	}
}

return GeneralPub.success(query_result);


boolean isPhone(String phone) {
	String regex = '^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$';
	if (phone.length() != 11) {
		return false;
	} else {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phone);
		boolean isMatch = m.matches();
		if (!isMatch) {
			return false;
		}
		return true;
	}
}
