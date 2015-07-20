package com.iluwatar.async.method.invocation;

import java.util.Optional;

public interface AsyncCallback<T> {

	void onComplete(T value, Optional<Exception> ex);

}
