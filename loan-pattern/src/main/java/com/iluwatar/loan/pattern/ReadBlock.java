package com.iluwatar.loan.pattern;

import java.io.BufferedReader;
import java.io.IOException;

public interface ReadBlock {
    void call(BufferedReader reader) throws IOException;
}
