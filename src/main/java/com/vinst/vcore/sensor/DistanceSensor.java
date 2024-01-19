package com.vinst.vcore.sensor;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.vinst.vcore.core.DigitalInputPin;
import com.vinst.vcore.core.DigitalOutputPin;
import com.vinst.vcore.core.Pin;
import com.vinst.vcore.sensor.interfaces.Sensor;

import java.lang.System;

public class DistanceSensor implements Sensor<Integer> {

    private static final int ECHO_INDEX = 0;
    private static final int TRIG_INDEX = 1;
    private long REJECTION_START = 10000, REJECTION_TIME = 23529411;
    private DigitalOutputPin digitalOutput;
    private DigitalInputPin digitalInput;

    @Override
    public void connectPins(Pin.PIN... pins) {

        DigitalOutputPin digitalOutputPin = new DigitalOutputPin();
        digitalOutputPin.setPinNumber(pins[TRIG_INDEX]);
        digitalOutput = digitalOutputPin;

        DigitalInputPin digitalInputPin = new DigitalInputPin();
        digitalInputPin.setPinNumber(pins[ECHO_INDEX]);
        digitalInput = digitalInputPin;
    }

    @Override
    public Integer read() {
        try {
            return getDistance();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return -1;
        }
    }

    public int getDistance() throws Exception { // in milimeters
        int distance = 0;
        long start_time, end_time, rejection_start = 0, rejection_time = 0;
        DigitalOutputPin pin_trig = digitalOutput;
        DigitalInputPin pin_echo = digitalInput;

        // Start ranging- trig should be in high state for 10us to generate ultrasonic
        // signal
        // this will generate 8 cycle sonic burst.
        // produced signal would looks like, _|-----|

        pin_trig.low();
        busyWaitMicros(2);
        pin_trig.high();
        busyWaitMicros(10);
        pin_trig.low();

        // echo pin high time is propotional to the distance _|----|
        // distance calculation
        while (pin_echo.isLow()) { // wait until echo get high
            busyWaitNanos(1); // wait 1ns
            rejection_start++;
            if (rejection_start == REJECTION_START)
                return -2; // something wrong
        }
        start_time = System.nanoTime();

        while (pin_echo.isHigh()) { // wait until echo get low
            busyWaitNanos(1); // wait 1ns
            rejection_time++;
            if (rejection_time == REJECTION_TIME)
                return -1; // infinity
        }
        end_time = System.nanoTime();

        distance = (int) ((end_time - start_time) / 5882.35294118); // distance in mm
        // distance=(end_time-start_time)/(200*29.1); //distance in mm
        return distance;
    }

    public static void busyWaitMicros(long micros) {
        long waitUntil = System.nanoTime() + (micros * 1_000);
        while (waitUntil > System.nanoTime()) {
            ;
        }
    }

    public static void busyWaitNanos(long nanos) {
        long waitUntil = System.nanoTime() + nanos;
        while (waitUntil > System.nanoTime()) {
            ;
        }
    }

}
