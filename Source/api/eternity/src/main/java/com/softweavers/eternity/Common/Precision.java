package com.softweavers.eternity.Common;

import java.math.MathContext;
import java.math.RoundingMode;

public interface Precision {
    MathContext MATH_CONTEXT = new MathContext(20, RoundingMode.HALF_EVEN);

}
