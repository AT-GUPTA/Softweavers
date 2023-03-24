package com.softweavers.eternity.Domain;

public interface ParserHandler {
    /**
     * Handles which functions will be caught and how it will be calculated.
     * @param expr The expression to be parsed
     * @return The result concurrent result
     */
    String evaluateFunctions(String expr);
}
