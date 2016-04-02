package com.iluwatar.image.microservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the Image microservice's endpoints
 */
@RestController
public class ImageController {

  /**
   * An endpoint for a user to retrieve an image path
   * @return An image path
   */
  @RequestMapping(value = "/image-path", method = RequestMethod.GET)
  public String getImagePath() {
    return "/product-image.png";
  }
}
