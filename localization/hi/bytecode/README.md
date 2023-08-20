---
title: Bytecode
category: Behavioral
language: hi
tag:
 - Game programming
---

## हेतु

वर्चुअल मशीन के लिए निर्देशों के रूप में एन्कोडिंग व्यवहार की अनुमति देता है।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> एक टीम एक नए गेम पर काम कर रही है जहां जादूगर एक-दूसरे के खिलाफ लड़ते हैं। जादूगर का व्यवहार
> प्लेटेस्टिंग के माध्यम से सैकड़ों बार सावधानीपूर्वक समायोजित और पुनरावृत्त करने की आवश्यकता है। यह
> हर बार जब गेम डिजाइनर बदलाव करना चाहता है तो प्रोग्रामर से बदलाव करने के लिए कहना सबसे अच्छा है
> व्यवहार, इसलिए विज़ार्ड व्यवहार को डेटा-संचालित वर्चुअल मशीन के रूप में कार्यान्वित किया जाता है।

साफ़ शब्दों में

> बाइटकोड पैटर्न कोड के बजाय डेटा द्वारा संचालित व्यवहार को सक्षम बनाता है।

[Gameprogrammingpatterns.com](https://gameprogrammingpatterns.com/bytecode.html) प्रलेखन
बताता है:

> एक निर्देश सेट निम्न-स्तरीय संचालन को परिभाषित करता है जिन्हें निष्पादित किया जा सकता है। की एक श्रृंखला
> निर्देश बाइट्स के अनुक्रम के रूप में एन्कोड किया गया है। एक वर्चुअल मशीन इन निर्देशों को एक-एक करके क्रियान्वित करती है
> एक समय में, मध्यवर्ती मानों के लिए स्टैक का उपयोग करना। अनुदेशों के संयोजन से, जटिल उच्च-स्तरीय
> व्यवहार को परिभाषित किया जा सकता है।

**प्रोग्रामेटिक उदाहरण**

सबसे महत्वपूर्ण गेम ऑब्जेक्ट में से एक `Wizard` वर्ग है।

```java
@AllArgsConstructor
@Setter
@Getter
@Slf4j
public class Wizard {

  private int health;
  private int agility;
  private int wisdom;
  private int numberOfPlayedSounds;
  private int numberOfSpawnedParticles;

  public void playSound() {
    LOGGER.info("Playing sound");
    numberOfPlayedSounds++;
  }

  public void spawnParticles() {
    LOGGER.info("Spawning particles");
    numberOfSpawnedParticles++;
  }
}
```

इसके बाद, हम अपनी वर्चुअल मशीन के लिए उपलब्ध निर्देश दिखाते हैं। प्रत्येक निर्देश का अपना है
यह स्टैक डेटा के साथ कैसे संचालित होता है, इसका अपना शब्दार्थ। उदाहरण के लिए, ADD निर्देश शीर्ष पर है
स्टैक से दो आइटम, उन्हें एक साथ जोड़ता है और परिणाम को स्टैक पर भेजता है।

```java
@AllArgsConstructor
@Getter
public enum Instruction {

  LITERAL(1),         // e.g. "LITERAL 0", push 0 to stack
  SET_HEALTH(2),      // e.g. "SET_HEALTH", pop health and wizard number, call set health
  SET_WISDOM(3),      // e.g. "SET_WISDOM", pop wisdom and wizard number, call set wisdom
  SET_AGILITY(4),     // e.g. "SET_AGILITY", pop agility and wizard number, call set agility
  PLAY_SOUND(5),      // e.g. "PLAY_SOUND", pop value as wizard number, call play sound
  SPAWN_PARTICLES(6), // e.g. "SPAWN_PARTICLES", pop value as wizard number, call spawn particles
  GET_HEALTH(7),      // e.g. "GET_HEALTH", pop value as wizard number, push wizard's health
  GET_AGILITY(8),     // e.g. "GET_AGILITY", pop value as wizard number, push wizard's agility
  GET_WISDOM(9),      // e.g. "GET_WISDOM", pop value as wizard number, push wizard's wisdom
  ADD(10),            // e.g. "ADD", pop 2 values, push their sum
  DIVIDE(11);         // e.g. "DIVIDE", pop 2 values, push their division
  // ...
}
```

हमारे उदाहरण के केंद्र में `VirtualMachine` वर्ग है। यह निर्देशों को इनपुट के रूप में लेता है और
गेम ऑब्जेक्ट व्यवहार प्रदान करने के लिए उन्हें निष्पादित करता है।

```java
@Getter
@Slf4j
public class VirtualMachine {

  private final Stack<Integer> stack = new Stack<>();

  private final Wizard[] wizards = new Wizard[2];

  public VirtualMachine() {
    wizards[0] = new Wizard(randomInt(3, 32), randomInt(3, 32), randomInt(3, 32),
        0, 0);
    wizards[1] = new Wizard(randomInt(3, 32), randomInt(3, 32), randomInt(3, 32),
        0, 0);
  }

  public VirtualMachine(Wizard wizard1, Wizard wizard2) {
    wizards[0] = wizard1;
    wizards[1] = wizard2;
  }

  public void execute(int[] bytecode) {
    for (var i = 0; i < bytecode.length; i++) {
      Instruction instruction = Instruction.getInstruction(bytecode[i]);
      switch (instruction) {
        case LITERAL:
          // Read the next byte from the bytecode.
          int value = bytecode[++i];
          // Push the next value to stack
          stack.push(value);
          break;
        case SET_AGILITY:
          var amount = stack.pop();
          var wizard = stack.pop();
          setAgility(wizard, amount);
          break;
        case SET_WISDOM:
          amount = stack.pop();
          wizard = stack.pop();
          setWisdom(wizard, amount);
          break;
        case SET_HEALTH:
          amount = stack.pop();
          wizard = stack.pop();
          setHealth(wizard, amount);
          break;
        case GET_HEALTH:
          wizard = stack.pop();
          stack.push(getHealth(wizard));
          break;
        case GET_AGILITY:
          wizard = stack.pop();
          stack.push(getAgility(wizard));
          break;
        case GET_WISDOM:
          wizard = stack.pop();
          stack.push(getWisdom(wizard));
          break;
        case ADD:
          var a = stack.pop();
          var b = stack.pop();
          stack.push(a + b);
          break;
        case DIVIDE:
          a = stack.pop();
          b = stack.pop();
          stack.push(b / a);
          break;
        case PLAY_SOUND:
          wizard = stack.pop();
          getWizards()[wizard].playSound();
          break;
        case SPAWN_PARTICLES:
          wizard = stack.pop();
          getWizards()[wizard].spawnParticles();
          break;
        default:
          throw new IllegalArgumentException("Invalid instruction value");
      }
      LOGGER.info("Executed " + instruction.name() + ", Stack contains " + getStack());
    }
  }

  public void setHealth(int wizard, int amount) {
    wizards[wizard].setHealth(amount);
  }
  // other setters ->
  // ...
}
```

अब हम वर्चुअल मशीन का उपयोग करके पूरा उदाहरण दिखा सकते हैं।

```java
  public static void main(String[] args) {

    var vm = new VirtualMachine(
        new Wizard(45, 7, 11, 0, 0),
        new Wizard(36, 18, 8, 0, 0));

    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
    vm.execute(InstructionConverterUtil.convertToByteCode("GET_HEALTH"));
    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
    vm.execute(InstructionConverterUtil.convertToByteCode("GET_AGILITY"));
    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 0"));
    vm.execute(InstructionConverterUtil.convertToByteCode("GET_WISDOM"));
    vm.execute(InstructionConverterUtil.convertToByteCode("ADD"));
    vm.execute(InstructionConverterUtil.convertToByteCode("LITERAL 2"));
    vm.execute(InstructionConverterUtil.convertToByteCode("DIVIDE"));
    vm.execute(InstructionConverterUtil.convertToByteCode("ADD"));
    vm.execute(InstructionConverterUtil.convertToByteCode("SET_HEALTH"));
  }
```

यहाँ कंसोल आउटपुट है.

```
16:20:10.193 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0]
16:20:10.196 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0, 0]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed GET_HEALTH, Stack contains [0, 45]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0, 45, 0]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed GET_AGILITY, Stack contains [0, 45, 7]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0, 45, 7, 0]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed GET_WISDOM, Stack contains [0, 45, 7, 11]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed ADD, Stack contains [0, 45, 18]
16:20:10.197 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed LITERAL, Stack contains [0, 45, 18, 2]
16:20:10.198 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed DIVIDE, Stack contains [0, 45, 9]
16:20:10.198 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed ADD, Stack contains [0, 54]
16:20:10.198 [main] INFO com.iluwatar.bytecode.VirtualMachine - Executed SET_HEALTH, Stack contains []
```

## क्लास डायग्राम

![alt text](../../../bytecode/etc/bytecode.urm.png "Bytecode class diagram")

## प्रयोज्यता

जब आपके पास बहुत सारे व्यवहार हों जिन्हें आपको परिभाषित करने की आवश्यकता हो तो बाइटकोड पैटर्न का उपयोग करें
गेम की कार्यान्वयन भाषा उपयुक्त नहीं है क्योंकि:

* यह बहुत निम्न स्तर का है, जिससे इसे प्रोग्राम करना कठिन या त्रुटि-प्रवण हो जाता है।
* धीमे संकलन समय या अन्य टूलींग समस्याओं के कारण इस पर पुनरावृत्ति करने में बहुत अधिक समय लगता है।
* इस पर बहुत ज्यादा भरोसा है. यदि आप यह सुनिश्चित करना चाहते हैं कि परिभाषित किया जा रहा व्यवहार गेम को तोड़ न सके, तो आपको इसे शेष कोडबेस से सैंडबॉक्स करना होगा।

## संबंधित पैटर्न

* [Interpreter](https://java-design-patterns.com/patterns/interpreter/)

## श्रेय

* [Game programming patterns](http://gameprogrammingpatterns.com/bytecode.html)
