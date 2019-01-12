package com.psps.test;

import com.psps.Formatter;
import com.psps.FormatterFactory;
import org.junit.jupiter.api.Test;

import static com.psps.FormatterType.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestErrorFormatter {

    @Test
    void testUnpack() throws Exception{
        String testJson = "{\"packet_type\" : -1," +
                "\t\t\"system\" : 1," +
                "\t\t\"origin\" : \"attiny85\"," +
                "\t\t\"timestamp\" : 5.0," +
                "\t\t\"failure_type” : -1," +
                "\t\t\"message\" : \"Traceback (most recent call last):" +
                "\t\t File <stdin>, line 2, in <module>" +
                "\t\t File /home/flightcomputer/main.py, line 391, in read\"}";
        char []testRawData = testJson.toCharArray();

        FormatterFactory formatterFactory = new FormatterFactory();
        Formatter testErrorFormatter = formatterFactory.getFormatter(ERROR);

        testErrorFormatter.unpack(testRawData);
    }
    @Test
    void testClear() throws Exception{
        String testJson = "";
        char []testRawData = testJson.toCharArray();

        FormatterFactory formatterFactory = new FormatterFactory();
        Formatter testErrorFormatter = formatterFactory.getFormatter(ERROR);

        testErrorFormatter.unpack(testRawData);
        testErrorFormatter.clear();
    }

    @Test
    void testGetStringValue() throws Exception{
        String testJson = "/*INSERT PARTIAL ERROR PACKET HERE*/";
        char []testRawData = testJson.toCharArray();

        FormatterFactory formatterFactory = new FormatterFactory();
        Formatter testErrorFormatter = formatterFactory.getFormatter(ERROR);

        testErrorFormatter.unpack(testRawData);
    }

    @Test
    void testGetFormattedData() throws Exception{
        String testJson = "/*INSERT FULL ERROR PACKET HERE*/";
        char []testRawData = testJson.toCharArray();

        FormatterFactory formatterFactory = new FormatterFactory();
        Formatter testErrorFormatter = formatterFactory.getFormatter(ERROR);

        testErrorFormatter.unpack(testRawData);

        assertEquals(testErrorFormatter.getFormattedData(), testJson.trim());
    }

    @Test
    void testPack(){/*For Future Versions*/}

    @Test
    void testGetUnformattedData(){/*For Future Versions*/}
}
