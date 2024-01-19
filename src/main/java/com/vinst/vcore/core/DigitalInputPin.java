package com.vinst.vcore.core;

import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.vinst.vcore.core.interfaces.PinReaderDigital;

public class DigitalInputPin extends Pin<Boolean> implements PinReaderDigital {
    private DigitalInput digitalInput;

    @Override
    protected void prepare() {
        var config = DigitalInput.newConfigBuilder(pi4j)
                .id(this.getPinNumber().name())
                .name(this.getPinNumber().name())
                .address(this.getPinNumber().getPinNumber())
                .provider("pigpio-digital-input");
        this.digitalInput = pi4j.create(config);
    }

    @Override
    public Boolean read() {
        return this.digitalInput.isHigh();
    }

    @Override
    public boolean isHigh() {
        return this.digitalInput.isHigh();
    }

    @Override
    public boolean isLow() {
        return this.digitalInput.isLow();
    }

}
