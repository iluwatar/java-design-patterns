package org.dependent.mapping;

import com.iluwatar.dependentmapping.Track;
import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

    @org.junit.jupiter.api.Test
    void getTitle() {
        Track track=new Track("testTitle");
        assertNotNull(track.getTitle());
        assertEquals("testTitle",track.getTitle());
    }

}