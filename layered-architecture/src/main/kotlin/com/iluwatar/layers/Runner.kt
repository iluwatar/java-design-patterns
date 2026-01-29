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
// ABOUTME: Entry point for the layered architecture application.
// ABOUTME: Initializes sample data and renders cakes using the CakeViewImpl.
package com.iluwatar.layers

import dto.CakeInfo
import dto.CakeLayerInfo
import dto.CakeToppingInfo
import exception.CakeBakingException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import service.CakeBakingService
import view.CakeViewImpl

private val logger = KotlinLogging.logger {}

/**
 * The Runner class is the entry point of the application. It implements CommandLineRunner, which
 * means it will execute the run method after the application context is loaded.
 *
 * The Runner class is responsible for initializing the cake baking service with sample data and
 * creating a view to render the cakes. It uses the CakeBakingService to save new layers and
 * toppings and to bake new cakes. It also handles exceptions that might occur during the cake
 * baking process.
 */
@EntityScan(basePackages = ["entity"])
@ComponentScan(basePackages = ["com.iluwatar.layers", "service", "dto", "exception", "view", "dao"])
@Component
class Runner(
    private val cakeBakingService: CakeBakingService,
) : CommandLineRunner {
    override fun run(vararg args: String) {
        // initialize sample data
        initializeData()
        // create view and render it
        val cakeView = CakeViewImpl(cakeBakingService)
        cakeView.render()
    }

    /** Initializes the example data. */
    private fun initializeData() {
        cakeBakingService.saveNewLayer(CakeLayerInfo("chocolate", 1200))
        cakeBakingService.saveNewLayer(CakeLayerInfo("banana", 900))
        cakeBakingService.saveNewLayer(CakeLayerInfo(STRAWBERRY, 950))
        cakeBakingService.saveNewLayer(CakeLayerInfo("lemon", 950))
        cakeBakingService.saveNewLayer(CakeLayerInfo("vanilla", 950))
        cakeBakingService.saveNewLayer(CakeLayerInfo(STRAWBERRY, 950))

        cakeBakingService.saveNewTopping(CakeToppingInfo("candies", 350))
        cakeBakingService.saveNewTopping(CakeToppingInfo("cherry", 350))

        val cake1 =
            CakeInfo(
                CakeToppingInfo("candies", 0),
                listOf(
                    CakeLayerInfo("chocolate", 0),
                    CakeLayerInfo("banana", 0),
                    CakeLayerInfo(STRAWBERRY, 0),
                ),
            )
        try {
            cakeBakingService.bakeNewCake(cake1)
        } catch (e: CakeBakingException) {
            logger.error(e) { "Cake baking exception" }
        }
        val cake2 =
            CakeInfo(
                CakeToppingInfo("cherry", 0),
                listOf(
                    CakeLayerInfo("vanilla", 0),
                    CakeLayerInfo("lemon", 0),
                    CakeLayerInfo(STRAWBERRY, 0),
                ),
            )
        try {
            cakeBakingService.bakeNewCake(cake2)
        } catch (e: CakeBakingException) {
            logger.error(e) { "Cake baking exception" }
        }
    }

    companion object {
        const val STRAWBERRY = "strawberry"

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Runner::class.java, *args)
        }
    }
}
