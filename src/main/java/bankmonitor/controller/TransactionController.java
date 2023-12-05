package bankmonitor.controller;

import bankmonitor.service.TransactionService;
import generated.locations.api.TransactionsApi;
import generated.locations.model.TransactionDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class TransactionController implements TransactionsApi {

    private final TransactionService transactionService;

    @GetMapping("/transactions")
    @ResponseBody
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.findAllTransactions());
    }

    @PostMapping("/transactions")
    @ResponseBody
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody String json) {
        return ResponseEntity.ok(transactionService.createTransaction(json));
    }

    @PutMapping("/transactions/{id}")
    @ResponseBody
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id, @RequestBody String updateJson) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, updateJson));
    }
}