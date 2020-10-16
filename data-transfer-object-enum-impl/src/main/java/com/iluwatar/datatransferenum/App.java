package com.iluwatar.datatransferenum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Data Transfer Object pattern is a design pattern in which an data transfer object is used to
 * serve related information together to avoid multiple call for each piece of information.
 *
 * <p>In this example, ({@link App}) as as product details consumer i.e. client to
 * request for product details to server.
 *
 * <p>productResource ({@link ProductResource}) act as server to serve product information. And
 * The productDto ({@link ProductDto} is data transfer object to share product information.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Method as act client and request to server for details.
   *
   * @param args program argument.
   */
  public static void main(String[] args) {
    Product tv =
        new Product().setId(1L).setName("TV").setSupplier("Sony").setPrice(1000D).setCost(1090D);
    Product microwave =
        new Product().setId(2L).setName("microwave").setSupplier("Delonghi").setPrice(1000D)
            .setCost(1090D);
    Product refrigerator =
        new Product().setId(3L).setName("refrigerator").setSupplier("Botsch").setPrice(1000D)
            .setCost(1090D);
    Product airConditioner =
        new Product().setId(4L).setName("airConditioner").setSupplier("LG").setPrice(1000D)
            .setCost(1090D);
    List<Product> products =
        new ArrayList<>(Arrays.asList(tv, microwave, refrigerator, airConditioner));
    ProductResource productResource = new ProductResource(products);

    LOGGER.info("####### List of products including sensitive data just for admins: \n {}",
        Arrays.toString(productResource.getAllProductsForAdmin().toArray()));
    LOGGER.info("####### List of products for customers: \n {}",
        Arrays.toString(productResource.getAllProductsForCustomer().toArray()));

    LOGGER.info("####### Going to save Sony PS5 ...");
    ProductDto.Request.Create createProductRequestDto = new ProductDto.Request.Create()
        .setName("PS5")
        .setCost(1000D)
        .setPrice(1220D)
        .setSupplier("Sony");
    productResource.save(createProductRequestDto);
    LOGGER.info("####### List of products after adding PS5: {}",
        Arrays.toString(productResource.getProducts().toArray()));
  }
}
