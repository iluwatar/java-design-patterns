package com.jasciiz.pluginpattern;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instantiate plugins by .xml file
 *  provid
 */
public class PluginManager {
  private static Map<String, Plugin> PLUGINS = new HashMap<String, Plugin>();
  private static String filePath = "src/main/java/com/jasciiz/PluginPattern/PluginPattern.xml";

  public PluginManager() {
    loadPlugins();
  }

  public PluginManager(String path) {
    this.filePath = path;
    loadPlugins();
  }

  /**
   * Instantiate plugin and put it into PLUGINS.
   * @param name the name of plugin,
   * @param place the place of plugin class.
   */
  private static void pluginFactory(String name, String place) {
    try {
      Class<?> classPlugin = Class.forName(place);
      Object pluginObject = classPlugin.getDeclaredConstructor().newInstance();
      Plugin example = (Plugin) pluginObject;
      example.init();
      PLUGINS.put(name, example);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }


  /**
   * load plugins from xml file.
   *
   */
  private static void loadPlugins() {
    try {
      SAXBuilder saxBuilder = new SAXBuilder();
      InputStream is = new FileInputStream(new File(filePath));
      Document document = saxBuilder.build(is);
      Element rootElement = document.getRootElement();
      Element plugins = rootElement.getChild("plugins");
      List<Element> pluginList = plugins.getChildren();
      for (Element plugin : pluginList) {
        Element name = plugin.getChild("name");
        Element place = plugin.getChild("place");
        String nameValue = name.getValue();
        String placeValue = place.getValue();
        pluginFactory(nameValue, placeValue);
      }
    } catch (JDOMException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * run plugin by name.
   *
   */
  public void runPluginByName(String name) throws IOException {
    if (PLUGINS.containsKey(name)) {
      PLUGINS.get(name).run();
    } else {
      throw new IOException("Wrong name of plugin");
    }

  }

}