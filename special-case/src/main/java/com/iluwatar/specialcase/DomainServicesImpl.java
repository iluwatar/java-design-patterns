package com.iluwatar.specialcase;

public class DomainServicesImpl implements DomainServices {

  /**
   * Domain purchase with userName and itemName, with validation for userName.
   *
   * @param userName of the user
   * @param itemName of the item
   * @return instance of ReceiptViewModel
   */
  public ReceiptViewModel purchase(String userName, String itemName) {
    Db.User user = Db.getInstance().findUserByUserName(userName);
    if (user == null) {
      return new InvalidUser(userName);
    }

    Db.Account account = Db.getInstance().findAccountByUser(user);
    return purchase(user, account, itemName);
  }

  /**
   * Domain purchase with user, account and itemName,
   * with validation for whether product is out of stock
   * and whether user has insufficient funds in the account.
   *
   * @param user in Db
   * @param account in Db
   * @param itemName of the item
   * @return instance of ReceiptViewModel
   */
  private ReceiptViewModel purchase(Db.User user, Db.Account account, String itemName) {
    Db.Product item = Db.getInstance().findProductByItemName(itemName);
    if (item == null) {
      return new OutOfStock(user.getUserName(), itemName);
    }

    ReceiptDto receipt = user.purchase(item);
    MoneyTransaction transaction = account.withdraw(receipt.getPrice());
    if (transaction == null) {
      return new InsufficientFunds(user.getUserName(), account.getAmount(), itemName);
    }

    return receipt;
  }
}
