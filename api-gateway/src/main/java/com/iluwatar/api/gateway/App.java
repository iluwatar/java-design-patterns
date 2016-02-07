package com.iluwatar.api.gateway;

/**
 * With the Microservices pattern, a client may need data from multiple different microservices.
 * If the client called each microservice directly, that could contribute to longer load times,
 * since the client would have to make a network request for each microservice called. Moreover,
 * having the client call each microservice directly ties the client to that microservice - if the
 * internal implementations of the microservices change (for example, if two microservices are
 * combined sometime in the future) or if the location (host and port) of a microservice changes,
 * then every client that makes use of those microservices must be updated.
 *
 * <p>
 * The intent of the API Gateway pattern is to alleviate some of these issues. In the API Gateway
 * pattern, an additional entity (the API Gateway) is placed between the client and the
 * microservices. The job of the API Gateway is to aggregate the calls to the microservices.
 * Rather than the client calling each microservice individually, the client calls the API Gateway
 * a single time. The API Gateway then calls each of the microservices that the client needs.
 *
 * <p>
 * This implementation shows what the API Gateway pattern could look like for an e-commerce site.
 * In this case, the (@link ImageService) and (@link PriceService) represent our microservices.
 * Customers viewing the site on a desktop device can see both price information and an image of
 * a product, so the (@link ApiGateway) calls both of the microservices and aggregates the data in
 * the (@link DesktopProduct) model. However, mobile users only see price information; they do not
 * see a product image. For mobile users, the (@link ApiGateway) only retrieves price information,
 * which it uses to populate the (@link MobileProduct).
 */
public class App {

  /**
   * Program entry point
   *
   * @param args
   *          command line args
   */
  public static void main(String[] args) {
    ApiGateway apiGateway = new ApiGateway();

    DesktopProduct desktopProduct = apiGateway.getProductDesktop();
    System.out.println(String.format("Desktop Product \nPrice: %s\nImage path: %s",
        desktopProduct.getPrice(), desktopProduct.getImagePath()));

    MobileProduct mobileProduct = apiGateway.getProductMobile();
    System.out.println(String.format("Mobile Product \nPrice: %s", mobileProduct.getPrice()));
  }
}
