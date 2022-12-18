/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.typeobject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.iluwatar.typeobject.Candy.Type;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * The JsonParser class helps parse the json file candy.json to get all the different candies.
 */

public class JsonParser {
  Hashtable<String, Candy> candies;

  JsonParser() {
    this.candies = new Hashtable<>();
  }

  void parse() throws JsonParseException {
    var is = this.getClass().getClassLoader().getResourceAsStream("candy.json");
    var reader = new InputStreamReader(is);
    var json = (JsonObject) com.google.gson.JsonParser.parseReader(reader);
    var array = (JsonArray) json.get("candies");
    for (var item : array) {
      var candy = (JsonObject) item;
      var name = candy.get("name").getAsString();
      var parentName = candy.get("parent").getAsString();
      var t = candy.get("type").getAsString();
      var type = Type.CRUSHABLE_CANDY;
      if (t.equals("rewardFruit")) {
        type = Type.REWARD_FRUIT;
      }
      var points = candy.get("points").getAsInt();
      var c = new Candy(name, parentName, type, points);
      this.candies.put(name, c);
    }
    setParentAndPoints();
  }

  void setParentAndPoints() {
    for (var e = this.candies.keys(); e.hasMoreElements(); ) {
      var c = this.candies.get(e.nextElement());
      if (c.parentName == null) {
        c.parent = null;
      } else {
        c.parent = this.candies.get(c.parentName);
      }
      if (c.getPoints() == 0 && c.parent != null) {
        c.setPoints(c.parent.getPoints());
      }
    }
  }

}
