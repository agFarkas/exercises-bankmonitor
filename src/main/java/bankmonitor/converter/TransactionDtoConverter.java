package bankmonitor.converter;

import bankmonitor.model.Transaction;
import generated.locations.model.TransactionDto;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoConverter extends AbstractConverter<Transaction, TransactionDto> {

    @Override
    public TransactionDto convert(@NonNull Transaction transaction) {
        return new TransactionDto()
                .id(transaction.getId())
                .timestamp(transaction.getTimestamp())
                .data(transaction.getData())
                .reference(transaction.getReference())
                .amount(transaction.getAmount());
    }
}
