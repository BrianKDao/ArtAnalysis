package Projects.Project07;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class TestArt {
    private static final double EQ_DELTA = .01;

    @Test
    public void testInstanceVarsExistAndArePrivate() {
        Art art = new Art();
        String[] priv_vars = {"name", "creator", "value", "location"};
        for (String var : priv_vars) {
            assertInstanceVariablePrivate(var, art);
            assertInstanceVariableNotStatic(var, art);
        }
    }

    // Test Default Constructor


    @Test
    public void testDefaultConstructorIsCorrect() {
        Art art = new Art();
        assertInstanceVariableIsExpected("name",
                art,
                null,
                "When we use the default constructor" + " we expect the "
                        + "name to be `null`"
        );
        assertInstanceVariableIsExpected("creator",
                art,
                null,
                "When we use the default " + "constructor " + "we" + " " + "expect the Creator to be 0"
        );
        assertInstanceVariableIsExpected("value",
                art,
                0.0,
                "When we use the default " + "constructor we" + " expect"
                        + " the Value to be 0"
        );
        assertInstanceVariableIsExpected("location",
                art,
                null,
                "When we use the default " + "constructor we" + " expect"
                        + " the location to be 0"
        );
    }

    // Test Parameterized Constructor
    @Test
    public void testParameterizedConstructorIsCorrect() {
        Art art = new Art("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, " + "Manhattan, New York City"
        );
        assertInstanceVariableIsExpected("name",
                art,
                "Starry Night",
                "When we use the " + "parameterized constructor we "
        );
        assertInstanceVariableIsExpected("creator",
                art,
                "Van Gogh",
                "When we use the " + "parameterized constructor we "
        );
        assertInstanceVariableIsExpected("value",
                art,
                120000000.25,
                "When we use the " + "parameterized constructor we "
        );
        assertInstanceVariableIsExpected("location",
                art,
                "11 West 53rd Street, Manhattan, New York City",
                "When we use the " + "parameterized constructor we "
        );
    }

    //Test setName(String) sets
    @Test
    public void testSetNameSetsValue() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, Manhattan, New York City"
        );

        art.setName("Cloudy Night");
        assertEquals("When we call setName() we ", "Cloudy Night", art.getName());
    }

    // Test setCreator(double) sets
    @Test
    public void testSetCreatorSetsValue() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, Manhattan, New York City"
        );
        art.setCreator("Vincent Van Gogh");
        assertEquals("When we call setCreator() we expect the Creator to be updated!",
                "Vincent Van Gogh",
                art.getCreator()
        );
    }

    // Test setValue(int)
    @Test
    public void testSetValueSetsValue() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, Manhattan, New York City"
        );
        art.setValue(983.20);
        assertEquals("When we call setValue() we expect the Value to be updated!",
                983.20,
                art.getValue(),
                EQ_DELTA
        );
    }

    // Test setLocation(double)
    @Test
    public void testSetLocationSetsValue() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, Manhattan, New York City"
        );
        art.setLocation("601 W. Broad Street Richmond, VA 23220");
        assertEquals(
                "When we call setLocation() we expect the location to be updated!",
                "601 W. Broad Street Richmond, VA 23220",
                art.getLocation()
        );
    }

    // Test getName(): String
    @Test
    public void testGetNameReturnsValue() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, Manhattan, New York City"
        );
        assertEquals("When we called art.getName() we ", "Starry Night", art.getName());
    }


    // Test getCreator(): double
    @Test
    public void testGetCreatorReturnsValue() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, Manhattan, New York City"
        );
        assertEquals("When we called art.getCreator() we ", "Van Gogh", art.getCreator());
    }

    // Test getValue(): double
    @Test
    public void testGetValueReturnsValue() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, Manhattan, New York City"
        );
        assertEquals("When we called art.getValue() we ", 120000000.25, art.getValue(), EQ_DELTA);
    }

    // Test getLocation(): Double
    @Test
    public void testGetLocationReturnsValue() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                120000000.25,
                "11 West 53rd Street, Manhattan, New York City"
        );
        assertEquals(
                "When we called art.getLocation we ",
                "11 West 53rd Street, Manhattan, New York City",
                art.getLocation()
        );
    }

    // Test toString(): String
    @Test
    public void testToStringReturnsExpectedValues() {
        Art art = createArt("Starry Night",
                "Van Gogh",
                1200.25,
                "11 West 53rd Street, Manhattan, New York City"
        );
        String[] expected_tok = ("Starry Night Van Gogh 1200.25 11 West 53rd Street, Manhattan, " +
                "New York City")
                .split(" ");
        String actual = art.toString();
        for (String each : expected_tok) {
            assertTrue(String.format(
                    "When we called toString, we expected the returned value to contain %s but " + "we" + " got %s",
                    each,
                    actual
            ), actual.contains(each));
        }
    }

    // Test equals(): boolean
    @Test
    public void testTwoObjectsWithSameNameAreEqual() {
        Art art1 = createArt("A Cup of Java", "Budwell", 2500, "Henrico");
        Art art2 = createArt("A Cup of Java", "Budwell", 250, "Richmond");
        Art art3 = createArt("A Mug of Cocoa", "Whitten", 25, "VCU");

        assertEquals("When we compare two cities with the same name, we expect them to be equal",
                art1,
                art2
        );
        assertNotEquals(
                "When we compare two cities with diffferent names, we expect to be " + "not equal",
                art1,
                art3
        );
    }

    private Art createArt(String name, String creator, double value, String location) {
        Art testArt = new Art();
        @SuppressWarnings("rawtypes") Class c = testArt.getClass();

        try {
            Field size = c.getDeclaredField("name");
            size.setAccessible(true);
            size.set(testArt, name);

            Field flavor = c.getDeclaredField("creator");
            flavor.setAccessible(true);
            flavor.set(testArt, creator);

            Field age = c.getDeclaredField("value");
            age.setAccessible(true);
            age.set(testArt, value);

            Field email = c.getDeclaredField("location");
            email.setAccessible(true);
            email.set(testArt, location);

        } catch (Exception e) {
            fail(e.toString());
        }

        return testArt;
    }

    private void assertInstanceVariablePrivate(String aField, Object testObject) {
        @SuppressWarnings("rawtypes") Class c = testObject.getClass();
        try {
            c.getDeclaredField(aField);

            assertTrue(String.format(
                    "You must make your instance variables private: `%s.%s` is not private",
                    c.getSimpleName(),
                    aField
            ), Modifier.isPrivate(c.getDeclaredField(aField).getModifiers()));
        } catch (NoSuchFieldException e) {
            fail("Could not find the " + e.getLocalizedMessage() + " instance variable");
        } catch (Exception e) {
            fail("Something weird went wrong");
        }
    }

    private void assertInstanceVariableNotStatic(String aField, Object testObject) {
        @SuppressWarnings("rawtypes") Class c = testObject.getClass();
        try {
            c.getDeclaredField(aField);

            assertFalse(String.format(
                    "Your instance variables must NOT be static: `%s.%s` is static!",
                    c.getSimpleName(),
                    aField
            ), Modifier.isStatic(c.getDeclaredField(aField).getModifiers()));
        } catch (NoSuchFieldException e) {
            fail("Could not find the " + e.getLocalizedMessage() + " instance variable");
        } catch (Exception e) {
            fail("Something weird went wrong");
        }
    }

    private void assertInstanceVariableIsExpected(String aField,
                                                  Object testObject,
                                                  Object expected,
                                                  String message) {
        @SuppressWarnings("rawtypes") Class c = testObject.getClass();
        try {
            Field field = c.getDeclaredField(aField);
            field.setAccessible(true);
            Object fieldValue = field.get(testObject);

            if (expected == null) {
                assertNull(message, fieldValue);
            }
            //If class is a double we have a special Junit assert to run
            else if (expected.getClass().equals(Double.class)) {
                double doubleFieldValue = (double) fieldValue;
                double doubleExpected = (double) expected;
                assertEquals(message, doubleExpected, doubleFieldValue, .01);
            }
            //Array of some kind yay
            else if (expected.getClass().isArray()) {

            } else {
                assertEquals(message, expected, fieldValue);
            }

        } catch (Exception e) {
            fail(e.toString());
        }
    }

    private Object getPrivateInstanceVariable(String varName,
                                              Object o) throws NoSuchFieldException {
        Class c = o.getClass();
        Field field = c.getDeclaredField(varName);
        field.setAccessible(true);
        try {
            return field.get(o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Something weird happened:\n" + e.getLocalizedMessage());
        }


    }
}

