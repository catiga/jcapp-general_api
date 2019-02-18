package com.lengjiabao.general_api.ready.common

import java.io.Serializable


class McAvailabilityStatus implements Serializable {
	 boolean available;
	
	 String[] messages;
	
	 RetObj obj = new RetObj() {
		
		@Override
		public Object getObj() {
			return null;
		}
	};
}
