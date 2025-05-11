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
def modify_status = JC.request.param('modify_status');

JCLogger logger = JCLoggerFactory.getLogger("query ticket:");

SimpleAjax ret = JC.internal.call(SimpleAjax, 'project', '/auth/check_token_with_pid', [token:token,pid:pid]);
if(ret == null || !ret.available) {
	return GeneralPub.fail('user_not_login', '请登录后操作', null);
}

//重置pid
pid = ret.data.user.pid;

//以取票码查询
def query_result = JC.internal.call(SimpleAjax, 'ticketingsys', '/ticketing/take_by_code_for_direct_check_split', [get_code:qk, pid:pid, modify_status:modify_status]);
if(query_result && query_result.available) {
	query_result.data[0]['seats'] = query_result.data[1];
	query_result = [query_result.data[0]];
}

logger.info("query ticket by flag, qk: {}, result: {}", qk, JackSonBeanMapper.toJson(query_result));

//增加是否过场标志
if(query_result) {
	SimpleDateFormat _sdf_ymdhms_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');
	for (x in query_result) {
		def pass_flag = 0;    //默认未过场
		def play_time = x['plan_date'] + ' ' + x['plan_time'];
		try {
			pass_flag = _sdf_ymdhms_.parse(play_time).before(Calendar.getInstance().getTime());
		} catch (any) {
		}
		x['pass_flag'] = pass_flag;
	}
}

return GeneralPub.success(query_result);
