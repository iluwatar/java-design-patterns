package com.iluwatar.loan.pattern;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * WriteBlock interface is used for defining how to write data to a file and what data to write.
 */
public interface WriteBlock {

    void call(BufferedWriter writer) throws IOException;

}
