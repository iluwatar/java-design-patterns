package com.iluwatar.flux.store;

import com.iluwatar.flux.action.Content;
import com.iluwatar.flux.action.ContentAction;
import com.iluwatar.flux.action.MenuAction;
import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.view.View;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/12/15 - 10:18 PM
 *
 * @author Jeroen Meulemeester
 */
public class ContentStoreTest {

  @Test
  public void testOnAction() throws Exception {
    final ContentStore contentStore = new ContentStore();

    final View view = mock(View.class);
    contentStore.registerView(view);

    verifyZeroInteractions(view);

    // Content should not react on menu action ...
    contentStore.onAction(new MenuAction(MenuItem.PRODUCTS));
    verifyZeroInteractions(view);

    // ... but it should react on a content action
    contentStore.onAction(new ContentAction(Content.COMPANY));
    verify(view, times(1)).storeChanged(eq(contentStore));
    verifyNoMoreInteractions(view);
    assertEquals(Content.COMPANY, contentStore.getContent());

  }

}
