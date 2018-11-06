package com.lengjiabao.general_api.entry

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.lengjiabao.general_api.ready.Sha1

def _sdf_ = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss');

def appkey = '141';
def appsecrect = 'f32b19ea17bd0ad9df57e5744047e517';
def timestamp = _sdf_.format(Calendar.getInstance().getTime());

def url = 'http://212.64.50.111:8888/capi';

def data = '{"m":"cur_account_list","dt": {"storeId":11,no:"",pageNumber:1,pageSize:1000}}';

List<String> params = new ArrayList<>();
params.add(appkey);
params.add(appsecrect);
params.add(timestamp);
params.add(data);
Collections.sort(params, new Comparator<String>() {
	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
});
StringBuilder sb = new StringBuilder();
for (String param : params) {
	sb.append(param);
}
String str1 = sb.toString();
def sign = Sha1.getSHA1(str1);

def param = 'appkey=' + appkey + '&c=' + data + '&timestamp=' + timestamp + '&sign=' + sign;

param = param + '&sign=' + sign;

def ret = JC.remote.http_call(url, param);

println ret;



