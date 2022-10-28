## Intent
Helps developers encapsulate each business rule in a separate object and separate the definition of a business rule from its processing. New rules can be added without modifying the rest of the application logic.
## Explanation
> Validation rules can simply be composed dynamically in any order based on various conditions, such as configuration Settings, user preferences, runtime, and so on.
> Validation rule objects can simply be reused in different parts of the application.
> Adding a new validation rule is as simple as adding a new object to the collection.
> Unit testing is easy because the validation rule object is smaller than the single conditional statement.
> The loop complexity metric is better than the monolithic conditional operator.
### Real world example
* In this example we have an imaginary country that has an immigration policy with multiple criteria
* We create separate class for each criterion to implement the pattern to calculate the candidate's
* immigration score.
### Programmatic Example
Walking through our example, here's the basic Candidate entity.

![image](https://user-images.githubusercontent.com/110162648/198544770-266140ce-abc5-4d92-85b8-b66576fc80e7.png)

Here's the CustomerDao interface and two different implementations for it.

![image](https://user-images.githubusercontent.com/110162648/198544781-d7a14fe5-2e45-4e12-a0bc-aabf40e8a60b.png)


The program output:

![image](https://user-images.githubusercontent.com/110162648/198545044-4d45db23-0ed9-4369-881b-2b4261410222.png)

# Wikipedia says
Rules design pattern helps the developer to encapsulate each business rule in a separate object and decouple the definition of business rules from their processing. New rules can be added without the need to modify the rest of the application logic.
## Class Diagram

![image](https://user-images.githubusercontent.com/110162648/198545171-a154750a-e6a9-4785-b183-b97303e7ff8a.png)



