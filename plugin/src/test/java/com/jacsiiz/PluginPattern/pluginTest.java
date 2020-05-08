package com.jacsiiz.PluginPattern;

import com.jasciiz.PluginPattern.PluginManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class pluginTest {
    private PluginManager pluginManager;

    @Before
    public void setPluginManager(){
        pluginManager = new PluginManager();
    }

    @Test
    public void test1() throws IOException {
        pluginManager.RunPluginByName("plugin1");
    }

    @Test( expected = IOException.class)
    public void test2() throws IOException{
        pluginManager.RunPluginByName("plugin");
    }
}
