package bankmonitor.service;

import bankmonitor.converter.TransactionDtoConverter;
import bankmonitor.exception.NoEntityFoundException;
import bankmonitor.json.JsonObject;
import bankmonitor.model.Transaction;
import bankmonitor.repository.TransactionRepository;
import generated.locations.model.TransactionDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static bankmonitor.model.Transaction.AMOUNT_KEY;
import static bankmonitor.model.Transaction.REFERENCE_KEY;

@AllArgsConstructor
@Transactional
@Service
public class TransactionService {

    private static final String NO_TRANSACTION_WITH_ID__ERROR_MESSAGE_PATTERN = "No transaction with id %s.";

    private final TransactionDtoConverter transactionDtoConverter;

    private final TransactionRepository transactionRepository;

    public TransactionDto updateTransaction(Long id, String updateJson) {
        var updateData = new JsonObject(updateJson);

        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NoEntityFoundException(NO_TRANSACTION_WITH_ID__ERROR_MESSAGE_PATTERN.formatted(id)));

        var transactionData = makeTransactionData(updateData, transaction.getData());
        transaction.setData(transactionData.toString());

        return transactionDtoConverter.convert(
                transactionRepository.save(transaction)
        );
    }

    public TransactionDto createTransaction(String json) {
        var transaction = transactionRepository.save(new Transaction(json));

        return transactionDtoConverter.convert(transaction);
    }

    public List<TransactionDto> findAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionDtoConverter::convert)
                .collect(Collectors.toList());
    }

    private JsonObject makeTransactionData(JsonObject updateData, String transactionDataJson) {
        var transactionData = new JsonObject(transactionDataJson);

        updateData.copyIfExists(transactionData, AMOUNT_KEY, JsonObject::getInt);
        updateData.copyIfExists(transactionData, REFERENCE_KEY, JsonObject::getString);

        return transactionData;
    }
}
