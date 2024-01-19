package com.vinst.vcore.core;

import com.pi4j.context.Context;
import com.vinst.vcore.core.interfaces.PinReader;
import com.vinst.vcore.core.pi4j.PigIOContext;

public abstract class Pin<T> implements PinReader<T> {

    private PIN pinNumber;

    protected static Context pi4j;

    static {
        pi4j = PigIOContext.getInstance();
    }

    public static enum PIN {
        PIN_24(24),
        PIN_4(4),
        PIN_2(2),
        PIN_3(3),
        PIN_17(17);

        private int pinNumber;

        public int getPinNumber() {
            return pinNumber;
        }

        PIN(int pinNumber) {
            this.pinNumber = pinNumber;
        }
    }

    protected abstract void prepare();

    public PIN getPinNumber() {
        return this.pinNumber;
    }

    public void setPinNumber(PIN pinNumber) {
        this.pinNumber = pinNumber;

        prepare();
    }
}
