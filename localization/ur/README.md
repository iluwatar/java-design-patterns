<!-- the line below needs to be an empty line C: (its because kramdown isnt
     that smart and dearly wants an empty line before a heading to be able to
     display it as such, e.g. website) -->

# جاوا میں لاگو ڈیزائن پیٹرن

![Java CI](https://github.com/iluwatar/java-design-patterns/workflows/Java%20CI/badge.svg)
[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/LICENSE.md)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=ncloc)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=coverage)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)
[![Join the chat at https://gitter.im/iluwatar/java-design-patterns](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/iluwatar/java-design-patterns?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->

[![All Contributors](https://img.shields.io/badge/all_contributors-213-orange.svg?style=flat-square)](#contributors-)

<!-- ALL-CONTRIBUTORS-BADGE:END -->

# تعارف

ڈیزائن پیٹرن بہترین، باضابطہ طریقے ہیں جو ایک پروگرامر ایپلی کیشن یا سسٹم کو ڈیزائن کرتے وقت عام مسائل کو حل کرنے کے لیے استعمال کر سکتا ہے۔

ڈیزائن کے نمونے آزمائشی، ثابت شدہ ترقیاتی نمونے فراہم کرکے ترقی کے عمل کو تیز کر سکتے ہیں۔

ڈیزائن کے نمونوں کو دوبارہ استعمال کرنے سے ان لطیف مسائل کو روکنے میں مدد ملتی ہے جو بڑے مسائل کا باعث بنتے ہیں، اور یہ کوڈرز اور معماروں کے لیے کوڈ پڑھنے کی اہلیت کو بھی بہتر بناتا ہے جو پیٹرن سے واقف ہیں۔

# شروع کرتے ہیں

یہ سائٹ جاوا ڈیزائن پیٹرنز کی نمائش کرتی ہے۔ حل اوپن سورس کمیونٹی کے تجربہ کار پروگرامرز اور آرکیٹیکٹس کے ذریعہ تیار کیے گئے ہیں۔ پیٹرن کو ان کی اعلیٰ سطحی وضاحت یا ان کے سورس کوڈ کو دیکھ کر براؤز کیا جا سکتا ہے۔ سورس کوڈ کی مثالوں پر اچھی طرح تبصرہ کیا گیا ہے اور ان کو پروگرامنگ ٹیوٹوریل کے طور پر سوچا جا سکتا ہے کہ کسی مخصوص پیٹرن کو کیسے نافذ کیا جائے۔ ہم سب سے مشہور جنگ سے ثابت شدہ اوپن سورس جاوا ٹیکنالوجیز استعمال کرتے ہیں۔

مواد میں غوطہ لگانے سے پہلے، آپ کو مختلف اصولوں سے واقف ہونا چاہیے۔

[سافٹ ویئر ڈیزائن کے اصول](https://java-design-patterns.com/patterns/)

تمام ڈیزائن ممکنہ حد تک آسان ہونے چاہئیں۔

آپ کو KISS، YAGNI، اور سب سے آسان کام کرنا چاہیے جو ممکنہ طور پر اصولوں پر کام کر سکے۔

پیچیدگی اور نمونوں کو صرف اس وقت متعارف کرایا جانا چاہئے جب ان کی عملی توسیع کے لئے ضرورت ہو۔

ایک بار جب آپ ان تصورات سے واقف ہو جائیں تو آپ مندرجہ ذیل طریقوں میں سے کسی کے [دستیاب ڈیزائن پیٹرن](https://java-design-patterns.com/patterns/)  کے نمونوں میں ڈرلنگ شروع کر سکتے ہیں۔

⚪ نام سے مخصوص پیٹرن تلاش کریں۔ ایک نہیں مل سکتا؟ براہ کرم ایک نئے پیٹرن کی اطلاع دیں۔ [یہاں](https://github.com/iluwatar/java-design-patterns/issues).

   ⚪ کارکردگی'، 'گینگ آف فور' یا 'ڈیٹا تک رسائی' جیسے ٹیگز کا استعمال۔

   ⚪ پیٹرن کے زمرے استعمال کرنا، 'تخلیقی'، 'رویے'، اور دیگر۔

امید ہے کہ، آپ کو اس سائٹ پر پیش کردہ آبجیکٹ پر مبنی حل کارآمد معلوم ہوں گے۔
اپنے فن تعمیرات میں اور ان کو سیکھنے میں اتنا ہی مزہ کریں جتنا ہم نے انہیں تیار کرتے وقت کیا تھا۔

# شراکت کیسے کریں 

اگر آپ پروجیکٹ میں حصہ ڈالنے کے لیے تیار ہیں تو آپ کو متعلقہ معلومات مل جائیں گی۔
ہمارے  [ڈویلپر ویکی](https://github.com/iluwatar/java-design-patterns/wiki). ہم مدد کریں گے۔ آپ اور میں آپ کے سوالات کے جوابات [گیٹر چیٹ روم](https://gitter.im/iluwatar/java-design-patterns).


# لائسنس

یہ پروجیکٹ MIT لائسنس کی شرائط کے تحت لائسنس یافتہ ہے۔



