package com.psps.test;

import static com.psps.FormatterType.*;
import static org.junit.jupiter.api.Assertions.*;

import com.psps.Formatter;
import com.psps.FormatterFactory;
import org.junit.jupiter.api.Test;


class TestFlightFormatter {

    @Test
    void testUnpack() throws Exception{
        String testJson = "{\"packet_type\" : 0, \"system\" : 1, \"flight_data\" : [{\"altitude\" : 2000}, {\"max_altitude\" : 3000}]}";
        char []testRawData = testJson.toCharArray();

        FormatterFactory formatterFactory = new FormatterFactory();
        Formatter testFlightFormatter = formatterFactory.getFormatter(FLIGHT);

        testFlightFormatter.unpack(testRawData);

        assertEquals(testJson, testFlightFormatter.getFormattedData());
        assertEquals(2000, Integer.parseInt(testFlightFormatter.getStringValue("altitude")));
        assertEquals(2000, Integer.parseInt(testFlightFormatter.getStringValue("max_altitude")));
    }
    @Test
    void testClear() throws Exception{
        String testJson = "{\"packet_type\" : 0, \"system\" : 1, \"flight_data\" : [{\"altitude\" : 2000}]}";
        char []testRawData = testJson.toCharArray();

        FormatterFactory formatterFactory = new FormatterFactory();
        Formatter testFlightFormatter = formatterFactory.getFormatter(FLIGHT);

        testFlightFormatter.unpack(testRawData);
        testFlightFormatter.clear();

        assertNull(testFlightFormatter.getStringValue("altitude"));
    }

    @Test
    void testGetStringValue() throws Exception{
        String testJson = "{\"packet_type\" : 0, \"system\" : 1, \"flight_data\" : [{\"altitude\" : 2000}, {\"charge_drogue_1\" : true}], \"status-data\" : [{\"raspberry_pi_zero_w\" : true}, {\"status_timestamp\" : 5.0}]}";
        char []testRawData = testJson.toCharArray();

        FormatterFactory formatterFactory = new FormatterFactory();
        Formatter testFlightFormatter = formatterFactory.getFormatter(FLIGHT);

        testFlightFormatter.unpack(testRawData);

        assertEquals (2000.0, Float.parseFloat(testFlightFormatter.getStringValue("altitude")));
        assertEquals (5.0, Float.parseFloat(testFlightFormatter.getStringValue("status_timestamp")));
        assertTrue (Boolean.parseBoolean(testFlightFormatter.getStringValue("charge_drogue_1")));
        assertTrue (Boolean.parseBoolean(testFlightFormatter.getStringValue("raspberry_pi_zero_w")));
    }

}
