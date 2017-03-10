/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
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
import java.util.stream.Collectors;

/**
 * @param <T> DTO representation's type
 * @param <U> Domain representation's type
 */
public class Converter<T, U> {

  private final Function<T, U> fromDto;
  private final Function<U, T> fromEntity;

  /**
   * @param fromDto Function that converts given dto entity into the domain entity.
   * @param fromEntity Function that converts given domain entity into the dto entity.
   */
  public Converter(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
    this.fromDto = fromDto;
    this.fromEntity = fromEntity;
  }

  /**
   * @param arg DTO entity
   * @return The domain representation - the result of the converting function application on dto entity.
   */
  public U convertFromDto(final T arg) {
    return fromDto.apply(arg);
  }

  /**
   * @param arg domain entity
   * @return The DTO representation - the result of the converting function application on domain entity.
   */
  public T convertFromEntity(final U arg) {
    return fromEntity.apply(arg);
  }

  /**
   * @param arg collection of DTO entities
   * @return List of domain representation of provided entities retrieved by
   *        mapping each of them with the convertion function
   */
  public List<U> createFromDtos(final Collection<T> arg) {
    return arg.stream().map(this::convertFromDto).collect(Collectors.toList());
  }

  /**
   * @param arg collection of domain entities
   * @return List of domain representation of provided entities retrieved by
   *        mapping each of them with the convertion function
   */
  public List<T> createFromEntities(final Collection<U> arg) {
    return arg.stream().map(this::convertFromEntity).collect(Collectors.toList());
  }

}
