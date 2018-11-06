package com.lengjiabao.general_api.ready.wx

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.util.JackSonBeanMapper

class WxMsg {
	
	static void main(def argc) {
		def ts = '1539420615000';
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.valueOf(ts));
		def pay_year = c.get(Calendar.YEAR);
		def pay_month = c.get(Calendar.MONTH) + 1;
		def pay_day = c.get(Calendar.DAY_OF_MONTH);
		
		println pay_year;
		println pay_month;
		println pay_day;
		
		def trans_data = [:];
		trans_data['pay_amount'] = '3500';
		trans_data['a_time'] = Calendar.getInstance().getTimeInMillis() + '';
		trans_data['tnum'] = '191920029190125';
		trans_data['storename'] = '愉悦家精品店';
		
		
		WxMsg.push_consume_msg('wxb8fd6ebb082e8b24', 'ca3bace02b50fe9ee1cb84bf35359f65', 'ooKYc1NqeJUhc9v6c7xPtHJJ_zUY', trans_data);
	}

	def static push_consume_msg(def app_id, def app_key, def open_id, def trans_data) {
		def temp_id = 'prPolQFiSJsjKlBDTBWgRvYO1bI_tQC8cUMHhyHYdzg';
		/*
		 * {{first.DATA}}
			账单编号：{{keyword1.DATA}}
			消费金额：{{keyword2.DATA}}
			消费途径：{{keyword3.DATA}}
			消费账户：{{keyword4.DATA}}
			消费时间：{{keyword5.DATA}}
			{{remark.DATA}}
		 */
		def trans_pay_amount = new BigDecimal(trans_data['pay_amount']).divide(new BigDecimal('100')).setScale(2);
		def ts = Long.valueOf(trans_data['a_time']);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.valueOf(ts));
		def pay_year = c.get(Calendar.YEAR);
		def pay_month = c.get(Calendar.MONTH) + 1;
		def pay_day = c.get(Calendar.DAY_OF_MONTH);
		
		def trade_num = trans_data['tnum'];
		def store_name = trans_data['storename'];
		def msg_data = """
		{
           "touser":"${open_id}",
           "template_id":"${temp_id}",
           "url":"https://huacai.mt.92youpin.com/general_api/tcss/index#/storeList",
		   "miniprogram":{
             "appid":"wxc350d26e493c8cac",
             "pagepath":"/pages/launch"
           }, 
           "data":{
                   "first": {
                       "value":"尊敬的客户：欢迎光临愉悦家华彩店。",
                       "color":"#173177"
                   },
                   "keyword1":{
                       "value":"${trade_num}",
                       "color":"#173177"
                   },
                   "keyword2": {
                       "value":"${trans_pay_amount}元",
                       "color":"#173177"
                   },
				   "keyword3": {
                       "value":"${store_name}",
                       "color":"#173177"
                   },
				   "keyword4": {
                       "value":"微信支付",
                       "color":"#173177"
                   },
                   "keyword5": {
                       "value":"${pay_year}年${pay_month}月${pay_day}日",
                       "color":"#173177"
                   },
                   "remark":{
                       "value":"点击查看账单详细信息！",
                       "color":"#173177"
                   }
           }
       }
		""";

		//获取access_token
		def token_url = 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=' + app_id + '&secret=' + app_key;
		def token_result = JC.remote.http_call(token_url, null);
		token_result = JackSonBeanMapper.jsonToMap(token_result);
		def access_token = token_result['access_token'];
		if(access_token) {
			def tpl_msg_url = 'https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=' + access_token;
			
			def push_result = JC.remote.http_call(tpl_msg_url, msg_data);
			println push_result;
			return push_result;
		}
		return null;
	}
}
