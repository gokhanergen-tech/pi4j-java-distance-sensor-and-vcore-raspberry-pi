package com.vinst.vcore.sensor;

import com.vinst.vcore.core.AnalogOutputPin;
import com.vinst.vcore.core.Pin;
import com.vinst.vcore.sensor.interfaces.Sensor;

public class TempSensor implements Sensor<Integer> {

    private AnalogOutputPin tempSensorPin;

    public TempSensor(Pin.PIN pin) {
        tempSensorPin = new AnalogOutputPin();
        tempSensorPin.setPinNumber(pin);
    }

    @Override
    public void connectPins(Pin.PIN... pins) {
        tempSensorPin.setPinNumber(pins[0]);
    };

    public Integer read() {
        return tempSensorPin.read();
    };

}
