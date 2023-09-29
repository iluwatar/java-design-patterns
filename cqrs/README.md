---
title: CQRS
category: Architectural
language: en
tag:
  - Performance
  - Cloud distributed
---

## Intent
CQRS Command Query Responsibility Segregation - Separate the query side from the command side.

## Explanation
### Real-world example:

Imagine you are building a complex e-commerce platform. You have a system where customers can place orders, change their account information, and also search for products. In traditional architectures, these operations might all share the same data models and databases. However, as your platform grows, handling these various tasks efficiently becomes a challenge. This is where CQRS comes into play. It allows you to segregate the responsibility for handling commands (e.g., placing orders, updating customer data) from queries (e.g., searching for products). By doing so, you can optimize and scale each side independently to improve overall system performance and manageability.

### In plain words:

The CQRS (Command Query Responsibility Segregation) pattern is like having two specialized teams for different tasks. One team, the "command" side, focuses on making changes to the system (e.g., handling orders and updates), while the other team, the "query" side, specializes in retrieving information (e.g., searching and reading data). This separation allows you to tailor each side for its specific role, which can lead to better performance, scalability, and maintainability of your software.

### Wikipedia says:

The [CQRS pattern](https://en.wikipedia.org/wiki/Command%E2%80%93query_separation) (Command Query Responsibility Segregation) is an architectural pattern used in software design. It emphasizes separating the responsibility for handling commands (which change the system's state) from queries (which retrieve information without changing state). By doing so, CQRS promotes a more focused and optimized approach to handling different aspects of an application.

### Programmatic example:
Here's a simplified programmatic example in Python to illustrate the CQRS (Command Query Responsibility Segregation) pattern. In this example, we'll create a basic in-memory "store" where we can issue commands to update data and queries to retrieve data separately:
# Separate data stores for commands and queries
command_store = {}
query_store = {}

# Command handlers
def create_user(user_id, name):
    command_store[user_id] = {"name": name}

def update_user_name(user_id, new_name):
    if user_id in command_store:
        command_store[user_id]["name"] = new_name

# Query handlers
def get_user_name(user_id):
    if user_id in query_store:
        return query_store[user_id]["name"]
    return None

# Example commands
create_user(1, "Alice")
create_user(2, "Bob")
update_user_name(1, "Alicia")

# Example queries
query_store = command_store.copy()  # Snapshot the command store for querying

# Retrieve user names
print(get_user_name(1))  # Output: Alicia
print(get_user_name(2))  # Output: Bob
print(get_user_name(3))  # Output: None (user does not exist)

In this example:

We have two separate data stores: command_store for handling commands and query_store for handling queries.

The create_user and update_user_name functions are command handlers responsible for modifying data in the command_store.

The get_user_name function is a query handler responsible for retrieving data from the query_store.

We issue commands to create and update users, and then we take a snapshot of the command_store to populate the query_store. This separation of stores demonstrates the CQRS pattern's principle of segregating the responsibility for commands and queries.

## Class diagram
![alt text](./etc/cqrs.png "CQRS")

## Applicability
Use the CQRS pattern when

* You want to scale the queries and commands independently.
* You want to use different data models for queries and commands. Useful when dealing with complex domains.
* You want to use architectures like event sourcing or task based UI.

## Credits

* [Greg Young - CQRS, Task Based UIs, Event Sourcing agh!](http://codebetter.com/gregyoung/2010/02/16/cqrs-task-based-uis-event-sourcing-agh/)
* [Martin Fowler - CQRS](https://martinfowler.com/bliki/CQRS.html)
* [Oliver Wolf - CQRS for Great Good](https://www.youtube.com/watch?v=Ge53swja9Dw)
* [Command and Query Responsibility Segregation (CQRS) pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/cqrs)
