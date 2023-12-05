package bankmonitor.model;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class TransactionTest {

    @Test
    void test_getData() {
        var transaction = new Transaction("{ \"reference\": \"foo\", \"amount\": 100}");

        assertNotNull(transaction.getTimestamp());
        assertEquals("foo", transaction.getReference());
        assertEquals(100, transaction.getAmount());
    }

    @Test
    void test_getDataWithEmptyFields() {
        var transaction = new Transaction("{ }");

        assertNotNull(transaction.getTimestamp());
        assertEquals("", transaction.getReference());
        assertEquals(-1, transaction.getAmount());
    }

    @Test
    void test_getDataOfNull() {
        var transaction = new Transaction(null);

        assertNotNull(transaction.getTimestamp());
        assertEquals("", transaction.getReference());
        assertEquals(-1, transaction.getAmount());
    }

    @Test
    void test_getDataOfBlank() {
        var transaction = new Transaction(" ");

        assertNotNull(transaction.getTimestamp());
        assertEquals("", transaction.getReference());
        assertEquals(-1, transaction.getAmount());
    }

    @Test
    void test_setUpTransactionWithInvalidData() {
        assertThrowsExactly(JSONException.class, () -> new Transaction("{ \"reference\": \"foo\""));
    }

    @Test
    void test_updateDataWithValidData() {
        var transaction = new Transaction("{ \"reference\": \"foo\", \"amount\": 100}");

        assertNotNull(transaction.getTimestamp());
        assertEquals("foo", transaction.getReference());
        assertEquals(100, transaction.getAmount());

        var newData = "{ \"reference\": \"bar\", \"amount\": 3500}";
        transaction.setData(newData);

        assertEquals("bar", transaction.getReference());
        assertEquals(3500, transaction.getAmount());
    }

    @Test
    void test_updateDataWithEmptyData() {
        var transaction = new Transaction("{ \"reference\": \"foo\", \"amount\": 100}");

        assertNotNull(transaction.getTimestamp());
        assertEquals("foo", transaction.getReference());
        assertEquals(100, transaction.getAmount());

        String newData = "";
        transaction.setData(newData);

        assertEquals("", transaction.getReference());
        assertEquals(-1, transaction.getAmount());
    }

    @Test
    void test_updateDataWithiNalidData() {
        var transaction = new Transaction("{ \"reference\": \"foo\", \"amount\": 100}");

        assertNotNull(transaction.getTimestamp());
        assertEquals("foo", transaction.getReference());
        assertEquals(100, transaction.getAmount());

        var newData = "{ \"reference\": \"bar";
        assertThrowsExactly(JSONException.class, () -> transaction.setData(newData));
    }
}