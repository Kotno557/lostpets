package br.lostpets.project.model.valueobject;

import org.junit.Test;
import static org.junit.Assert.*;

public class PhoneNumberTest {

    @Test
    public void testValidMobilePhone() {
        PhoneNumber phone = new PhoneNumber("11987654321");
        assertEquals("11987654321", phone.getValue());
        assertEquals(PhoneNumber.PhoneType.MOBILE, phone.getType());
        assertTrue(phone.isMobile());
        assertFalse(phone.isLandline());
        assertEquals("(11) 98765-4321", phone.getFormatted());
    }

    @Test
    public void testValidLandlinePhone() {
        PhoneNumber phone = new PhoneNumber("1133334444");
        assertEquals("1133334444", phone.getValue());
        assertEquals(PhoneNumber.PhoneType.LANDLINE, phone.getType());
        assertTrue(phone.isLandline());
        assertFalse(phone.isMobile());
        assertEquals("(11) 3333-4444", phone.getFormatted());
    }

    @Test
    public void testPhoneWithFormatting() {
        PhoneNumber phone = new PhoneNumber("(11) 98765-4321");
        assertEquals("11987654321", phone.getValue());
        assertEquals("(11) 98765-4321", phone.getFormatted());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPhoneFormat() {
        new PhoneNumber("123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPhone() {
        new PhoneNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyPhone() {
        new PhoneNumber("");
    }

    @Test
    public void testPhoneEquality() {
        PhoneNumber phone1 = new PhoneNumber("11987654321");
        PhoneNumber phone2 = new PhoneNumber("(11) 98765-4321");
        assertEquals(phone1, phone2);
    }

    @Test
    public void testPhoneToString() {
        PhoneNumber phone = new PhoneNumber("11987654321");
        assertEquals("(11) 98765-4321", phone.toString());
    }
}
