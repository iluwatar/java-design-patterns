package com.iluwatar.service;

import com.iluwatar.api.UpdateService;
import com.iluwatar.exception.ApplicationException;
import com.iluwatar.model.Card;
import com.iluwatar.repository.JpaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service to update {@link Card} entity.
 */
@RequiredArgsConstructor
public class CardUpdateService implements UpdateService<Card> {

  private final JpaRepository<Card> cardJpaRepository;

  @Override
  public Card doUpdate(Card obj, long id) {
    float additionalSum = obj.getSum();
    Card cardToUpdate = cardJpaRepository.findById(id);
    int initialVersion = cardToUpdate.getVersion();
    float resultSum = cardToUpdate.getSum() + additionalSum;
    cardToUpdate.setSum(resultSum);
    //Maybe more complex business-logic e.g. HTTP-requests and so on

    if (initialVersion != cardJpaRepository.getEntityVersionById(id)) {
      String exMessage =
          String.format("Entity with id %s were updated in another transaction", id);
      throw new ApplicationException(exMessage);
    }

    cardJpaRepository.update(cardToUpdate);
    return cardToUpdate;
  }
}
