---
title: "整潔架構：可維護的軟體架構風格"
shortTitle: 整潔架構
description: "透過 Java 的真實世界範例、程式碼片段與類別圖了解整潔架構風格。"
category: Architectural
language: zh-TW
tag:
    - Architecture
    - Decoupling
    - Domain
    - Inversion of control
    - Layered architecture
    - Modularity
    - Testing
---

## 整潔架構的目的

組織系統，使核心商業邏輯不依賴外部關注點與框架。

## 整潔架構的詳細說明與真實世界範例

真實世界範例

> 想像一家大型披薩連鎖店有網站、行動 App、電話與店內自助機等訂購管道。核心的披薩領域邏輯（計算價格、準備訂單、管理忠誠度點數）完全與使用者介面和儲存機制分離。因此，連鎖店可以新增聊天機器人或更換資料庫，而不必修改基本訂購規則。

簡單來說

> 整潔架構透過嚴格分層與清楚邊界，將核心商業邏輯與資料庫、框架或 UI 等外部關注點隔離，確保單一層的變更不會波及整個系統。

維基百科指出：

> Robert C. Martin 於 2012 年提出的整潔架構結合六角形架構、洋蔥架構及其他變體的原則。它以同心環呈現不同元件層級，將 UI、資料庫、外部系統與裝置等介面卡放在外環，將使用案例與實體留在內環。它使用相依性反轉原則，嚴格規定相依性只能由外環指向內環，不能反向存在。

心智圖

![整潔架構心智圖](./etc/clean-architecture-mind-map.png)

流程圖

![整潔架構流程圖](./etc/clean-architecture-flowchart.png)

## Java 整潔架構的程式範例

首先定義核心領域實體：`Product`、`Order` 與 `Cart`。這些類別包含基本的商業邏輯與狀態。

```java
public class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

public class Cart {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

public class Order {
    private String orderId;
    private List<CartItem> items;
    private double totalPrice;

    public Order(String orderId, List<CartItem> items) {
        this.orderId = orderId;
        this.items = items;
        this.totalPrice = items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}
```

建立儲存庫介面來抽象每個領域物件的資料操作，這樣可以替換儲存或持久化機制，而不必修改較高層的邏輯。

```java
public interface CartRepository {
    void addItemToCart(String userId, Product product, int quantity);
    void removeItemFromCart(String userId, String productId);
    List<Cart> getItemsInCart(String userId);
    double calculateTotal(String userId);
    void clearCart(String userId);
}

public interface ProductRepository {
    Product getProductById(String productId);
}

public interface OrderRepository {
    void saveOrder(Order order);
}
```

記憶體內儲存庫使用簡單集合保存狀態，展示如何替換或擴充資料層，例如換成資料庫，而不影響領域邏輯。

```java
public class InMemoryCartRepository implements CartRepository {
  private final Map<String, List<Cart>> userCarts = new HashMap<>();

  @Override
  public void addItemToCart(String userId, Product product, int quantity) {
    List<Cart> cart = userCarts.getOrDefault(userId, new ArrayList<>());
    cart.add(new Cart(product, quantity));
    userCarts.put(userId, cart);
  }

  @Override
  public void removeItemFromCart(String userId, String productId) {
    List<Cart> cart = userCarts.get(userId);
    if (cart != null) cart.removeIf(item -> item.getProduct().getId().equals(productId));
  }

  @Override public List<Cart> getItemsInCart(String userId) {
    return userCarts.getOrDefault(userId, new ArrayList<>());
  }

  @Override public double calculateTotal(String userId) {
    return userCarts.getOrDefault(userId, new ArrayList<>()).stream()
        .mapToDouble(Cart::getTotalPrice).sum();
  }

  @Override public void clearCart(String userId) { userCarts.remove(userId); }
}

public class InMemoryOrderRepository implements OrderRepository {
    private final List<Order> orders = new ArrayList<>();
    @Override public void saveOrder(Order order) { orders.add(order); }
}

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> products = new HashMap<>();
    public InMemoryProductRepository() {
        products.put("1", new Product("1", "Laptop", 1000.0));
        products.put("2", new Product("2", "Smartphone", 500.0));
    }
    @Override public Product getProductById(String productId) { return products.get(productId); }
}
```

