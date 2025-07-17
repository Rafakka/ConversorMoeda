import java.time.LocalDateTime;

public class ConversionRecord {
    private String from;
    private String to;
    private double originalAmount;
    private double convertedAmount;
    private double rate;
    private LocalDateTime timestamp;

    public ConversionRecord(String from, String to, double originalAmount,
                            double convertedAmount, double rate, LocalDateTime timestamp) {
        this.from = from;
        this.to = to;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.rate = rate;
        this.timestamp = timestamp;
    }
} 