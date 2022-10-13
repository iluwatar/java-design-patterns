package com.iluwatar.loan.pattern;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * ReadBlock interface is used for defining how to read and operate the data from a file.
 */
public interface ReadBlock {

    void call(BufferedReader reader) throws IOException;

}
