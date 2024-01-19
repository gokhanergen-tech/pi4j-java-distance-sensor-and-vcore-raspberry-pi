package com.vinst.vcore;

import static com.vinst.vcore.core.Pin.PIN;

import com.pi4j.context.Context;
import com.pi4j.util.Console;
import com.vinst.vcore.core.Pin.PIN;
import com.vinst.vcore.core.DigitalOutputPin;
import com.vinst.vcore.core.pi4j.PigIOContext;
import com.vinst.vcore.sensor.DistanceSensor;

public class App {

    public static final Console console = new Console();

    public static void main(String[] args) {
        console.println("The application is starting!");
        Context pi4j = PigIOContext.getInstance();

        PrintInfo.printLoadedPlatforms(console, pi4j);
        PrintInfo.printDefaultPlatform(console, pi4j);
        PrintInfo.printProviders(console, pi4j);
        PrintInfo.printRegistry(console, pi4j);
        DistanceSensor distanceSensor = new DistanceSensor();
        distanceSensor.connectPins(PIN.PIN_2, PIN.PIN_3);
        DigitalOutputPin digitalOutputPin = new DigitalOutputPin();
        digitalOutputPin.setPinNumber(PIN.PIN_17);

        while (true) {
            try {
                double distance = distanceSensor.getDistance();

                if (distance != -1) {
                    distance = distance / 10;
                    console.println(String.format("Measured Distance: %.2fcm", distance));

                    if (distance < 10 && digitalOutputPin.isLow())
                        digitalOutputPin.high();
                    else if (digitalOutputPin.isHigh() && distance > 10)
                        digitalOutputPin.low();
                }

                Thread.sleep(1000);
            } catch (Exception e) {
                pi4j.shutdown();
                console.println("Veri okuma durduruldu!");
                break;
            }
        }

    }
}
