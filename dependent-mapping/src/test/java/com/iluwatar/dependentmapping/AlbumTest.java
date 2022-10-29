package com.iluwatar.dependentmapping;

import com.iluwatar.dependentmapping.Album;
import com.iluwatar.dependentmapping.Track;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @org.junit.jupiter.api.Test
    void addTrack() {
        Album album=new Album((long)1,"testAlbum");
        assertEquals(0,album.getDepObjs().length);
        Track track1=new Track("testTrack1");
        album.addDepObj(track1);
        assertEquals(1,album.getDepObjs().length);
        Track track2=new Track("testTrack1");
        album.addDepObj(track2);
        assertEquals(2,album.getDepObjs().length);
    }

    @org.junit.jupiter.api.Test
    void removeTrack() {
        Album album=new Album((long)1,"testAlbum");
        assertEquals(0,album.getDepObjs().length);
        Track track1=new Track("testTrack1");
        album.addDepObj(track1);
        assertEquals(1,album.getDepObjs().length);
        Track track2=new Track("testTrack1");
        album.addDepObj(track2);
        assertEquals(2,album.getDepObjs().length);
        album.removeDepObj(0);
        assertEquals(1,album.getDepObjs().length);
        assertEquals(track2,album.getDepObjs()[0]);
        album.removeDepObj(track2);
        assertEquals(0,album.getDepObjs().length);
    }

    @org.junit.jupiter.api.Test
    void getDepObjs() {
        Album album=new Album((long)1,"testAlbum");
        assertNotNull(album.getDepObjs());
    }

    @org.junit.jupiter.api.Test
    void getTracks() {
        Album album=new Album((long)1,"testAlbum");
        assertNotNull(album.getDepObjs());
    }

    @org.junit.jupiter.api.Test
    void getId() {
        Album album=new Album((long)1,"testAlbum");
        assertEquals((long) 1,album.getId());
    }
}