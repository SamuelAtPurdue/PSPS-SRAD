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

    @Test
    void testGetFormattedData() throws Exception{
        String testJson = "{\"packet_type\" : 0," +
                " \"system\" : 1," +
                " \"flight_data\" : [" +
                "   {\"altitude\" : 9144.0}," +
                "   {\"max_altitude\" : 9144.0}," +
                "   {\"velocity_x\" : 1.13}," +
                "   {\"velocity_y\" : -0.21}," +
                "   {\"velocity_z\" : 11.43}," +
                "   {\"acceleration_x\" : -0.03}," +
                "   {\"acceleration_y\" : -0.01}," +
                "   {\"acceleration_z\" : -11.23}," +
                "   {\"pitch_attitude\" : 87.12}," +
                "   {\"yaw_attitude\" : 2.88}," +
                "   {\"roll_attitude\" : 278.21}," +
                "   {\"roll_rate\" : 5.23}," +
                "   {\"latitude\" : 3.231444549560546875E1}," +
                "   {\"longitude\" : 1.0677889251708984375E2}," +
                "   {\"battery_voltage_1\" : 3.5}," +
                "   {\"battery_voltage_2\" : 3.6}," +
                "   {\"current_draw_1\" : 0.302}," +
                "   {\"current_draw_2\" : 0.291}," +
                "   {\"temperature\" : 32.71}," +
                "   {\"charge_drogue_1\" : true}," +
                "   {\"charge_drogue_2\" : true}," +
                "   {\"charge_main_1\" : true}," +
                "   {\"charge_main_2\" : true}," +
                "   {\"charge_payload_1\" : true}," +
                "   {\"charge_payload_2\" : true}," +
                "   {\"flight_timestamp\" : 5.213}]," +
                " \"status_data\" : [" +
                "   {\"raspberry_pi_zero_w\" : true}," +
                "   {\"attiny85\" : true}," +
                "   {\"ultimate_gps_module\" : true}," +
                "   {\"lsm9ds1\" : true}," +
                "   {\"bmp280\" : true}," +
                "   {\"ds3231\" : true}," +
                "   {\"charge_drogue_1\" : true}," +
                "   {\"charge_drogue_2\" : true}," +
                "   {\"charge_main_1\" : true}," +
                "   {\"charge_main_2\" : true}," +
                "   {\"charge_payload_1\" : true}," +
                "   {\"charge_payload_2\" : true}," +
                "   {\"status_timestamp\" : 5.273}]}";
        char []testRawData = testJson.toCharArray();

        FormatterFactory formatterFactory = new FormatterFactory();
        Formatter testFlightFormatter = formatterFactory.getFormatter(FLIGHT);

        testFlightFormatter.unpack(testRawData);

        assertEquals(testFlightFormatter.getFormattedData(), testJson.trim());
    }

    @Test
    void testPack(){/*For Future Versions*/}

    @Test
    void testGetUnformattedData(){/*For Future Versions*/}
}
