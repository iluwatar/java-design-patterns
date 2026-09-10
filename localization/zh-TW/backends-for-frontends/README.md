---
title: "Java 前端專用後端模式：依照用戶端需求打造 API"
shortTitle: 前端專用後端
description: "了解 Java 的前端專用後端（BFF）設計模式，讓每種用戶端擁有專屬後端服務。"
category: Architectural
language: zh-TW
tag:
  - API design
  - Architecture
  - Client-server
  - Decoupling
  - Microservices
---

## 又稱為

* Backend For Frontend
* BFF 模式

## 前端專用後端模式的目的

為每個用戶端應用程式（行動裝置、桌面程式、聊天機器人等）提供專屬後端服務，讓每個用戶端都能取得完全符合自身需求的 API，而不是讓所有用戶端共用一個通用後端。

## 前端專用後端模式的詳細說明與真實世界範例

真實世界範例

> 想像一家零售公司有行動 App、桌面後台工具與客服聊天機器人。它們都需要客戶、購物車、訂單與供應商資料，但手機畫面只需要簡短摘要，桌面工具卻需要完整的訂單與庫存細節。公司不提供一個讓所有用戶端自行篩選或過度取得資料的共用 API，而是為行動用戶端與內部網路用戶端各建立一個小型 BFF。每個 BFF 只呼叫用戶端需要的下游微服務，並回傳符合該用戶端的資料內容。

簡單來說

> 讓每種類型的用戶端都有量身打造的後端，而不是強迫所有用戶端使用一個大小通吃的 API。

廣泛推廣此模式的 Sam Newman 表示：

> 建立供特定前端應用程式或介面使用的獨立後端服務。

## 架構圖

```
node mobile{
 component iosapp as "ios app"
 component androidapp as "android app"
}
node intranet{
 component desktop as "desktop app"
 component chatbot
}
component bff as "BFF server"{
 component iosbff as "ios BFF"
 component androidbff as "android BFF"
 component chatbotbff as "chatbot BFF"
 component desktopbff as "desktop BFF"
}
node intranetserv as "intranet services server"{
 component ss as "supplier service API"
}
cloud onlypublic as "public cloud"{
 component cas as "customer authentication service API"
 component cs as "cart service API"
}
cloud cloudserv as "managed cloud"{
 component os as "order service API"
}
iosapp -- iosbff
androidapp -- androidbff
chatbot -- chatbotbff
desktop -- desktopbff
iosbff -- cas
androidbff -- cas
iosbff -- cs
androidbff -- cs
iosbff -- os
androidbff -- os
chatbotbff -- os
desktopbff -- os
chatbotbff -- ss
desktopbff -- ss
```

為了讓示範保持聚焦，本範例以兩個面向用戶端的 BFF 取代圖中的四個：代表 iOS/Android BFF 的 **Mobile BFF**，以及代表桌面程式/聊天機器人 BFF 的 **Desktop BFF**。兩者都呼叫共用的 `AuthService` 與 `OrderService`；`CartService` 只有 Mobile BFF 使用，`SupplierService` 只有 Desktop BFF 可以存取，與圖中的扇出關係一致。

## 類別圖

![前端專用後端類別圖](./etc/backends-for-frontends.png)

## 何時使用前端專用後端模式

* 不同用戶端需要同一份底層資料的不同形狀、粒度或聚合方式。
* 共用 API 已累積大量用戶端專屬的條件分支、選擇性欄位或查詢參數。
* 不同用戶端團隊需要獨立演進 API，不希望每次變更都協調同一個後端團隊。
* 某些用戶端（例如行動裝置）需要大幅縮減資料量以降低頻寬與延遲，而其他用戶端需要更豐富的資料。

## 優點與取捨

優點：

* 每個用戶端都取得針對需求最佳化的 API，讓用戶端更簡單、效能更好。
* 用戶端團隊可以獨立演進自己的 BFF，減少跨團隊協調。
* 下游微服務維持通用且可重複使用，用戶端專屬邏輯集中在 BFF 層。

取捨：

* 需要建立、部署與維運更多服務。
* 若沒有妥善抽取，共用邏輯可能在不同 BFF 間重複。
* 用戶端與下游服務之間多了一次網路跳轉。

## 如何在 Java 中實作前端專用後端模式

1. 找出需要不同資料形狀的用戶端類型。
2. 定義每個用戶端真正依賴的下游服務，例如 `AuthService`、`CartService`、`OrderService` 與 `SupplierService`。
3. 為每種用戶端建立一個 BFF，實作共用的 `ClientBff<T>` 契約，且只依賴該用戶端需要的下游服務。
4. 讓每個 BFF 將下游資料聚合並重新整理成用戶端專用的回應 DTO，例如 `MobileDashboardResponse`、`DesktopDashboardResponse`。
5. 讓用戶端應用程式呼叫自己的 BFF，而不是直接呼叫下游服務。

## 原始碼

* [模式：前端專用後端（Sam Newman）](https://samnewman.io/patterns/architectural/bff/)
* [Microservices Patterns: With examples in Java（Chris Richardson）](https://www.amazon.com/Microservices-Patterns-examples-Chris-Richardson/dp/1617294543)

## 參考資料與致謝

* [Building Microservices](https://www.oreilly.com/library/view/building-microservices-2nd/9781492034018/)
* [Backend for frontend（microservices.io）](https://microservices.io/patterns/apigateway.html)