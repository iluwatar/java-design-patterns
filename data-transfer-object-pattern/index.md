Data Transfer Object Pattern , also known as Transfer Object Pattern .  

Transfer Object is a simple POJO class having getter/setter methods and is serializable so that it can be transferred over the network. 
It does not have any behavior. Server Side business class normally fetches data from the database and fills the POJO and send it to the client or pass it by value.


Transfer Object is used to encapsulate the business data. A single method call is used to send and retrieve the Transfer Object. When the client requests the enterprise bean for the business data, the enterprise bean can construct the Transfer Object, populate it with its attribute values, and pass it by value to the client.

Clients usually require more than one value from an enterprise bean. To reduce the number of remote calls and to avoid the associated overhead, it is best to use Transfer Objects to transport the data from the enterprise bean to its client.

When an enterprise bean uses a Transfer Object, the client makes a single remote method invocation to the enterprise bean to request the Transfer Object instead of numerous remote method calls to get individual attribute values. The enterprise bean then constructs a new Transfer Object instance, copies values into the object and returns it to the client. The client receives the Transfer Object and can then invoke accessor (or getter) methods on the Transfer Object to get the individual attribute values from the Transfer Object. Or, the implementation of the Transfer Object may be such that it makes all attributes public. Because the Transfer Object is passed by value to the client, all calls to the Transfer Object instance are local calls instead of remote method invocations.


Structure

Below figure shows the class diagram that represents the Transfer Object pattern in its simplest form.

https://www.dropbox.com/s/w02ao4tz4b0h359/data-transfer-object-pattern.jpg?dl=0


As shown in this class diagram, the Transfer Object is constructed on demand by the enterprise bean and returned to the remote client. However, the Transfer Object pattern can adopt various strategies, depending on requirements .

Below figure contains the sequence diagram that shows the interactions for the Transfer Object pattern .

Transfer Object Sequence Diagram

https://www.dropbox.com/s/k8fiug8wqruq718/sequence%20diagram.jpg?dl=0

Client

This represents the client of the enterprise bean. The client can be an end-user application, as in the case of a rich client application that has been designed to directly access the enterprise beans. 

The BusinessObject

It represents a role in this pattern that can be fulfilled by a session bean, an entity bean, or a Data Access Object (DAO). The BusinessObject is responsible for creating the Transfer Object and returning it to the client upon request. 

TransferObject

The TransferObject is an arbitrary serializable Java object referred to as a Transfer Object. A Transfer Object class may provide a constructor that accepts all the required attributes to create the Transfer Object. The constructor may accept all entity bean attribute values that the Transfer Object is designed to hold. 
