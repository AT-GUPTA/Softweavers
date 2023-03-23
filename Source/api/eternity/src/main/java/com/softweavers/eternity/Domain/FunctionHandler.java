package com.softweavers.eternity.Domain;

import java.math.BigDecimal;

public interface FunctionHandler {
    BigDecimal arccos(BigDecimal x);

    BigDecimal pow(BigDecimal base, BigDecimal exp);

    BigDecimal log(BigDecimal val, BigDecimal base);

    BigDecimal gamma(BigDecimal z);

    BigDecimal sinh(BigDecimal x);

    BigDecimal xToY(BigDecimal y);
}
