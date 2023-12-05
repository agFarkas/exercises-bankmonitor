package bankmonitor.converter;

import bankmonitor.model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionDtoConverterTest {

    private TransactionDtoConverter transactionDtoConverter;

    @BeforeAll
    public void init() {
        this.transactionDtoConverter = new TransactionDtoConverter();
    }

    @Test
    void test_convert() {
        var transaction = new Transaction("{\"foo\": \"bar\"}");
        transaction.setId(-2L);

        var transactionDto = transactionDtoConverter.convert(transaction);

        assertEquals(-2, transactionDto.getId());
        assertNotNull(transactionDto.getTimestamp());
        assertEquals(-1, transactionDto.getAmount());
        assertEquals("", transactionDto.getReference());
    }
}