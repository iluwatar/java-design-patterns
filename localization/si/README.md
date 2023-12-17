<!-- The line below should be a blank line C: (because it's not kramdown
     It's smart and lovely that a blank line is required before a heading
     Show it like this, e.g. website) -->

# ජාවා නිර්මාණ රටා

![Java CI](https://github.com/iluwatar/java-design-patterns/workflows/Java%20CI/badge.svg)
[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/LICENSE.md)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=ncloc)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=iluwatar_java-design-patterns&metric=coverage)](https://sonarcloud.io/dashboard?id=iluwatar_java-design-patterns)
[![Join the chat at https://gitter.im/iluwatar/java-design-patterns](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/iluwatar/java-design-patterns?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-176-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

<!-- <br/>

Baca dalam bahasa lain : [**zh**](localization/zh/README.md), [**ko**](localization/ko/README.md), [**fr**](localization/fr/README.md), [**tr**](localization/tr/README.md), [**ar**](localization/ar/README.md), [**es**](localization/es/README.md), [**pt**](localization/pt/README.md), [**id**](localization/id/README.md)

<br/> -->

# හැදින්වීම

පැන නගින ගැටළු විසඳීම සඳහා හොඳම ක්රමය නිර්මාණ රටා වේ.

සැලසුම් රටා ලබා දීමෙන් සංවර්ධන ක්රියාවලිය වේගවත් කළ හැකිය.

සැකිලි නැවත භාවිතා කිරීම හේතු වන පොදු ගැටළු මඟහරවා ගැනීමට උපකාරී වේ.
ගැටළු ඇතිවේ. මෙය ක්‍රමලේඛකයින් සඳහා කේතයේ කියවීමේ හැකියාවද වැඩි කරයි.


# ආරම්භය

මෙම ගබඩාව Java හි නිර්මාණ රටා සත්කාරකත්වය සපයයි. ඒවා දියුණු කළා
විවෘත මූලාශ්‍ර ප්‍රජාවේ ක්‍රමලේඛකයින්. රටාව තෝරා ගත හැකිය.
එහි විස්තරයෙන් හෝ එහි මූල කේතය බැලීමෙන්. කේතය හොඳින් ලේඛනගත කර ඇත,
එය නිශ්චිත රටාවක් පිළිබඳ ක්‍රමලේඛන නිබන්ධනයක් ලෙස සැලකිය හැකිය.
අපි වඩාත් ජනප්රිය (ගිනි, ජලය සහ තඹ පයිප්ප පරීක්ෂා කරන ලද) තාක්ෂණයන් භාවිතා කරමු,
විවෘත කේත මෘදුකාංග මත පමණක් පදනම් වේ.

මෙයට කිමිදීමට පෙර, ඔබ විවිධත්වය ගැන හුරුපුරුදු විය යුතුය.
[මෘදුකාංග සංවර්ධනයේ මූලධර්ම](https://java-design-patterns.com/principles/).

සියලුම මෝස්තර හැකි තරම් සරල විය යුතුය. අනවශ්‍ය ක්‍රියාකාරීත්වයක් ඇති නොකරන්න
බොහෝ දුරට ප්‍රයෝජනවත් නොවනු ඇත, නමුත් කළ හැකි සරල දෙයක් සාදන්න
කාර්යය. පරිමාණය කිරීමේදී පමණක් රටා සංකීර්ණ කිරීම හා හඳුන්වා දීම අවශ්ය වේ
ඇත්තටම අවශ්යයි.

ඔබ මෙම සංකල්ප ගැන හුරුපුරුදු වූ පසු, අධ්යයනය ආරම්භ කරන්න
[ලබා ගත හැකි නිර්මාණ රටා](https://java-design-patterns.com/patterns/)
පහත ක්‍රම වලින්:

- නමින් සැකිල්ලක් සොයන්න. එකක් හොයාගන්න බැරි වුනාද? එය වාර්තා කරන්න [මෙහි](https://github.com/iluwatar/java-design-patterns/issues).
- `Performance`, `Gang of Four` හෝ `Data access` වැනි ටැග් භාවිතා කිරීම.
- සැකිලි කාණ්ඩ භාවිතා කිරීම  `Creational`, `Behavioral` සහ වෙනත්.


මෙහි ඉදිරිපත් කර ඇති වෛෂයික-නැඹුරු විසඳුම් ඔබට ප්‍රයෝජනවත් වනු ඇතැයි අපි බලාපොරොත්තු වෙමු
ප්රයෝජනවත් වන අතර ඔබේ ව්යාපෘතිවල ස්ථානයක් සොයා ගනු ඇත, ඔබට එම සතුට ලැබෙනු ඇත
ඔවුන්ගේ සංවර්ධනයේදී අපට ලැබුණු අධ්‍යයනයෙන්.

# සංවර්ධනයට සහභාගී වන්නේ කෙසේද
ඔබට ව්‍යාපෘතියේ ජීවිතයට සහභාගී වීමට අවශ්‍ය නම්, සියලු ප්‍රයෝජනවත් තොරතුරු ක්‍රියාත්මක වේ
අපගේ [wiki](https://github.com/iluwatar/java-design-patterns/wiki). අපිට උදව් කරන්න පුළුවන්
සහ [Gitter] කතාබස් (https://gitter.im/iluwatar/java-design-patterns) තුළ ඔබේ ප්‍රශ්නවලට පිළිතුරු දෙන්න.

# බලපත්‍රය
ව්‍යාපෘතිය MIT බලපත්‍රයේ සාරාංශ මත පදනම් වේ.

