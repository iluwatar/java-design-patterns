package com.iluwatar.flux.view;

import com.iluwatar.flux.action.Content;
import com.iluwatar.flux.store.ContentStore;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Date: 12/12/15 - 10:31 PM
 *
 * @author Jeroen Meulemeester
 */
public class ContentViewTest {

  @Test
  public void testStoreChanged() throws Exception {
    final ContentStore store = mock(ContentStore.class);
    when(store.getContent()).thenReturn(Content.PRODUCTS);

    final ContentView view = new ContentView();
    view.storeChanged(store);

    verify(store, times(1)).getContent();
    verifyNoMoreInteractions(store);
  }

}
