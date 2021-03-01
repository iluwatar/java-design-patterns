/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.datatransfer.product;

/**
 * {@link ProductDto} is a data transfer object POJO.
 * Instead of sending individual information to
 * client We can send related information together in POJO.
 *
 * <p>Dto will not have any business logic in it.
 */
public enum ProductDto {
  ;

  /**
   * This is Request class which consist of Create or any other request DTO's
   * you might want to use in your API.
   */
  public enum Request {
    ;

    /**
     * This is Create dto class for requesting create new product.
     */
    public static final class Create implements Name, Price, Cost, Supplier {
      private String name;
      private Double price;
      private Double cost;
      private String supplier;

      @Override
      public String getName() {
        return name;
      }

      public Create setName(String name) {
        this.name = name;
        return this;
      }

      @Override
      public Double getPrice() {
        return price;
      }

      public Create setPrice(Double price) {
        this.price = price;
        return this;
      }

      @Override
      public Double getCost() {
        return cost;
      }

      public Create setCost(Double cost) {
        this.cost = cost;
        return this;
      }

      @Override
      public String getSupplier() {
        return supplier;
      }

      public Create setSupplier(String supplier) {
        this.supplier = supplier;
        return this;
      }
    }
  }

  /**
   * This is Response class which consist of any response DTO's
   * you might want to provide to your clients.
   */
  public enum Response {
    ;

    /**
     * This is Public dto class for API response with the lowest data security.
     */
    public static final class Public implements Id, Name, Price {
      private Long id;
      private String name;
      private Double price;

      @Override
      public Long getId() {
        return id;
      }

      public Public setId(Long id) {
        this.id = id;
        return this;
      }

      @Override
      public String getName() {
        return name;
      }

      public Public setName(String name) {
        this.name = name;
        return this;
      }

      @Override
      public Double getPrice() {
        return price;
      }

      public Public setPrice(Double price) {
        this.price = price;
        return this;
      }

      @Override
      public String toString() {
        return "Public{"
            + "id="
            + id
            + ", name='"
            + name
            + '\''
            + ", price="
            + price
            + '}';
      }
    }

    /**
     * This is Private dto class for API response with the highest data security.
     */
    public static final class Private implements Id, Name, Price, Cost {
      private Long id;
      private String name;
      private Double price;
      private Double cost;

      @Override
      public Long getId() {
        return id;
      }

      public Private setId(Long id) {
        this.id = id;
        return this;
      }

      @Override
      public String getName() {
        return name;
      }

      public Private setName(String name) {
        this.name = name;
        return this;
      }

      @Override
      public Double getPrice() {
        return price;
      }

      public Private setPrice(Double price) {
        this.price = price;
        return this;
      }

      @Override
      public Double getCost() {
        return cost;
      }

      public Private setCost(Double cost) {
        this.cost = cost;
        return this;
      }

      @Override
      public String toString() {
        return "Private{"
            +
            "id="
            + id
            +
            ", name='"
            + name
            + '\''
            +
            ", price="
            + price
            +
            ", cost="
            + cost
            +
            '}';
      }
    }
  }

  /**
   * Use this interface whenever you want to provide the product Id in your DTO.
   */
  private interface Id {
    /**
     * Unique identifier of the product.
     *
     * @return : id of the product.
     */
    Long getId();
  }

  /**
   * Use this interface whenever you want to provide the product Name in your DTO.
   */
  private interface Name {
    /**
     * The name of the product.
     *
     * @return : name of the product.
     */
    String getName();
  }

  /**
   * Use this interface whenever you want to provide the product Price in your DTO.
   */
  private interface Price {
    /**
     * The amount we sell a product for.
     * <b>This data is not confidential</b>
     *
     * @return : price of the product.
     */
    Double getPrice();
  }

  /**
   * Use this interface whenever you want to provide the product Cost in your DTO.
   */
  private interface Cost {
    /**
     * The amount that it costs us to purchase this product
     * For the amount we sell a product for, see the {@link Price Price} parameter.
     * <b>This data is confidential</b>
     *
     * @return : cost of the product.
     */
    Double getCost();
  }

  /**
   * Use this interface whenever you want to provide the product Supplier in your DTO.
   */
  private interface Supplier {
    /**
     * The name of supplier of the product or its manufacturer.
     * <b>This data is highly confidential</b>
     *
     * @return : supplier of the product.
     */
    String getSupplier();
  }
}
