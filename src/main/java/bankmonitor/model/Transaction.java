package bankmonitor.model;

import bankmonitor.json.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    public static final String REFERENCE_KEY = "reference";
    public static final String AMOUNT_KEY = "amount";
    private static final String EMPTY_STRING = "";
    private static final String EMPTY_JSON = "{}";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionSequenceGenerator")
    @SequenceGenerator(name = "transactionSequenceGenerator", sequenceName = "transaction_sequence", allocationSize = 1, initialValue = 6)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime timestamp;

    @Column(name = "data")
    private String data;

    @Transient
    private JsonObject dataObject;

    public Transaction(String jsonData) {
        this.timestamp = LocalDateTime.now();
        setData(jsonData);
    }

    public void setData(String data) {
        this.data = StringUtils.hasText(data) ? data : EMPTY_JSON;
        setUpDataObject();
    }

    @Transient
    public Integer getAmount() {
        var jsonData = obtainDataObject();

        return jsonData.getInt(AMOUNT_KEY)
                .orElse(-1);
    }

    @Transient
    public String getReference() {
        var jsonData = obtainDataObject();

        return jsonData.getString(REFERENCE_KEY)
                .orElse(EMPTY_STRING);
    }

    private JsonObject obtainDataObject() {
        if (Objects.isNull(this.dataObject)) {
            setUpDataObject();
        }

        return dataObject;
    }

    private void setUpDataObject() {
        this.dataObject = new JsonObject(this.data);
    }
}