package com.iluwatar.pipeline;

class ConvertToCharArrayHandler implements Handler<String, char[]> {
    @Override
    public char[] process(String input) {
        return input.toCharArray();
    }
}
