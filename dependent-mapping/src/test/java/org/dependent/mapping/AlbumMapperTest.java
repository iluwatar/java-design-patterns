package org.dependent.mapping;

import com.iluwatar.denpendentmapping.AlbumMapper;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AlbumMapperTest {

    @Test
    void findstatement() {
        Connection connection=null;
        AlbumMapper albumMapper=new AlbumMapper(connection);
        String str= albumMapper.findstatement();
        assertNotNull(str);
    }
}