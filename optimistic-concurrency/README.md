

## Intent

Each entity is given a version number, which is changed every time the entity is updated.

## Explanation

1. Fetch entity from database. It will be associated with some version number.
2. Make changes to the entity.
3. Check the version number of the original object
4. If no one has made any change to the entity, then the version number remain the same.
   Then, We can replace the original with the clone and update the version.
   Otherwise, give an error.

## Programmatic Example

Cloning original object
```java
    Optional found = productDao.get(productId);
    if (found.isPresent()) {
        Product oldProduct = (Product) found.get();
        long oldId = oldProduct.getId();
        int oldVersion = oldProduct.getVersion();
        // clone
        Product newProduct = new Product(oldProduct);
```
Making changes to the clone
```java
// make changes
int remaining = oldProduct.getAmountInStock() - amount;
if (remaining < 0) {
    System.out.println("There are not enough products in stock!");
    return;
}
newProduct.setAmountInStock(remaining);
```
Replace the original with the clone
```java
Query query = em.createQuery("update Product set "
        + "id = :newId, "
        + "version = :newVersion, "
        + "name = :newName, "
        + "description = :newDesc, "
        + "price = :newPrice, "
        + "amountInStock = :newAmount "
```
If no one has updated it, then the entry should still have old version.
Thus, update is successful
```java
        + "where id = :oldId "
        + "and version = :oldVersion"
);
```
Also increment the version during update
```java
query.setParameter("newVersion", newProduct.getVersion() + 1);
```
If someone already updated it, then the entry will have a different version.
Thus, the version filter on update will not find any entry to update. 
This will raise a runtime exception and a rollback will be performed.
```java
try {
   tx.begin();
   op.accept(em);
   tx.commit();
} catch (RuntimeException e) {
   tx.rollback();
   throw e;
}
```
The exception will be caught in the buy function in ProductService.
Then, a log will be printed for client
```java
try {
    productDao.update(newProduct, oldId, oldVersion);
} catch (OptimisticLockException e) {
    System.out.println("Buy operation is not successful!");
    return;
}
```

## Reference
https://www.javacodegeeks.com/2012/11/jpahibernate-version-based-optimistic-concurrency-control.html