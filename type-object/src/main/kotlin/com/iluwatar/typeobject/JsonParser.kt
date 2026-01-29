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
package com.iluwatar.typeobject

// ABOUTME: Parses the candy.json resource file to create Candy objects with their type hierarchy.
// ABOUTME: Resolves parent references and inherits points from parent candies.

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import java.io.InputStreamReader
import java.util.Hashtable

/** The JsonParser class helps parse the json file candy.json to get all the different candies. */
class JsonParser {
  val candies: Hashtable<String, Candy> = Hashtable()

  fun parse() {
    val inputStream = this.javaClass.classLoader.getResourceAsStream("candy.json")
    val reader = InputStreamReader(inputStream!!)
    val json = com.google.gson.JsonParser.parseReader(reader) as JsonObject
    val array = json.get("candies") as JsonArray
    for (item in array) {
      val candy = item as JsonObject
      val name = candy.get("name").asString
      val parentName = candy.get("parent").asString
      val t = candy.get("type").asString
      val type = if (t == "rewardFruit") Candy.Type.REWARD_FRUIT else Candy.Type.CRUSHABLE_CANDY
      val points = candy.get("points").asInt
      val c = Candy(name, parentName, type, points)
      candies[name] = c
    }
    setParentAndPoints()
  }

  fun setParentAndPoints() {
    for (key in candies.keys()) {
      val c = candies[key]!!
      c.parent = if (c.parentName == "null") null else candies[c.parentName]
      if (c.points == 0 && c.parent != null) {
        c.points = c.parent!!.points
      }
    }
  }
}
