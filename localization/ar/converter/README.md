---
title: Converter
shortTitle: Converter
category: Creational
language: ar
tag:
  - Decoupling
---

## الهدف

الهدف من نمط المحول (Converter) هو توفير وسيلة عامة وشائعة للتحويل الثنائي الاتجاه بين الأنواع المقابلة، مما يتيح تنفيذًا نظيفًا حيث لا يحتاج الأنواع إلى معرفة بعضها البعض. علاوة على ذلك، يقدم نمط المحول تعيينًا ثنائي الاتجاه للمجموعات الثنائية الاتجاه، مما يقلل من الكود المكرر إلى الحد الأدنى.

## الشرح

مثال من الحياة الواقعية

> في التطبيقات الواقعية، غالبًا ما يحدث أن يتكون طبقة قاعدة البيانات من كيانات تحتاج إلى أن يتم تحويلها إلى DTO لاستخدامها في طبقة منطق الأعمال. يتم إجراء تحويل مشابه لعدد potentially ضخم من الفئات ونحتاج إلى طريقة عامة لتحقيق ذلك.

ببساطة

> يسهل نمط التحويل (Converter) تعيين الكائنات من فئة إلى كائنات من فئة أخرى.

**مثال برمجي**

نحتاج إلى حل عام لمشكلة التعيين. لذلك، دعونا نقدم محولًا عامًا.


```java
public class Converter<T, U> {

  private final Function<T, U> fromDto;
  private final Function<U, T> fromEntity;

  public Converter(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
    this.fromDto = fromDto;
    this.fromEntity = fromEntity;
  }

  public final U convertFromDto(final T dto) {
    return fromDto.apply(dto);
  }

  public final T convertFromEntity(final U entity) {
    return fromEntity.apply(entity);
  }

  public final List<U> createFromDtos(final Collection<T> dtos) {
    return dtos.stream().map(this::convertFromDto).collect(Collectors.toList());
  }

  public final List<T> createFromEntities(final Collection<U> entities) {
    return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
  }
}
```

## المحولات المتخصصة ترث من هذه الفئة الأساسية كما يلي.


```java
public class UserConverter extends Converter<UserDto, User> {

  public UserConverter() {
    super(UserConverter::convertToEntity, UserConverter::convertToDto);
  }

  private static UserDto convertToDto(User user) {
    return new UserDto(user.getFirstName(), user.getLastName(), user.isActive(), user.getUserId());
  }

  private static User convertToEntity(UserDto dto) {
    return new User(dto.getFirstName(), dto.getLastName(), dto.isActive(), dto.getEmail());
  }

}
```

الآن يصبح التحويل بين `User` و `UserDto` أمرًا تافهًا.


```java
var userConverter = new UserConverter();
var dtoUser = new UserDto("John", "Doe", true, "whatever[at]wherever.com");
var user = userConverter.convertFromDto(dtoUser);
```

## مخطط الفئات

![alt text](./etc/converter.png "نمط المحول")

## القابلية للتطبيق

استخدم نمط المحول في الحالات التالية:

* عندما يكون لديك أنواع تتطابق منطقيًا مع بعضها البعض وتحتاج إلى تحويل الكيانات بينهما.
* عندما ترغب في توفير أشكال مختلفة لتحويل الأنواع اعتمادًا على السياق.
* كلما قدمت كائن نقل البيانات (DTO)، من المحتمل أن تحتاج إلى تحويله إلى معادله في المجال.

## الاعتمادات

* [نمط المحول في Java 8](http://www.xsolve.pl/blog/converter-pattern-in-java-8/)
