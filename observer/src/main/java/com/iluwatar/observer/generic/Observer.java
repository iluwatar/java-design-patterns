package com.iluwatar.observer.generic;

/**
 * 
 * Observer
 *
 * @param <S>
 * @param <O>
 * @param <A>
 */
public interface Observer<S extends Observable<S, O, A>, O extends Observer<S, O, A>, A> {

    void update(S subject, A argument);
}
