---
title: Arrange/Act/Assert
category: Idiom
language: hi
tag:
 - Testing
---

## दूसरा नाम

दिया/कब/तब

## हेतु

अरेंज/एक्ट/एसर्ट (एएए) यूनिट परीक्षणों के आयोजन के लिए एक पैटर्न है।
यह परीक्षणों को तीन स्पष्ट और विशिष्ट चरणों में विभाजित करता है:

1. व्यवस्थित करें: परीक्षण के लिए आवश्यक सेटअप और आरंभीकरण करें।
2. अधिनियम: परीक्षण के लिए आवश्यक कार्रवाई करें।
3. दावा: परीक्षण के परिणाम सत्यापित करें।

## व्याख्या

इस पैटर्न के कई महत्वपूर्ण लाभ हैं. यह एक परीक्षण के बीच स्पष्ट अलगाव पैदा करता है
सेटअप, संचालन और परिणाम। यह संरचना कोड को पढ़ने और समझने में आसान बनाती है। अगर
आप चरणों को क्रम में रखते हैं और उन्हें अलग करने के लिए अपना कोड प्रारूपित करते हैं, आप एक परीक्षण स्कैन कर सकते हैं और
जल्दी से समझें कि यह क्या करता है।

जब आप अपनी परीक्षाएँ लिखते हैं तो यह कुछ हद तक अनुशासन भी लागू करता है। आपको सोचना होगा
आपके परीक्षण द्वारा निष्पादित किए जाने वाले तीन चरणों के बारे में स्पष्ट रूप से बताएं। यह परीक्षणों को लिखने के लिए अधिक स्वाभाविक बनाता है
उसी समय, चूँकि आपके पास पहले से ही एक रूपरेखा है।

वास्तविक दुनिया का उदाहरण

> हमें एक कक्षा के लिए व्यापक और स्पष्ट यूनिट टेस्ट सूट लिखने की जरूरत है।

साफ़ शब्दों में

> अरेंज/एक्ट/एसर्ट एक परीक्षण पैटर्न है जो परीक्षणों को आसान बनाने के लिए तीन स्पष्ट चरणों में व्यवस्थित करता है
> रखरखाव।

विकीविकीवेब कहता है

> अरेंज/एक्ट/एसर्ट यूनिटटेस्ट विधियों में कोड को व्यवस्थित करने और फ़ॉर्मेट करने का एक पैटर्न है।

**प्रोग्रामेटिक उदाहरण**

आइए सबसे पहले इकाई परीक्षण के लिए अपने `Cash` वर्ग का परिचय दें।

```java
public class Cash {

  private int amount;

  Cash(int amount) {
    this.amount = amount;
  }

  void plus(int addend) {
    amount += addend;
  }

  boolean minus(int subtrahend) {
    if (amount >= subtrahend) {
      amount -= subtrahend;
      return true;
    } else {
      return false;
    }
  }

  int count() {
    return amount;
  }
}
```

फिर हम अपने यूनिट परीक्षण को अरेंज/एक्ट/एसर्ट पैटर्न के अनुसार लिखते हैं। स्पष्ट रूप से ध्यान दें
प्रत्येक इकाई परीक्षण के लिए अलग-अलग चरण।

```java
class CashAAATest {

  @Test
  void testPlus() {
    //Arrange
    var cash = new Cash(3);
    //Act
    cash.plus(4);
    //Assert
    assertEquals(7, cash.count());
  }

  @Test
  void testMinus() {
    //Arrange
    var cash = new Cash(8);
    //Act
    var result = cash.minus(5);
    //Assert
    assertTrue(result);
    assertEquals(3, cash.count());
  }

  @Test
  void testInsufficientMinus() {
    //Arrange
    var cash = new Cash(1);
    //Act
    var result = cash.minus(6);
    //Assert
    assertFalse(result);
    assertEquals(1, cash.count());
  }

  @Test
  void testUpdate() {
    //Arrange
    var cash = new Cash(5);
    //Act
    cash.plus(6);
    var result = cash.minus(3);
    //Assert
    assertTrue(result);
    assertEquals(8, cash.count());
  }
}
```

## प्रयोज्यता

जब अरेंज/एक्ट/एसर्ट पैटर्न का उपयोग करें

* आपको अपने यूनिट परीक्षणों की संरचना करने की आवश्यकता है ताकि उन्हें पढ़ना, बनाए रखना और बढ़ाना आसान हो।

## श्रेय

* [Arrange, Act, Assert: What is AAA Testing?](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx)
* [Bill Wake: 3A – Arrange, Act, Assert](https://xp123.com/articles/3a-arrange-act-assert/)
* [Martin Fowler: GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html)
* [xUnit Test Patterns: Refactoring Test Code](https://www.amazon.com/gp/product/0131495054/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0131495054&linkId=99701e8f4af2f7e8dd50d720c9b63dbf)
* [Unit Testing Principles, Practices, and Patterns](https://www.amazon.com/gp/product/1617296279/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617296279&linkId=74c75cf22a63c3e4758ae08aa0a0cc35)
* [Test Driven Development: By Example](https://www.amazon.com/gp/product/0321146530/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321146530&linkId=5c63a93d8c1175b84ca5087472ef0e05)
