package com.iluwatar.datatransferenum;

/**
 * {@link ProductDto} is a data transfer object POJO.
 * Instead of sending individual information to
 * client We can send related information together in POJO.
 *
 * <p>Dto will not have any business logic in it.
 */
public enum ProductDto {
  ;

  public enum Request {
    ;

    public static class Create implements Name, Price, Cost, Supplier {
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

  public enum Response {
    NONE;

    public static class Public implements Id, Name, Price {
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

    public static class Private implements Id, Name, Price, Cost {
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

  private interface Id {
    /**
     * Unique identifier of the product.
     *
     * @return : id of the product.
     */
    Long getId();
  }

  private interface Name {
    /**
     * The name of the product.
     *
     * @return : name of the product.
     */
    String getName();
  }

  private interface Price {
    /**
     * The amount we sell a product for.
     * <b>This data is not confidential</b>
     *
     * @return : price of the product.
     */
    Double getPrice();
  }

  private interface Cost {
    /**
     * The amount that it costs us to purchase this product
     * For the amount we sell a product for, see the {@link Price Price} parameter.
     * <b>This data is confidential</b>
     */
    Double getCost();
  }

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
