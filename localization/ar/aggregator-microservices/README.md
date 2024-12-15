---
title: Aggregator Microservices
shortTitle: Aggregator Microservices
category: Architectural
language: ar
tag:
- Cloud distributed
- Decoupling
- Microservices
---

## الهدف

يقوم المستخدم بإجراء مكالمة واحدة إلى خدمة المجمع، ومن ثم يقوم المجمع بالاتصال بكل خدمة ميكروسيرفيس ذات صلة.

## الشرح

مثال من العالم الواقعي

> يحتاج سوقنا الإلكتروني إلى معلومات حول المنتجات والمخزون الحالي. يقوم بإجراء مكالمة إلى خدمة المجمع التي بدورها تتصل بخدمة ميكروسيرفيس المعلومات الخاصة بالمنتج وخدمة ميكروسيرفيس المخزون للمنتج التي تعيد المعلومات مجمعة.

ببساطة

> يقوم ميكروسيرفيس المجمع بجمع البيانات من عدة ميكروسيرفيسات ويعيد النتيجة مجمعة لمعالجتها.

يقول StackOverflow

> يقوم ميكروسيرفيس المجمع بالاتصال بعدة خدمات لتحقيق الوظيفة المطلوبة من التطبيق.

**مثال برمجي**

لنبدأ بنموذج البيانات. هنا هو `Product`.


```java
public class Product {
  private String title;
  private int productInventories;
  // getters and setters ->
  ...
}
```

بعد ذلك، يمكننا تقديم ميكروسيرفيسنا `Aggregator` (مجمع الميكروسيرفيسات). يحتوي على `ProductInformationClient` (عميل معلومات المنتج) و
`ProductInventoryClient` (عميل مخزون المنتج) للاتصال بالميكروسيرفيسات المعنية.


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

هذه هي جوهر تنفيذ الميكروسيرفيسات للمعلومات. ميكروسيرفيس المخزون مشابه، ببساطة يعيد
عدد المخزون.


```java
@RestController
public class InformationController {
  @RequestMapping(value = "/information", method = RequestMethod.GET)
  public String getProductTitle() {
    return "The Product Title.";
  }
}
```

الآن عند استدعاء واجهة برمجة التطبيقات REST الخاصة بنا `Aggregator` تُرجع معلومات المنتج.


```bash
curl http://localhost:50004/product
{"title":"The Product Title.","productInventories":5}
```

## مخطط الفئة

![alt text](./aggregator-service/etc/aggregator-service.png "Aggregator Microservice")

## قابلية التطبيق

استخدم نمط تجميع الميكروسيرفيسات (Aggregator Microservices) عندما تحتاج إلى واجهة برمجة تطبيقات موحدة لعدة ميكروسيرفيسات، بغض النظر عن جهاز العميل.


## الشكر

* [أنماط تصميم الميكروسيرفيس](http://web.archive.org/web/20190705163602/http://blog.arungupta.me/microservice-design-patterns/)
* [أنماط الميكروسيرفيس: مع أمثلة في Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=8b4e570267bc5fb8b8189917b461dc60)
* [أنماط العمارة: اكتشاف الأنماط الأساسية في أكثر المجالات ضرورة في العمارة المؤسسية](https://www.amazon.com/gp/product/B077T7V8RC/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=B077T7V8RC&linkId=c34d204bfe1b277914b420189f09c1a4)
