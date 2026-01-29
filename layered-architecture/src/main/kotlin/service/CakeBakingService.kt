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
// ABOUTME: Service interface defining cake baking operations.
// ABOUTME: Provides methods for managing cakes, layers, and toppings.
package service

import dto.CakeInfo
import dto.CakeLayerInfo
import dto.CakeToppingInfo
import exception.CakeBakingException
import org.springframework.stereotype.Service

/** Service for cake baking operations. */
@Service
interface CakeBakingService {
    /** Bakes new cake according to parameters. */
    @Throws(CakeBakingException::class)
    fun bakeNewCake(cakeInfo: CakeInfo)

    /** Get all cakes. */
    fun getAllCakes(): List<CakeInfo>

    /** Store new cake topping. */
    fun saveNewTopping(toppingInfo: CakeToppingInfo)

    /** Get available cake toppings. */
    fun getAvailableToppings(): List<CakeToppingInfo>

    /** Add new cake layer. */
    fun saveNewLayer(layerInfo: CakeLayerInfo)

    /** Get available cake layers. */
    fun getAvailableLayers(): List<CakeLayerInfo>

    fun deleteAllCakes()

    fun deleteAllLayers()

    fun deleteAllToppings()
}
