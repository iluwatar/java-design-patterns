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
// ABOUTME: Implementation of cake baking service with database persistence.
// ABOUTME: Handles baking cakes, managing layers and toppings via JPA repositories.
package service

import dao.CakeDao
import dao.CakeLayerDao
import dao.CakeToppingDao
import dto.CakeInfo
import dto.CakeLayerInfo
import dto.CakeToppingInfo
import entity.Cake
import entity.CakeLayer
import entity.CakeTopping
import exception.CakeBakingException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/** Implementation of CakeBakingService. */
@Service
@Transactional
open class CakeBakingServiceImpl(
    private val cakeDao: CakeDao,
    private val cakeLayerDao: CakeLayerDao,
    private val cakeToppingDao: CakeToppingDao,
) : CakeBakingService {
    @Throws(CakeBakingException::class)
    override fun bakeNewCake(cakeInfo: CakeInfo) {
        val allToppings = getAvailableToppingEntities()
        val matchingToppings =
            allToppings.filter { it.name == cakeInfo.cakeToppingInfo?.name }
        if (matchingToppings.isEmpty()) {
            throw CakeBakingException("Topping ${cakeInfo.cakeToppingInfo?.name} is not available")
        }
        val allLayers = getAvailableLayerEntities()
        val foundLayers = mutableSetOf<CakeLayer>()
        for (info in cakeInfo.cakeLayerInfos) {
            val found = allLayers.find { layer -> layer.name == info.name }
            if (found == null) {
                throw CakeBakingException("Layer ${info.name} is not available")
            } else {
                foundLayers.add(found)
            }
        }

        val toppingOptional = cakeToppingDao.findById(matchingToppings.first().id!!)
        if (toppingOptional.isPresent) {
            val topping = toppingOptional.get()
            val cake = Cake()
            cake.topping = topping
            cake.layers = foundLayers
            cakeDao.save(cake)
            topping.cake = cake
            cakeToppingDao.save(topping)
            // copy set to avoid a ConcurrentModificationException
            val foundLayersToUpdate = foundLayers.toSet()

            for (layer in foundLayersToUpdate) {
                layer.cake = cake
                cakeLayerDao.save(layer)
            }
        } else {
            throw CakeBakingException("Topping ${cakeInfo.cakeToppingInfo?.name} is not available")
        }
    }

    override fun saveNewTopping(toppingInfo: CakeToppingInfo) {
        cakeToppingDao.save(CakeTopping(toppingInfo.name, toppingInfo.calories))
    }

    override fun saveNewLayer(layerInfo: CakeLayerInfo) {
        cakeLayerDao.save(CakeLayer(layerInfo.name, layerInfo.calories))
    }

    private fun getAvailableToppingEntities(): List<CakeTopping> =
        cakeToppingDao.findAll().filter { it.cake == null }

    override fun getAvailableToppings(): List<CakeToppingInfo> =
        cakeToppingDao.findAll()
            .filter { it.cake == null }
            .map { CakeToppingInfo(it.id, it.name!!, it.calories) }

    private fun getAvailableLayerEntities(): List<CakeLayer> = cakeLayerDao.findAll().filter { it.cake == null }

    override fun getAvailableLayers(): List<CakeLayerInfo> =
        cakeLayerDao.findAll()
            .filter { it.cake == null }
            .map { CakeLayerInfo(it.id, it.name!!, it.calories) }

    override fun deleteAllCakes() {
        cakeDao.deleteAll()
    }

    override fun deleteAllLayers() {
        cakeLayerDao.deleteAll()
    }

    override fun deleteAllToppings() {
        cakeToppingDao.deleteAll()
    }

    override fun getAllCakes(): List<CakeInfo> =
        cakeDao.findAll().map { cake ->
            val cakeToppingInfo =
                CakeToppingInfo(
                    cake.topping!!.id,
                    cake.topping!!.name!!,
                    cake.topping!!.calories,
                )
            val cakeLayerInfos =
                cake.layers.map { layer ->
                    CakeLayerInfo(layer.id, layer.name!!, layer.calories)
                }
            CakeInfo(cake.id, cakeToppingInfo, cakeLayerInfos)
        }
}
