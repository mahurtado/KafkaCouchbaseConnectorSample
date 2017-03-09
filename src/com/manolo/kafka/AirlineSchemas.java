package com.manolo.kafka;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;

public enum AirlineSchemas {
	;
	
	public static final Schema KEY_SCHEMA = SchemaBuilder.string().build();
	public static final Schema VALUE_DEFAULT_SCHEMA =
			SchemaBuilder.struct().name("com.manolo.Airline")
			.field("key", Schema.STRING_SCHEMA)
			.field("id", Schema.INT32_SCHEMA)
			.field("type", Schema.STRING_SCHEMA)
			.field("name", Schema.OPTIONAL_STRING_SCHEMA)
			.field("iata", Schema.OPTIONAL_STRING_SCHEMA)
			.field("icao", Schema.OPTIONAL_STRING_SCHEMA)
			.field("callsign", Schema.OPTIONAL_STRING_SCHEMA)
			.field("country", Schema.OPTIONAL_STRING_SCHEMA)
			.build();

}

