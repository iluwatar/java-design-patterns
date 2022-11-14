<!-- the line below needs to be an empty line C: (its because kramdown isnt
     that smart and dearly wants an empty line before a heading to be able to
     display it as such, e.g. website) -->

# जाभामा लागू गरिएको डिजाइन ढाँचाहरू

![Java CI](https://github.com/iluwatar/java-design-patterns/workflows/Java%20CI/badge.svg)
[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/LICENSE.md)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=ncloc)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=coverage)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)
[![Join the chat at https://gitter.im/iluwatar/java-design-patterns](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/iluwatar/java-design-patterns?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-218-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

<br>

फरक भाषामा पढ्नुहोस् : [**zh**](localization/zh/README.md), [**ko**](localization/ko/README.md), [**fr**](localization/fr/README.md), [**tr**](localization/tr/README.md), [**ar**](localization/ar/README.md), [**es**](localization/es/README.md), [**pt**](localization/pt/README.md), [**id**](localization/id/README.md), [**ru**](localization/ru/README.md), [**de**](localization/de/README.md), [**ja**](localization/ja/README.md), [**vi**](localization/vi/README.md), [**bn**](localization/bn/README.md), [**np**](localization/np/README.md)
<br>

# परिचय

डिजाइन ढाँचाहरू सबै भन्दा राम्रो, औपचारिक अभ्यासहरू हुन् जुन प्रोग्रामरले अनुप्रयोग वा प्रणाली डिजाइन गर्दा सामान्य समस्याहरू समाधान गर्न प्रयोग गर्न सक्छ।

डिजाइन ढाँचाहरूले परीक्षण, प्रमाणित विकास प्रतिमानहरू प्रदान गरेर विकास प्रक्रियालाई गति दिन सक्छ।

डिजाइन ढाँचाहरू पुन: प्रयोग गर्नाले प्रमुख समस्याहरू निम्त्याउने सूक्ष्म समस्याहरूलाई रोक्न मद्दत गर्दछ, र यसले ढाँचाहरूसँग परिचित भएका कोडरहरू र आर्किटेक्टहरूको लागि कोड पढ्न योग्यतामा पनि सुधार गर्दछ।

# सुरु गर्दै

यो साइटले जाभा डिजाइन ढाँचाहरू प्रदर्शन गर्दछ। समाधानहरू द्वारा विकसित गरिएको छ
खुला स्रोत समुदायबाट अनुभवी प्रोग्रामरहरू र आर्किटेक्टहरू। द
ढाँचाहरू तिनीहरूको उच्च-स्तरको विवरणहरू वा हेरेर ब्राउज गर्न सकिन्छ
स्रोत कोड। स्रोत कोड उदाहरणहरू राम्ररी टिप्पणी गरिएका छन् र सोच्न सकिन्छ
एक विशिष्ट ढाँचा कसरी कार्यान्वयन गर्ने बारे प्रोग्रामिङ ट्यूटोरियल। हामी सबैभन्दा बढी प्रयोग गर्छौं
लोकप्रिय युद्ध सिद्ध खुला स्रोत जाभा प्रविधिहरू।

तपाईंले सामग्रीमा डुब्नु अघि, तपाईं विभिन्नसँग परिचित हुनुपर्छ
[सफ्टवेयर डिजाइन सिद्धान्तहरू](https://java-design-patterns.com/principles/).

सबै डिजाइनहरू सकेसम्म सरल हुनुपर्छ। चुम्बन, यज्ञीबाट सुरु गर्नुपर्छ,
र सम्भवतः सिद्धान्तहरू काम गर्न सक्ने साधारण कुरा गर्नुहोस्। जटिलता र
ढाँचाहरू मात्र पेश गर्नुपर्छ जब तिनीहरू व्यावहारिक रूपमा आवश्यक हुन्छन्
विस्तारशीलता।

एकचोटि तपाइँ यी अवधारणाहरूसँग परिचित भएपछि तपाइँ मा ड्रिल गर्न सुरु गर्न सक्नुहुन्छ
[उपलब्ध डिजाइन ढाँचा](https://java-design-patterns.com/patterns/) कुनै पनि द्वारा
निम्न दृष्टिकोणहरु को

- नाम द्वारा एक विशिष्ट ढाँचा खोज्नुहोस्। एउटा भेट्टाउन सक्नुहुन्न? कृपया एउटा नयाँ ढाँचा रिपोर्ट गर्नुहोस् [यहाँ](https://github.com/iluwatar/java-design-patterns/issues).
- `प्रदर्शन`, `Gang of Four`, वा `डेटा पहुँच` जस्ता ट्यागहरू प्रयोग गर्दै।
- ढाँचा कोटिहरू प्रयोग गर्दै, `सृजनात्मक`, `व्यवहार`, र अन्य।

आशा छ, तपाईंले यस साइटमा प्रस्तुत वस्तु-उन्मुख समाधानहरू उपयोगी पाउनुहुनेछ
तपाईंको आर्किटेक्चरमा र तिनीहरूलाई सिक्न धेरै रमाईलो गर्नुहोस् हामीले तिनीहरूलाई विकास गर्दा।

# कसरी योगदान गर्ने
यदि तपाईं परियोजनामा ​​योगदान गर्न इच्छुक हुनुहुन्छ भने तपाईंले सान्दर्भिक जानकारी पाउनुहुनेछ
हाम्रो [विकासकर्ता विकि](https://github.com/iluwatar/java-design-patterns/wiki). हामी मद्दत गर्नेछौं
तपाईं र [गिटर च्याटरूम](https://gitter.im/iluwatar/java-design-patterns) मा तपाईंका प्रश्नहरूको जवाफ दिनुहोस्।

# इजाजतपत्र
यो परियोजना MIT लाइसेन्स को सर्तहरु अन्तर्गत इजाजतपत्र दिइएको छ।

