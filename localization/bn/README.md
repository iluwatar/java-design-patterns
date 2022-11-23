<!-- the line below needs to be an empty line C: (its because kramdown isnt

     that smart and dearly wants an empty line before a heading to be able to

     display it as such, e.g. website) -->

# জাভাতে প্রয়োগ করা ডিজাইনের প্যাটার্ন

![Java CI](https://github.com/iluwatar/java-design-patterns/workflows/Java%20CI/badge.svg)

[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/LICENSE.md)

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=ncloc)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=coverage)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)

[![Join the chat at https://gitter.im/iluwatar/java-design-patterns](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/iluwatar/java-design-patterns?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->

[![All Contributors](https://img.shields.io/badge/all_contributors-213-orange.svg?style=flat-square)](#contributors-)

<!-- ALL-CONTRIBUTORS-BADGE:END -->

<br/>

বিভিন্ন ভাষায় পড়ুন : [**zh**](localization/zh/README.md), [**ko**](localization/ko/README.md), [**fr**](localization/fr/README.md), [**tr**](localization/tr/README.md), [**ar**](localization/ar/README.md), [**es**](localization/es/README.md), [**pt**](localization/pt/README.md), [**id**](localization/id/README.md), [**ru**](localization/ru/README.md), [**de**](localization/de/README.md), [**ja**](localization/ja/README.md), [**vi**](localization/vi/README.md), [**bn**](localization/bn/README.md)

<br/>

# ভূমিকা

ডিজাইন প্যাটার্ন হল সেরা, আনুষ্ঠানিক অনুশীলন যা একজন প্রোগ্রামার ব্যবহার করতে পারে

একটি অ্যাপ্লিকেশন বা সিস্টেম ডিজাইন করার সময় সাধারণ সমস্যাগুলি সমাধান করুন।

ডিজাইনের প্যাটার্নগুলি পরীক্ষিত, প্রমাণিত প্রদান করে উন্নয়ন প্রক্রিয়াকে ত্বরান্বিত করতে পারে

উন্নয়ন দৃষ্টান্ত

ডিজাইন প্যাটার্নগুলি পুনঃব্যবহার করা সূক্ষ্ম সমস্যাগুলি প্রতিরোধ করতে সাহায্য করে যা প্রধান সমস্যাগুলির কারণ হয়

সমস্যা, এবং এটি কোডার এবং স্থপতিদের জন্য কোড পাঠযোগ্যতাও উন্নত করে যারা

নিদর্শনগুলির সাথে পরিচিত।

# শুরু হচ্ছে

এই সাইটটি জাভা ডিজাইন প্যাটার্ন প্রদর্শন করে। সমাধান উন্নত করা হয়েছে

ওপেন সোর্স সম্প্রদায়ের অভিজ্ঞ প্রোগ্রামার এবং স্থপতি দ্বারা। প্যাটার্নগুলি তাদের উচ্চ-স্তরের বর্ণনা দ্বারা বা তাদের দেখে ব্রাউজ করা যেতে পারে

সোর্স কোড। কোড উদাহরণ ভাল মন্তব্য করা হয় এবং হিসাবে চিন্তা করা যেতে পারে

প্রোগ্রামিং টিউটোরিয়াল কিভাবে একটি নির্দিষ্ট প্যাটার্ন বাস্তবায়ন করতে হয়। আমরা সবচেয়ে বেশি ব্যবহার করি

জনপ্রিয় ওপেন সোর্স জাভা প্রযুক্তি।

আপনি উপাদান সম্পর্কে জানার আগে, আপনি বিভিন্ন সঙ্গে পরিচিত হতে হবে

[Software Design Principles](https://java-design-patterns.com/principles/).

সমস্ত ডিজাইন যতটা সম্ভব সহজ হওয়া উচিত। আপনার চুম্বন, যগ্নি দিয়ে শুরু করা উচিত,

এবং সবচেয়ে সহজ জিনিসটি করুন যা সম্ভবত নীতিগুলি কাজ করতে পারে। জটিলতা এবং

নিদর্শনগুলি কেবল তখনই চালু করা উচিত যখন সেগুলি ব্যবহারিক জন্য প্রয়োজন

এক্সটেনসিবিলিটি

একবার আপনি এই ধারণাগুলির সাথে পরিচিত হয়ে গেলে আপনি ড্রিল করা শুরু করতে পারেন

[available design patterns](https://java-design-patterns.com/patterns/) নিম্নলিখিত পন্থাগুলির যেকোনো একটি দ্বারা

 - নাম দ্বারা একটি নির্দিষ্ট প্যাটার্ন জন্য অনুসন্ধান করুন। একটি খুঁজে পাচ্ছেন না? একটি নতুন প্যাটার্ন রিপোর্ট করুন [here](https://github.com/iluwatar/java-design-patterns/issues).

 - ট্যাগ ব্যবহার করা যেমন `পারফরম্যান্স`, `গ্যাং অফ ফোর` বা `ডেটা অ্যাক্সেস`। 

 - প্যাটার্ন বিভাগ, `সৃজনশীল`, `আচরণমূলক` এবং অন্যান্য ব্যবহার করে।

আশা করি, আপনি এই সাইটে উপস্থাপিত অবজেক্ট-ওরিয়েন্টেড সমাধানগুলিকে আপনার আর্কিটেকচারে উপযোগী খুঁজে পাবেন এবং সেগুলিকে বিকাশ করার সময় আমরা সেগুলি শিখতে যতটা মজা পেয়েছি।

# কিভাবে অবদান রাখতে হয়

আপনি যদি প্রকল্পে অবদান রাখতে ইচ্ছুক হন তবে আপনি আমাদের প্রাসঙ্গিক তথ্য পাবেন [developer wiki](https://github.com/iluwatar/java-design-patterns/wiki). 

আমরা আপনাকে সাহায্য করব এবং আপনার প্রশ্নের উত্তর দেব [Gitter chatroom](https://gitter.im/iluwatar/java-design-patterns).

# লাইসেন্স

এই প্রকল্পটি MIT লাইসেন্সের শর্তাবলীর অধীনে লাইসেন্সপ্রাপ্ত।

