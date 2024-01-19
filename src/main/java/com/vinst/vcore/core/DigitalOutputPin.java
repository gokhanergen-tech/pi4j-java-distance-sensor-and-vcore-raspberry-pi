package com.vinst.vcore.core;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.vinst.vcore.core.interfaces.PinReaderDigital;
import com.vinst.vcore.core.interfaces.PinWriterDigital;

public class DigitalOutputPin extends Pin<Boolean> implements PinWriterDigital, PinReaderDigital {
    private DigitalOutput digitalOutput;

    @Override
    protected void prepare() {
        var config = DigitalOutput.newConfigBuilder(pi4j)
                .id(this.getPinNumber().name())
                .name(this.getPinNumber().name())
                .address(this.getPinNumber().getPinNumber())
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
        this.digitalOutput = pi4j.create(config);
    }

    /**
     * is bigger than 0V
     */
    @Override
    public Boolean read() {
        return this.digitalOutput.isHigh();
    }

    @Override
    public void high() {
        this.digitalOutput.high();
    }

    @Override
    public void low() {
        this.digitalOutput.low();
    }

    @Override
    public boolean isHigh() {
        return this.digitalOutput.isHigh();
    }

    @Override
    public boolean isLow() {
        return this.digitalOutput.isLow();
    }
}
