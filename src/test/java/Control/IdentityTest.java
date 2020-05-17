package Control;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

class IdentityTest {
    String identity1 = Identity.getId();
    String identity2 = Identity.getId();

    @Test
    public void testIdentity(){
        System.out.println(identity1);
        assertNotEquals(identity1,identity2);
    }
}