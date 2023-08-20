---
title: Collection Pipeline
category: Functional
language: hi
tag:
 - Reactive
---

## हेतु
कलेक्शन पाइपलाइन फ़ंक्शन कंपोज़िशन और कलेक्शन पाइपलाइन पेश करती है, दो कार्यात्मक-शैली पैटर्न जिन्हें आप अपने कोड में संग्रहों को पुनरावृत्त करने के लिए जोड़ सकते हैं।
कार्यात्मक प्रोग्रामिंग में, छोटे मॉड्यूलर कार्यों या संचालन की एक श्रृंखला के माध्यम से जटिल संचालन को अनुक्रमित करना आम बात है। श्रृंखला को फ़ंक्शंस की संरचना, या फ़ंक्शंस संरचना कहा जाता है। जब डेटा का संग्रह किसी फ़ंक्शन संरचना के माध्यम से प्रवाहित होता है, तो यह एक संग्रह पाइपलाइन बन जाता है। फ़ंक्शन संरचना और संग्रह पाइपलाइन दो डिज़ाइन पैटर्न हैं जिनका उपयोग अक्सर कार्यात्मक-शैली प्रोग्रामिंग में किया जाता है।

## क्लास डायग्राम
![alt text](../../../collection-pipeline/etc/collection-pipeline.png "Collection Pipeline")

## प्रयोज्यता
जब संग्रह पाइपलाइन पैटर्न का उपयोग करें

* जब आप ऑपरेशनों का एक क्रम निष्पादित करना चाहते हैं जहां एक ऑपरेशन का एकत्रित आउटपुट अगले में फीड किया जाता है
* जब आप अपने कोड में बहुत सारे स्टेटमेंट का उपयोग करते हैं
* जब आप अपने कोड में बहुत सारे लूप का उपयोग करते हैं

## श्रेय

* [Function composition and the Collection Pipeline pattern](https://www.ibm.com/developerworks/library/j-java8idioms2/index.html)
* [Martin Fowler](https://martinfowler.com/articles/collection-pipeline/)
* [Java8 Streams](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
