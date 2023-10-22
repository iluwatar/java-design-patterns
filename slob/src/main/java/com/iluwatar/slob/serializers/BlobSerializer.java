package com.iluwatar.slob.serializers;

import com.iluwatar.slob.lob.Customer;
import java.sql.SQLException;

public class BlobSerializer extends LobSerializer {

  protected BlobSerializer() throws SQLException {
  }

  @Override
  public Object serialize(Customer toSerialize) {
    // TODO: Yet to do
    return 0;
  }

  @Override
  public Customer deSerialize(Object toSerialize) {
    // TODO: Yet to do
    return null;
  }
}
