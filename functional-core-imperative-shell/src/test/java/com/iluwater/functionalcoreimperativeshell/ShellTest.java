package com.iluwater.functionalcoreimperativeshell;

import com.iluwatar.functionalcoreimperativeshell.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.*;

/** 
* Shell Tester. 
* 
* @author inkfin
* @since <pre>May 15, 2022</pre> 
* @version 1.0 
*/ 
public class ShellTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: reviewArticle(DraftArticle draftArticle)
    *
    */
    @Test
    public void testReviewArticle() throws Exception {
        Article article = new DraftArticle(
                "The Game Awards crowns The Legend of Zelda...",
                "The Game Awards 2017 The 17 biggest trailers and announcements..."
        );

        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("y\n".getBytes(StandardCharsets.US_ASCII)));

        article = Shell.reviewArticle((DraftArticle) article);

        System.setIn(stdin);

        assertEquals(article.getClass(), PublishArticle.class);
        assertEquals(article.getTitle(), "The Game Awards crowns The Legend of Zelda...");
        assertEquals(article.getBody(), "The Game Awards 2017 The 17 biggest trailers and announcements...");
    }

    /**
     *
     * Method: reviewArticle(DraftArticle draftArticle)
     *
     */
    @Test
    public void testReviewArticle1() throws Exception {
        Article article = new DraftArticle(
                "The Game Awards crowns The Legend of Zelda...",
                "The Game Awards 2017 The 17 biggest trailers and announcements..."
        );

        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("n\n".getBytes(StandardCharsets.US_ASCII)));

        article = Shell.reviewArticle((DraftArticle) article);

        System.setIn(stdin);

        assertEquals(article.getClass(), DraftArticle.class);
        assertEquals(article.getTitle(), "The Game Awards crowns The Legend of Zelda...");
        assertEquals(article.getBody(), "The Game Awards 2017 The 17 biggest trailers and announcements...");
    }

} 
