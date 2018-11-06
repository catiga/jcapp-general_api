package com.lengjiabao.general_api.ready.util

//import com.jeancoder.scm.ready.dto.AuthToken
//import com.jeancoder.scm.ready.dto.DataTrans
//import com.jeancoder.scm.ready.dto.SysProjectInfo

class GlobalHolder {

	
	private static ThreadLocal<String> _token_ = new ThreadLocal<String>();
	
	private static ThreadLocal<BigInteger> _pid_ = new ThreadLocal<BigInteger>();
	
	public static void setToken(String token) {
		_token_.set(token);
	}
	
	public static String getToken() {
		return _token_.get();
	}
	
	public static void setPid(BigInteger pid) {
		_pid_.set(pid);
	}
	
	public static BigInteger getPid() {
		return _pid_.get();
	}
}
