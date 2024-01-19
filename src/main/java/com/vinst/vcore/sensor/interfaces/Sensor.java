package com.vinst.vcore.sensor.interfaces;

import com.vinst.vcore.core.Pin;

public interface Sensor<R> {
    default void connectPins(Pin.PIN... pins) {
    };

    R read();
}
