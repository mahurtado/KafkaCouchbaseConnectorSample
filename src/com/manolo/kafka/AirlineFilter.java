package com.manolo.kafka;

import com.couchbase.client.dcp.message.DcpMutationMessage;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.connect.kafka.filter.Filter;

public class AirlineFilter implements Filter {

	@Override
	public boolean pass(final ByteBuf event) {
		
		return DcpMutationMessage.is(event) && DcpMutationMessage.keyString(event).startsWith("airline");
		
		/*
		if(DcpMutationMessage.is(event)){
			try {
				JsonObject json = JsonObject.fromJson(bufToString(DcpMutationMessage.content(event)));
				return json != null && "airline".equals(json.getString("type"));
			} catch (Exception e) {
				System.out.println("Error processing event: " + DcpMutationMessage.toString(event));
				e.printStackTrace();
				return false;
			}
		}
		return false;
		*/
	}

}
