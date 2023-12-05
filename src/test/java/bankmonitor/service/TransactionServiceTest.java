package bankmonitor.service;

import bankmonitor.converter.TransactionDtoConverter;
import bankmonitor.exception.NoEntityFoundException;
import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    private TransactionService transactionService;

    @BeforeEach
    public void init() {
        this.transactionService = new TransactionService(
                new TransactionDtoConverter(),
                transactionRepository
        );
    }

    @Test
    void test_findAllTransactions() {
        mockRepositoryFindAllForFinding();

        var transactionDtos = transactionService.findAllTransactions();
        assertEquals(1, transactionDtos.size());
        var transactionDto = transactionDtos.get(0);

        assertEquals(-2, transactionDto.getId());
        assertEquals(-1, transactionDto.getAmount());
        assertEquals("", transactionDto.getReference());
        assertNotNull(transactionDto.getTimestamp());
    }

    @Test
    void test_createTransaction() {
        mockForReturningSaved();
        transactionService.createTransaction("{\"foo\": \"bar\"}");

        verify(transactionRepository)
                .save(transactionCaptor.capture());

        var transaction = transactionCaptor.getValue();

        assertEquals(-2, transaction.getId());
        assertEquals(-1, transaction.getAmount());
        assertEquals("", transaction.getReference());
        assertNotNull(transaction.getTimestamp());
    }

    @Test
    void updateExistingTransaction() {
        mockForReturningSaved();
        mockRepositoryFindByIdForFinding();

        transactionService.updateTransaction(-2L, "{\"amount\": 789, \"reference\": \"DUMMY-REF\"}");

        verify(transactionRepository)
                .save(transactionCaptor.capture());

        var transaction = transactionCaptor.getValue();

        assertEquals(-2, transaction.getId());
        assertEquals(789, transaction.getAmount());
        assertEquals("DUMMY-REF", transaction.getReference());
        assertNotNull(transaction.getTimestamp());
    }

    @Test
    void test_updateNonExistingTransaction() {
        mockRepositoryFindByIdForNotFinding();

        var exception = assertThrowsExactly(
                NoEntityFoundException.class,
                () -> transactionService.updateTransaction(-2L, "{\"amount\": 789, \"reference\": \"DUMMY-REF\"}")
        );

        assertEquals("No transaction with id -2.", exception.getMessage());
    }

    private void mockRepositoryFindAllForFinding() {
        when(transactionRepository.findAll())
                .thenReturn(List.of(makeMockEntity()));
    }

    private void mockRepositoryFindByIdForFinding() {
        when(transactionRepository.findById(any()))
                .thenReturn(Optional.of(makeMockEntity()));
    }

    private void mockRepositoryFindByIdForNotFinding() {
        when(transactionRepository.findById(any()))
                .thenReturn(Optional.empty());
    }

    private Transaction makeMockEntity() {
        var transaction = new Transaction("{\"foo\": \"bar\"}");
        transaction.setId(-2L);

        return transaction;
    }

    private void mockForReturningSaved() {
        when(transactionRepository.save(any()))
                .thenAnswer(answer -> {
                    var transaction = (Transaction) answer.getArgument(0);
                    transaction.setId(-2L);

                    return transaction;
                });
    }

}