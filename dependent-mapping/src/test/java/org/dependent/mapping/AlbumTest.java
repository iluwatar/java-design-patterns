package org.dependent.mapping;

import com.iluwatar.denpendetmapping.Album;
import com.iluwatar.denpendetmapping.Track;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @org.junit.jupiter.api.Test
    void addTrack() {
        Album album=new Album((long)1,"testAlbum");
        assertEquals(0,album.getTracks().length);
        Track track1=new Track("testTrack1");
        album.addDepObj(track1);
        assertEquals(1,album.getTracks().length);
        Track track2=new Track("testTrack1");
        album.addDepObj(track2);
        assertEquals(2,album.getTracks().length);
    }

    @org.junit.jupiter.api.Test
    void removeTrack() {
        Album album=new Album((long)1,"testAlbum");
        assertEquals(0,album.getTracks().length);
        Track track1=new Track("testTrack1");
        album.addDepObj(track1);
        assertEquals(1,album.getTracks().length);
        Track track2=new Track("testTrack1");
        album.addDepObj(track2);
        assertEquals(2,album.getTracks().length);
        album.removeDepObj(0);
        assertEquals(1,album.getTracks().length);
        assertEquals(track2,album.getTracks()[0]);
        album.removeDepObj(track2);
        assertEquals(0,album.getTracks().length);
    }

    @org.junit.jupiter.api.Test
    void getDepObjs() {
        Album album=new Album((long)1,"testAlbum");
        assertNotNull(album.getDepObjs());
    }

    @org.junit.jupiter.api.Test
    void getTracks() {
        Album album=new Album((long)1,"testAlbum");
        assertNotNull(album.getTracks());
    }

    @org.junit.jupiter.api.Test
    void getId() {
        Album album=new Album((long)1,"testAlbum");
        assertEquals((long) 1,album.getId());
    }
}