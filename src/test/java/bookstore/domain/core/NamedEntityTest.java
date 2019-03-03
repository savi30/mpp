package bookstore.domain.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NamedEntityTest {
    private NamedEntity<Long> entity = new NamedEntity<Long>();

    @Before
    public void setUp() {
        long ID = 3;
        entity.setId(ID);
        entity.setName("Init");
    }

    @After
    public void tearDown() {
        entity = null;
    }

    @Test
    public void getNameTest() {
        assertEquals("Names should be the same", entity.getName(), "Init");
    }

    @Test
    public void setNameTest() {
        entity.setName("Changed");
        assertEquals("Names should be the same", entity.getName(), "Changed");
    }
}