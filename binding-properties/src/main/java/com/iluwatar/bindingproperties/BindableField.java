package com.iluwatar.bindingproperties;

import java.util.HashMap;
import java.util.function.Function;

/**
 * This is the abstract class for a fielt to be bindable. Inherit this
 * type of class and put your objects inside it then you can bind it with other
 *  fields to make them change simultaneously.
 */
public abstract class BindableField<T> {
  protected T value;
  protected boolean blockPropagation;
  protected HashMap<BindableField, Function<T, ? extends Object>> notifyMap;

  /**
   * Bind this field with another field, whenever this filed changes, another
   *  field will change too, but not vise versa. The new value of another field
   *  is equal to the new value of this field.
   *
   * @param target another bindable field.
   *
   */
  public void bind(BindableField<T> target) {
    notifyMap.put(target, (newValue) -> newValue);
  }

  /**
   * Bind this field with another field, whenever this filed changes, another
   *  field will change too, but not vise versa. The changing behavior is
   * defined by the second parameter.
   *
   * @param target another bindable field.
   * @param onChangeFunction the function to caculate the new value for
   *                         another bindable field whenever this field
   *                         changes.
   */
  public <M> void bind(BindableField<M> target, Function<T, M> onChangeFunction) {
    notifyMap.put(target, onChangeFunction);
  }

  /**
   * Bind this field with another field, whenever this filed changes, another
   *  field will change too, and vise versa. The new value of another field
   *  is equal to the new value of this field.
   *
   * @param target another bindable field.
   *
   */
  public void bidirectionalBind(BindableField<T> target) {
    notifyMap.put(target, (newValue) -> newValue);
    target.bind(this);
  }

  /**
   * Bind this field with another field, whenever this filed changes, another
   *  field will change too, and vise versa.
   *
   * @param target another bindable field.
   * @param onChangeFunction the function to caculate the new value for
   *                         another bindable field whenever this field
   *                         changes.
   * @param oppositeOnChangeFunction the function to caculate the new value for
   *                             this bindable field whenever another field
   *                             changes.
   */
  public <M> void bidirectionalBind(BindableField<M> target,
                                    Function<T, M> onChangeFunction,
                                    Function<M, T> oppositeOnChangeFunction) {
    notifyMap.put(target, onChangeFunction);
    target.bind(this, oppositeOnChangeFunction);
  }

  protected void beforePropertyChanged(T newValue) {
    block();
    notifyMap.forEach((x, y) -> x.setValue(y.apply(newValue)));
    unBlock();
  }

  protected void block() {
    blockPropagation = true;
  }

  protected void unBlock() {
    blockPropagation = false;
  }

  /**
   * Set the value of this property, the implementations may depends
   *  on the property itself.
   */
  public abstract void setValue(T newValue);

  /**
   * Get the value of this property, the implementations may depends
   *  on the property itself.
   */
  public abstract T getValue();


}
