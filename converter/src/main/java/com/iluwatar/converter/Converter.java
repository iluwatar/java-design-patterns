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
package com.iluwatar.converter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;

/**
 * Generic converter, thanks to Java8 features not only provides a way of generic bidirectional
 * conversion between corresponding types, but also a common way of converting a collection of
 * objects of the same type, reducing boilerplate code to the absolute minimum.
 *
 * @param <T> DTO representation's type
 * @param <U> Domain representation's type
 */
@RequiredArgsConstructor
public class Converter<T, U> {

  private final Function<T, U> fromDto;
  private final Function<U, T> fromEntity;

  /**
   * Converts DTO to Entity.
   *
   * @param dto DTO entity
   * @return The domain representation - the result of the converting function application on dto
   *     entity.
   */
  public final U convertFromDto(final T dto) {
    return fromDto.apply(dto);
  }

  /**
   * Converts Entity to DTO.
   *
   * @param entity domain entity
   * @return The DTO representation - the result of the converting function application on domain
   *     entity.
   */
  public final T convertFromEntity(final U entity) {
    return fromEntity.apply(entity);
  }

  /**
   * Converts list of DTOs to list of Entities.
   *
   * @param dtos collection of DTO entities
   * @return List of domain representation of provided entities retrieved by mapping each of them
   *     with the conversion function
   */
  public final List<U> createFromDtos(final Collection<T> dtos) {
    return dtos.stream().map(this::convertFromDto).toList();
  }

  /**
   * Converts list of Entities to list of DTOs.
   *
   * @param entities collection of domain entities
   * @return List of domain representation of provided entities retrieved by mapping each of them
   *     with the conversion function
   */
  public final List<T> createFromEntities(final Collection<U> entities) {
    return entities.stream().map(this::convertFromEntity).toList();
  }

}
