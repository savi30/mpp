package bookstore.domain.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import bookstore.domain.core.Entity;
import static org.junit.Assert.*;

public class EntityTest {
    private Entity<Long> entity;
    private long ID = 1;

    @Before
    public void setUp() throws Exception {
        entity = new Entity<Long>();
        entity.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        entity = null;
    }

    @Test
    public void getIdTest() {
        assertEquals("IDs should be equal", (Long)ID, entity.getId());
    }

    @Test
    public void setIdTest() {
        ID = 2;
        entity.setId(ID);
        assertEquals("IDs should be equal", (Long)ID, entity.getId());
    }

}