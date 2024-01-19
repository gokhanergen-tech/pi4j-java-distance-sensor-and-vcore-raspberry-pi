package com.vinst.vcore.core.pi4j;

import java.util.Objects;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;

public class PigIOContext {

    private static Context pi4jContext;

    private PigIOContext() {
    };

    public static Context getInstance() {
        if (Objects.isNull(pi4jContext))
            pi4jContext = Pi4J.newAutoContext();

        return pi4jContext;
    }

}
