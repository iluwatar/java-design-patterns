package com.iluwatar.loan.pattern;

import java.io.BufferedWriter;
import java.io.IOException;

public interface WriteBlock {
    void call(BufferedWriter writer) throws IOException;
}
