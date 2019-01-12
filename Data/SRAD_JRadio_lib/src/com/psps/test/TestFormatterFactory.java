package com.psps.test;

import static org.junit.jupiter.api.Assertions.*;

import com.psps.*;
import org.junit.jupiter.api.Test;

import static com.psps.FormatterType.*;

class TestFormatterFactory {
    @Test
    void testGetFormatter() throws UnknownFormatterException {
        FormatterFactory factory = new FormatterFactory();
        Formatter testFlight = factory.getFormatter(FLIGHT);
        Formatter testError = factory.getFormatter(ERROR);

        assertTrue (testFlight instanceof FlightFormatter);
        assertTrue (testError instanceof ErrorFormatter);
    }
}
