package com.lengjiabao.general_api.entry.api.gate

import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.lengjiabao.general_api.ready.util.DataUtils

import java.text.SimpleDateFormat
import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.common.SimpleAjax
import com.lengjiabao.general_api.ready.util.GlobalHolder
import com.lengjiabao.general_api.ready.util.JackSonBeanMapper
import com.lengjiabao.general_api.ready.ypcall.GeneralPub

def pid = GlobalHolder.pid;
def qk = JC.request.param('qk');
def modify_status = JC.request.param('modify_status');

JCLogger logger = JCLoggerFactory.getLogger("query ticket:");

if (!qk.startsWith("so-")) {
    return new RespModel(errorCode: 901001, errorMessage: DataUtils.toUnicode("无效的自营快捷入场码"), status: 0)
}

//以取票码查询
SimpleAjax query_result = JC.internal.call(SimpleAjax, 'ticketingsys', '/ticketing/take_by_code_for_direct_check_split', [get_code:qk, pid:pid, modify_status:modify_status]);

logger.info("query ticket by flag, qk: {}, result: {}", qk, JackSonBeanMapper.toJson(query_result));

def ret_data = null;

if(query_result && query_result.available) {
    ret_data = query_result.data;
}

if (ret_data == null) {
    return new RespModel(errorCode: query_result.messages[0], errorMessage: query_result.messages[1], status: 0)
}

//增加是否过场标志

SimpleDateFormat _sdf_ymdhms_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');
for (x in ret_data[1]) {
    def pass_flag = 0;    //默认未过场
    def play_time = x['plan_date'] + ' ' + x['plan_time'];
    try {
        pass_flag = _sdf_ymdhms_.parse(play_time).before(Calendar.getInstance().getTime());
    } catch (any) {
    }
    x['pass_flag'] = pass_flag;
}

try {
    def orderData = ret_data[0];
    def orderSeats = ret_data[1];

    RespData respData = new RespData();
    respData.cinemaName = DataUtils.toUnicode(orderData["store_name"]);
    respData.gateDealNum = "e3811e62b99b05ea01c0cac5c7509cb5";
    respData.ticketNum = orderData["order_no"];
    respData.movieName = DataUtils.toUnicode(orderData["film_name"]);
    respData.seatInfo = "";
    respData.hallName = DataUtils.toUnicode(orderData["hall_name"]);
    respData.startTime = orderData["plan_date"] + " " + orderData["plan_time"]
    respData.endTime = orderData["plan_date"] + " " + orderData["plan_time"]
    respData.ticketType = DataUtils.toUnicode("成人票")
    respData.price = orderData["pay_amount"];

    return RespModel(status: 1, errorMessage: "", errorCode: 0, data: respData);
} catch (e) {
    logger.error("闸机检票服务异常", e)
    return new RespModel(errorCode: 909999, errorMessage: DataUtils.toUnicode("检票服务调试中"), status: 0)
}

return GeneralPub.success(query_result);
