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

// ABOUTME: A pool that reuses crushed candy cells instead of creating new ones each time.
// ABOUTME: Uses a randomCode array of candy types loaded from JSON to assign candies to recycled cells.

import com.google.gson.JsonParseException
import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

/**
 * The CellPool class allows the reuse of crushed cells instead of creation of new cells each time.
 * The reused cell is given a new candy to hold using the randomCode field which holds all the
 * candies available.
 */
class CellPool(num: Int) {
  val pool: MutableList<Cell> = ArrayList(num)
  var pointer: Int
  var randomCode: Array<Candy>

  init {
    randomCode = try {
      assignRandomCandyTypes()
    } catch (e: Exception) {
      logger.error(e) { "Error occurred: " }
      // manually initialising this.randomCode
      arrayOf(
        Candy("cherry", FRUIT, Candy.Type.REWARD_FRUIT, 20),
        Candy("mango", FRUIT, Candy.Type.REWARD_FRUIT, 20),
        Candy("purple popsicle", CANDY, Candy.Type.CRUSHABLE_CANDY, 10),
        Candy("green jellybean", CANDY, Candy.Type.CRUSHABLE_CANDY, 10),
        Candy("orange gum", CANDY, Candy.Type.CRUSHABLE_CANDY, 10)
      )
    }
    for (i in 0 until num) {
      val c = Cell()
      c.candy = randomCode[RANDOM.nextInt(randomCode.size)]
      pool.add(c)
    }
    pointer = num - 1
  }

  fun getNewCell(): Cell {
    val newCell = pool.removeAt(pointer)
    pointer--
    return newCell
  }

  fun addNewCell(c: Cell) {
    c.candy = randomCode[RANDOM.nextInt(randomCode.size)] // changing candytype to new
    pool.add(c)
    pointer++
  }

  @Throws(JsonParseException::class)
  fun assignRandomCandyTypes(): Array<Candy> {
    val jp = JsonParser()
    jp.parse()
    val code = ArrayList<Candy>()
    for (key in jp.candies.keys()) {
      if (key != FRUIT && key != CANDY) {
        // not generic
        code.add(jp.candies[key]!!)
      }
    }
    return code.toTypedArray()
  }

  companion object {
    private val RANDOM = SecureRandom()
    const val FRUIT = "fruit"
    const val CANDY = "candy"
  }
}
