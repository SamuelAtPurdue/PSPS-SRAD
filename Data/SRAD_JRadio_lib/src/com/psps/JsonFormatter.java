package com.psps;

/**
 * Created by Samuel Hild on 10/1/2018.
 * This class will not be instantiated outside the package.
 * any instance should be acquired through the DataFormatter() class.
 */
class JsonFormatter implements Formatter {


    @Override
    public void pack(Object... dataIn) {

    }

    @Override
    public String[] unpack() {
        return new String[0];
    }

    @Override
    public Object getFormattedData() {
        return null;
    }
}
