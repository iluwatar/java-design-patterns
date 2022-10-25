## Intent
Helps developers encapsulate each business rule in a separate object and separate the definition of a business rule from its processing. New rules can be added without modifying the rest of the application logic.
## Explanation
> Validation rules can simply be composed dynamically in any order based on various conditions, such as configuration Settings, user preferences, runtime, and so on.
> Validation rule objects can simply be reused in different parts of the application.
> Adding a new validation rule is as simple as adding a new object to the collection.
> Unit testing is easy because the validation rule object is smaller than the single conditional statement.
> The loop complexity metric is better than the monolithic conditional operator.
# Wikipedia says
Rules design pattern helps the developer to encapsulate each business rule in a separate object and decouple the definition of business rules from their processing. New rules can be added without the need to modify the rest of the application logic.
## Class Diagram
![image](https://user-images.githubusercontent.com/110162648/197736364-6cbc20bd-302d-4491-8f43-4cbb048ad414.png)
