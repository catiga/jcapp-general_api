package com.lengjiabao.general_api.ready.common

import java.io.Serializable


class McAvailabilityStatus implements Serializable {
	private boolean available;
	
	private String[] messages;
	
	private RetObj obj = new RetObj() {
		
		@Override
		public Object getObj() {
			return null;
		}
	};
}