控制器透過使用案例或服務層 `ShoppingCartService` 協調結帳流程與購物車操作：

```java
public class OrderController {
  private final ShoppingCartService shoppingCartUseCase;
  public OrderController(ShoppingCartService useCase) { this.shoppingCartUseCase = useCase; }
  public Order checkout(String userId) { return shoppingCartUseCase.checkout(userId); }
}

public class CartController {
  private final ShoppingCartService shoppingCartUseCase;
  public CartController(ShoppingCartService useCase) { this.shoppingCartUseCase = useCase; }
  public void addItemToCart(String userId, String productId, int quantity) {
    shoppingCartUseCase.addItemToCart(userId, productId, quantity);
  }
  public void removeItemFromCart(String userId, String productId) {
    shoppingCartUseCase.removeItemFromCart(userId, productId);
  }
  public double calculateTotal(String userId) {
    return shoppingCartUseCase.calculateTotal(userId);
  }
}
```

整潔架構開始運作。在 `main` 中組合所有元件，模擬加入商品、計算總額與建立訂單的流程：

```java
public static void main(String[] args) {
    ProductRepository productRepository = new InMemoryProductRepository();
    CartRepository cartRepository = new InMemoryCartRepository();
    OrderRepository orderRepository = new InMemoryOrderRepository();
    ShoppingCartService service = new ShoppingCartService(
        productRepository, cartRepository, orderRepository);
    CartController cartController = new CartController(service);
    OrderController orderController = new OrderController(service);
    String userId = "user123";
    cartController.addItemToCart(userId, "1", 1);
    cartController.addItemToCart(userId, "2", 2);
    System.out.println("Total: $" + cartController.calculateTotal(userId));
    Order order = orderController.checkout(userId);
    System.out.println("Order placed! Order ID: " + order.getOrderId()
        + ", Total: $" + order.getTotalPrice());
}
```

輸出：

```md
Total: $2000.0
Order placed! Order ID: ORDER-1743349969254, Total: $2000.0
```

## 何時在 Java 中使用整潔架構

* 想讓商業規則獨立於 UI、資料庫及其他外部代理。
* 大型 Java 應用程式需要高度可維護性與可測試性。
* 想在應用程式層之間建立清楚邊界。

## 整潔架構的實際應用

* 金融與保險領域的大型企業系統。
* 強調解耦與模組化設計的微服務架構。
* 需要嚴格分離關注點、以領域為中心的 Java 系統。

## 整潔架構的優點與取捨

優點：

* 將核心邏輯與基礎設施細節隔離，提高可維護性。
* 透過領域模型周圍的清楚邊界提高可測試性。
* 可替換或升級外部元件而不影響核心邏輯。

取捨：

* 強制嚴格分層與邊界會增加初期複雜度。
* 對不需要嚴格分離的小型專案可能產生額外負擔。
* 團隊必須有紀律地遵守架構規則。

## 相關 Java 設計模式

* [相依性注入](https://java-design-patterns.com/patterns/dependency-injection/)：以注入相依性取代硬編碼，促進層間解耦。
* [分層架構](https://java-design-patterns.com/patterns/layered-architecture/)：兩者都分離不同層的關注點，但整潔架構更強調嚴格的相依性規則。
* [六角形架構](https://java-design-patterns.com/patterns/hexagonal-architecture/)：同樣著重以連接埠與介面卡隔離核心邏輯。

## 參考資料與致謝

* [Clean Architecture: A Craftsman's Guide to Software Structure and Design](https://amzn.to/3UoKkaR)
* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/3wRnjp5)
* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3wlDrze)