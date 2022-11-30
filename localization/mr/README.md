<!-- the line below needs to be an empty line C: (its because kramdown isnt
     that smart and dearly wants an empty line before a heading to be able to
     display it as such, e.g. website) -->
     
     # जावा मध्ये डिझाइन नमुने लागू केले
     
     ![Java CI](https://github.com/iluwatar/java-design-patterns/workflows/Java%20CI/badge.svg)
[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/LICENSE.md)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=ncloc)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=coverage)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)
[![Join the chat at https://gitter.im/iluwatar/java-design-patterns](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/iluwatar/java-design-patterns?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-214-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

<br/>

Read in different language : [**zh**](localization/zh/README.md), [**ko**](localization/ko/README.md), [**fr**](localization/fr/README.md), [**tr**](localization/tr/README.md), [**ar**](localization/ar/README.md), [**es**](localization/es/README.md), [**pt**](localization/pt/README.md), [**id**](localization/id/README.md), [**ru**](localization/ru/README.md), [**de**](localization/de/README.md), [**ja**](localization/ja/README.md), [**vi**](localization/vi/README.md), [**ma**](localization/ma/README.md)

<br/>

#परिचय

डिझाईन पॅटर्न हे प्रोग्रामर वापरू शकणार्‍या सर्वोत्तम, औपचारिक पद्धती आहेत
अनुप्रयोग किंवा सिस्टम डिझाइन करताना सामान्य समस्या सोडवा.

डिझाइन नमुने चाचणी केलेले, सिद्ध करून विकास प्रक्रियेस गती देऊ शकतात
विकास नमुना.

डिझाईन नमुन्यांचा पुनर्वापर केल्याने प्रमुख कारणीभूत असलेल्या सूक्ष्म समस्या टाळण्यास मदत होते
समस्या, आणि ते कोडर आणि आर्किटेक्टसाठी कोड वाचनीयता देखील सुधारते
नमुन्यांशी परिचित आहेत.

#सुरुवात

ही साइट Java डिझाइन पॅटर्न दाखवते. द्वारे उपाय विकसित केले गेले आहेत
ओपन-सोर्स समुदायातील अनुभवी प्रोग्रामर आणि आर्किटेक्ट. द
नमुने त्यांच्या उच्च-स्तरीय वर्णनांद्वारे किंवा त्यांच्याकडे पाहून ब्राउझ केले जाऊ शकतात
मूळ सांकेतिक शब्दकोश. स्त्रोत कोड उदाहरणे चांगल्या प्रकारे टिप्पणी केली आहेत आणि म्हणून विचार केला जाऊ शकतो
विशिष्ट पॅटर्न कसे अंमलात आणायचे यावरील प्रोग्रामिंग ट्यूटोरियल. आम्ही सर्वात जास्त वापरतो
लोकप्रिय युद्ध-सिद्ध ओपन-सोर्स जावा तंत्रज्ञान.

आपण सामग्रीमध्ये जाण्यापूर्वी, आपल्याला विविध गोष्टींशी परिचित असले पाहिजे [सॉफ्टवेअर डिझाइन तत्त्वे](https://java-design-patterns.com/principles/).

सर्व रचना शक्य तितक्या सोप्या असाव्यात. तुम्ही KISS, YAGNI ने सुरुवात करावी,
आणि शक्यतो तत्त्वे कार्य करू शकणारी सर्वात सोपी गोष्ट करा. जटिलता आणि
नमुने केवळ तेव्हाच सादर केले जावे जेव्हा ते प्रॅक्टिकलसाठी आवश्यक असतील
विस्तारक्षमता

एकदा आपण या संकल्पनांशी परिचित झाल्यानंतर आपण मध्ये ड्रिल करणे सुरू करू शकता
[उपलब्ध डिझाइन नमुने](https://java-design-patterns.com/patterns/) कोणत्याही
खालील दृष्टिकोनांपैकी

 - नावाने विशिष्ट नमुना शोधा. एक शोधू शकत नाही? कृपया नवीन नमुना [येथे] (https://github.com/iluwatar/java-design-patterns/issues) नोंदवा.
 - `परफॉर्मन्स`, `गँग ऑफ फोर` किंवा `डेटा ऍक्सेस` यासारखे टॅग वापरणे.
 - पॅटर्न श्रेण्या, `सृजनात्मक`, `वर्तणूक` आणि इतर वापरणे.

आशा आहे की, तुम्हाला या साइटवर सादर केलेले ऑब्जेक्ट-ओरिएंटेड उपाय उपयुक्त वाटतील
तुमच्या आर्किटेक्चर्समध्ये आणि त्यांना विकसित करताना आम्हाला जेवढी मजा आली तेवढीच ती शिकण्यात मजा करा.
# योगदान कसे द्यावे

तुम्ही प्रकल्पात योगदान देण्यास इच्छुक असाल तर तुम्हाला संबंधित माहिती मिळेल
आमचे [डेव्हलपर विकी](https://github.com/iluwatar/java-design-patterns/wiki). आम्ही मदत करू
तुम्ही आणि तुमच्या प्रश्नांची उत्तरे [Gitter chatroom](https://gitter.im/iluwatar/java-design-patterns) मध्ये द्या.

# परवाना

हा प्रकल्प एमआयटी परवान्याच्या अटींनुसार परवानाकृत आहे.

