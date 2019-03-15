package application;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialConnector{


    Timeline timeline;
    GraphScene graphScene;
    TopInfoScene topInfoScene;
    SerialPort arduinoPort = null;
    SideInfoScene sideInfoScene;

    public SerialConnector(Timeline t, GraphScene gc, SideInfoScene sc){
        timeline = t;
        graphScene = gc;
        sideInfoScene = sc;
       // topInfoScene = tc;
    }



    public void connectArduino(String port){
        SerialPort serialPort = new SerialPort(port);
        JSONParser parser = new JSONParser();
        arduinoPort = serialPort;
        final int length = 21;
        try {
            serialPort.openPort();
            StringBuilder message = new StringBuilder();
            serialPort.setParams(9600, 8, 0, 0);//Set params
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            ArrayList<Byte> packet = new ArrayList<Byte>();
            AtomicBoolean reading = new AtomicBoolean(false);
            serialPort.setEventsMask(mask);//Set mask
            serialPort.addEventListener((SerialPortEvent event) -> {
                    if(event.isRXCHAR() && event.getEventValue() > 0){
                        try {
                            byte buffer[] = serialPort.readBytes();
                            for (byte b: buffer) {

                                if (b == 126){
                                    //send and format json
                                    if (reading.get() == true && packet.size() == 22){
                                        //checksum
                                        JSONObject json = new JSONObject();
                                        json.put("accX", packet.get(15));
                                        json.put("accY", packet.get(16));
                                        json.put("accZ", packet.get(17));
                                        json.put("gyrX", packet.get(18));
                                        json.put("gyrY", packet.get(19));
                                        json.put("gyrZ", packet.get(20));
                                        System.out.println(packet.toString());
                                        timeline.add(json);
                                        graphScene.addNewestPoint(timeline);
                                        sideInfoScene.update(timeline);
                                        //clear packet
                                        packet.clear();

                                    }
                                    packet.add(b);
                                    reading.set(true);

                                }else if (reading.get() == true){
                                    packet.add(b);
                                }

                            }
                        }
                        catch (SerialPortException ex) {
                            System.out.println(ex);
                            System.out.println("serialEvent");
                        }
                    }


                });


        } catch (SerialPortException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.out.println("SerialPortException: " + ex.toString());
        } catch (Exception e){
            System.out.println("Error");
        }
    }

    public void disconnectArduino(){

        System.out.println("disconnectArduino()");
        if(arduinoPort != null){
            try {
                arduinoPort.removeEventListener();

                if(arduinoPort.isOpened()){
                    arduinoPort.closePort();
                }

            } catch (SerialPortException ex) {
                Logger.getLogger(Main.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

}
