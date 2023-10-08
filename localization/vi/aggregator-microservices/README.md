---
title: Microservices Tổng Hợp
category: Architectural
language: vi
tag:
- Cloud distributed
- Decoupling
- Microservices
---

## Mục Đích
Người dùng chỉ cần thực hiện một lần gọi duy nhất đến dịch vụ tổng hợp, sau đó dịch vụ tổng hợp sẽ gọi dịch vụ con tương ứng.

## Giải Thích

Ví dụ thực tế

> Trang web thị trường của chúng ta cần thông tin về sản phẩm và tồn kho hiện tại của chúng. Nó thực hiện gọi đến dịch vụ tổng hợp, sau đó dịch vụ tổng hợp gọi dịch vụ thông tin sản phẩm và dịch vụ tồn kho sản phẩm, cuối cùng trả lại thông tin kết hợp.

## Nói đơn giản hơn

> Microservices Tổng Hợp thu thập các phần dữ liệu từ các dịch vụ con khác nhau và trả về một tổng hợp để xử lý.

Theo Stack Overflow

> Microservices Tổng Hợp gọi nhiều dịch vụ để đạt được chức năng được yêu cầu bởi ứng dụng.

**Mã nguồn mẫu**

Hãy bắt đầu từ mô hình dữ liệu. Đây là lớp `Product` của chúng ta.

```java
public class Product {
  private String title;
  private int productInventories;
  // getters and setters ->
  ...
}
```

Tiếp theo, chúng ta có thể giới thiệu Microservices `Aggregator` của chúng ta. Nó chứa các khách hàng `ProductInformationClient` và
`ProductInventoryClient` để gọi các dịch vụ con tương ứng.

```java
@RestController
public class Aggregator {

  @Resource
  private ProductInformationClient informationClient;

  @Resource
  private ProductInventoryClient inventoryClient;

  @RequestMapping(path = "/product", method = RequestMethod.GET)
  public Product getProduct() {

    var product = new Product();
    var productTitle = informationClient.getProductTitle();
    var productInventory = inventoryClient.getProductInventories();

    //Fallback to error message
    product.setTitle(requireNonNullElse(productTitle, "Error: Fetching Product Title Failed"));

    //Fallback to default error inventory
    product.setProductInventories(requireNonNullElse(productInventory, -1));

    return product;
  }
}
```

Dưới đây là bản thể của việc triển khai dịch vụ thông tin sản phẩm. Dịch vụ tồn kho cũng tương tự như thế, nó chỉ trả về số lượng tồn kho.

```java
@RestController
public class InformationController {
  @RequestMapping(value = "/information", method = RequestMethod.GET)
  public String getProductTitle() {
    return "The Product Title.";
  }
}
```

Bây giờ, việc gọi `Aggregator` API REST của chúng ta sẽ trả về thông tin sản phẩm.

```bash
curl http://localhost:50004/product
{"title":"The Product Title.","productInventories":5}
```

## Sơ Đồ Lớp
![alt text](../../../aggregator-microservices/aggregator-service/etc/aggregator-service.png "Aggregator Microservice")


## Các Trường Hợp Sử Dụng
Sử dụng mẫu Microservices Tổng Hợp khi bạn cần một API chung cho các dịch vụ Microservices khác nhau, bất kể thiết bị của khách hàng.

## Người Đóng Góp

* [Microservice Design Patterns](http://web.archive.org/web/20190705163602/http://blog.arungupta.me/microservice-design-patterns/)
* [Microservices Patterns: With examples in Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=8b4e570267bc5fb8b8189917b461dc60)
* [Architectural Patterns: Uncover essential patterns in the most indispensable realm of enterprise architecture](https://www.amazon.com/gp/product/B077T7V8RC/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B077T7V8RC&linkId=c34d204bfe1b277914b420189f09c1a4)
