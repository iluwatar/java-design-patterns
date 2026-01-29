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

// ABOUTME: Generic bidirectional converter between DTO and domain entity types.
// ABOUTME: Provides single-object and collection conversion using function parameters.
package com.iluwatar.converter

/**
 * Generic converter, thanks to Kotlin features not only provides a way of generic bidirectional
 * conversion between corresponding types, but also a common way of converting a collection of
 * objects of the same type, reducing boilerplate code to the absolute minimum.
 *
 * @param T DTO representation's type
 * @param U Domain representation's type
 */
open class Converter<T, U>(
    private val fromDto: (T) -> U,
    private val fromEntity: (U) -> T,
) {

    /**
     * Converts DTO to Entity.
     *
     * @param dto DTO entity
     * @return The domain representation - the result of the converting function application on dto
     *     entity.
     */
    fun convertFromDto(dto: T): U = fromDto(dto)

    /**
     * Converts Entity to DTO.
     *
     * @param entity domain entity
     * @return The DTO representation - the result of the converting function application on domain
     *     entity.
     */
    fun convertFromEntity(entity: U): T = fromEntity(entity)

    /**
     * Converts list of DTOs to list of Entities.
     *
     * @param dtos collection of DTO entities
     * @return List of domain representation of provided entities retrieved by mapping each of them
     *     with the conversion function
     */
    fun createFromDtos(dtos: Collection<T>): List<U> = dtos.map(::convertFromDto)

    /**
     * Converts list of Entities to list of DTOs.
     *
     * @param entities collection of domain entities
     * @return List of domain representation of provided entities retrieved by mapping each of them
     *     with the conversion function
     */
    fun createFromEntities(entities: Collection<U>): List<T> = entities.map(::convertFromEntity)
}
