package com.vinst.vcore.core;

import java.util.Objects;

import com.pi4j.io.gpio.analog.AnalogOutput;

public class AnalogOutputPin extends Pin<Integer> {
    private AnalogOutput analogOutput;

    @Override
    public Integer read() {
        if (Objects.isNull(analogOutput)) {
            throw new NullPointerException("You must set a pin!");
        }
        return analogOutput.value();
    }

    @Override
    protected void prepare() {
        var ledConfig = AnalogOutput.newConfigBuilder(pi4j)
                .id("temp_0")
                .name("Temp Reader")
                .address(this.getPinNumber().getPinNumber())
                .provider(com.pi4j.io.gpio.analog.AnalogOutputProvider.class);
        this.analogOutput = pi4j.create(ledConfig);
        this.analogOutput.config().shutdownValue(-1);
    }
}
