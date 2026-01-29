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
// ABOUTME: Data transfer object representing a cake with topping and layers.
// ABOUTME: Includes total calories calculation from all components.
package dto

/** DTO for cakes. */
class CakeInfo(
    @JvmField val id: Long?,
    @JvmField val cakeToppingInfo: CakeToppingInfo?,
    @JvmField val cakeLayerInfos: List<CakeLayerInfo>,
) {
    /** Constructor without id. */
    constructor(
        cakeToppingInfo: CakeToppingInfo,
        cakeLayerInfos: List<CakeLayerInfo>,
    ) : this(null, cakeToppingInfo, cakeLayerInfos)

    /** Calculate calories. */
    fun calculateTotalCalories(): Int {
        val toppingCalories = cakeToppingInfo?.calories ?: 0
        return toppingCalories + cakeLayerInfos.sumOf { it.calories }
    }

    override fun toString(): String =
        "CakeInfo id=$id topping=$cakeToppingInfo layers=$cakeLayerInfos totalCalories=${calculateTotalCalories()}"
}
