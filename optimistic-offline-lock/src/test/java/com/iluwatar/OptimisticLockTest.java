package com.iluwatar;

import com.iluwatar.exception.ApplicationException;
import com.iluwatar.model.Card;
import com.iluwatar.repository.JpaRepository;
import com.iluwatar.service.CardUpdateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

@SuppressWarnings({"rawtypes", "unchecked"})
public class OptimisticLockTest {

  private CardUpdateService cardUpdateService;

  private JpaRepository cardRepository;

  @BeforeEach
  public void setUp() {
    cardRepository = Mockito.mock(JpaRepository.class);
    cardUpdateService = new CardUpdateService(cardRepository);
  }

  @Test
  public void shouldNotUpdateEntityOnDifferentVersion() {
    int initialVersion = 1;
    long cardId = 123L;
    Card card = Card.builder()
        .id(cardId)
        .version(initialVersion)
        .sum(123f)
        .build();
    when(cardRepository.findById(eq(cardId))).thenReturn(card);
    when(cardRepository.getEntityVersionById(Mockito.eq(cardId))).thenReturn(initialVersion + 1);

    Assertions.assertThrows(ApplicationException.class,
        () -> cardUpdateService.doUpdate(card, cardId));
  }

  @Test
  public void shouldUpdateOnSameVersion() {
    int initialVersion = 1;
    long cardId = 123L;
    Card card = Card.builder()
        .id(cardId)
        .version(initialVersion)
        .sum(123f)
        .build();
    when(cardRepository.findById(eq(cardId))).thenReturn(card);
    when(cardRepository.getEntityVersionById(Mockito.eq(cardId))).thenReturn(initialVersion);

    cardUpdateService.doUpdate(card, cardId);

    Mockito.verify(cardRepository).update(Mockito.any());
  }
}
