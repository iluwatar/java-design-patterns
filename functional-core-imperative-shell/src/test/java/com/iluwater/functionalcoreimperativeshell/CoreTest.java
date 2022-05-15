package com.iluwater.functionalcoreimperativeshell;

import com.iluwatar.functionalcoreimperativeshell.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;


/** 
* Core Tester. 
* 
* @author inkfin
* @since <pre>May 15, 2022</pre> 
* @version 1.0 
*/ 
public class CoreTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: createDraft(String title, String body)
    *
    */
    @Test
    public void testCreateDraft() throws Exception {

        Article article = Core.createDraft(
                "The Game Awards crowns The Legend of Zelda...",
                "The Game Awards 2017 The 17 biggest trailers and announcements..."
        );

        assertEquals(article.getClass(), DraftArticle.class);
        assertEquals(article.getTitle(), "The Game Awards crowns The Legend of Zelda...");
        assertEquals(article.getBody(), "The Game Awards 2017 The 17 biggest trailers and announcements...");
    }

    /**
    *
    * Method: publishDraft(DraftArticle draftArticle)
    *
    */
    @Test
    public void testPublishDraft() throws Exception {

        DraftArticle draftArticle = new DraftArticle(
                "The Game Awards crowns The Legend of Zelda...",
                "The Game Awards 2017 The 17 biggest trailers and announcements..."
        );

        Article article = Core.publishDraft(draftArticle);

        assertEquals(article.getClass(), PublishArticle.class);
        assertEquals(article.getTitle(), "The Game Awards crowns The Legend of Zelda...");
        assertEquals(article.getBody(), "The Game Awards 2017 The 17 biggest trailers and announcements...");
    }

    /**
    *
    * Method: revertPublish(PublishArticle publishArticle)
    *
    */
    @Test
    public void testRevertPublish() throws Exception {
        PublishArticle publishArticle = new PublishArticle(
                "The Game Awards crowns The Legend of Zelda...",
                "The Game Awards 2017 The 17 biggest trailers and announcements..."
        );

        Article article = Core.revertPublish(publishArticle);

        assertEquals(article.getClass(), DraftArticle.class);
        assertEquals(article.getTitle(), "The Game Awards crowns The Legend of Zelda...");
        assertEquals(article.getBody(), "The Game Awards 2017 The 17 biggest trailers and announcements...");
    }

} 
