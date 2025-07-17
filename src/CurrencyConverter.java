import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class CurrencyConverter {

    private static final String CONFIG_FILE = "config.properties";
    private static final List<String> SUPPORTED_CURRENCIES = Arrays.asList(
        "USD", "EUR", "GBP", "JPY", "ARS", "BTC", "CAD", "CHF", "AUD", "CNY", "INR", "RUB", "ZAR", "MXN", "CLP"
    );

    private static final List<ConversionRecord> conversionRecords = new ArrayList<>();

    private static String loadApiKey() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            return props.getProperty("API_KEY");
        } catch (IOException ex) {
            System.err.println("Erro ao carregar a chave da API: " + ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String apiKey = loadApiKey();
            if (apiKey == null || apiKey.isEmpty()) {
                System.out.println("API key não encontrada. Verifique o arquivo config.properties.");
                return;
            }

            while (true) {
                System.out.println("\n=== Conversor de Moedas ===");
                for (int i = 0; i < SUPPORTED_CURRENCIES.size(); i++) {
                    System.out.printf("%2d - BRL para %s\n", i + 1, SUPPORTED_CURRENCIES.get(i));
                }
                System.out.println("99 - Ver histórico");
                System.out.println("0 - Sair e salvar histórico");
                System.out.print("Escolha uma opção: ");
                int choice = scanner.nextInt();

                if (choice == 0) {
                    saveHistoryToFile();
                    break;
                }
                if (choice == 99) {
                    showHistory();
                    continue;
                }

                if (choice < 1 || choice > SUPPORTED_CURRENCIES.size()) {
                    System.out.println("Escolha inválida.");
                    continue;
                }

                String targetCurrency = SUPPORTED_CURRENCIES.get(choice - 1);
                System.out.print("Digite o valor em BRL: ");
                double amount = scanner.nextDouble();

                convert(apiKey, amount, targetCurrency);
            }
        } finally {
            scanner.close();
        }
    }

    private static void convert(String apiKey, double amount, String targetCurrency) {
        String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/BRL";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String searchKey = "\"" + targetCurrency + "\":";
            int index = response.indexOf(searchKey);
            if (index == -1) {
                System.out.println("Moeda não encontrada na resposta da API.");
                return;
            }

            int start = index + searchKey.length();
            int end = response.indexOf(",", start);
            if (end == -1) end = response.indexOf("}", start);

            double rate = Double.parseDouble(response.substring(start, end));
            double converted = amount * rate;
            LocalDateTime timestamp = LocalDateTime.now();

            ConversionRecord record = new ConversionRecord(
                "BRL", targetCurrency, amount, converted, rate, timestamp
            );
            conversionRecords.add(record);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(record);
            System.out.println(jsonOutput);

        } catch (Exception e) {
            System.out.println("Erro ao buscar taxa de câmbio: " + e.getMessage());
        }
    }

    private static void showHistory() {
        System.out.println("\n=== Histórico de Conversões ===");
        if (conversionRecords.isEmpty()) {
            System.out.println("Nenhuma conversão registrada ainda.");
        } else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (ConversionRecord record : conversionRecords) {
                System.out.println(gson.toJson(record));
            }
        }
    }

    private static void saveHistoryToFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("historico.json")) {
            gson.toJson(conversionRecords, writer);
            System.out.println("✔ Histórico salvo em historico.json com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar histórico: " + e.getMessage());
        }
    }
}
