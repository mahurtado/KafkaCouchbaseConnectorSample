package com.manolo.kafka;

import com.couchbase.client.dcp.message.DcpMutationMessage;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.connect.kafka.converter.Converter;
import com.couchbase.connect.kafka.dcp.EventType;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.couchbase.connect.kafka.converter.ConverterUtils.*;

public class AirlineConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirlineConverter.class);

    @Override
    public SourceRecord convert(final ByteBuf event, final String bucket, final String topic) {
        EventType type = EventType.of(event);
        if (type != null) {
            Struct record = new Struct(AirlineSchemas.VALUE_DEFAULT_SCHEMA);
            String key;
            long seqno;
            
            if (DcpMutationMessage.is(event)) {
            	key = bufToString(DcpMutationMessage.key(event));
	            seqno = DcpMutationMessage.bySeqno(event);
	            
	            JsonObject json = JsonObject.fromJson(bufToString(DcpMutationMessage.content(event)));
	            record.put("key", key);
	            record.put("id", json.get("id"));
	            record.put("type", json.get("type"));
	            record.put("name", json.get("name"));
	            record.put("iata", json.get("iata"));
	            record.put("callsign", json.get("callsign"));
	            record.put("country", json.get("country"));
	        }
            else {
                LOGGER.warn("unexpected event type {}", event.getByte(1));
                return null;
            }
            
            final Map<String, Object> offset = new HashMap<String, Object>(2);
            offset.put("bySeqno", seqno);
            final Map<String, String> partition = new HashMap<String, String>(2);
            partition.put("bucket", bucket);
            partition.put("partition", DcpMutationMessage.partition(event) + "");

            return new SourceRecord(partition, offset, topic,
            		AirlineSchemas.KEY_SCHEMA, key,
            		AirlineSchemas.VALUE_DEFAULT_SCHEMA, record);
        }
        return null;
    }

}
