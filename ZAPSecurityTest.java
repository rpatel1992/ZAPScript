import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

public class ZapScan {
    private static final String ZAP_ADDRESS = "localhost";
    private static final int ZAP_PORT = 8080;
    private static final String ZAP_API_KEY = "irmje6k7mttb7dlqmvfvoeqvij";

    public static void main(String[] args) {
        ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);
        String targetUrl = ""https://www.mrtesting.com/";

        try {
            // Start the passive scan
            System.out.println("Accessing target: " + targetUrl);
            api.accessUrl(targetUrl);

            // Start an active scan
            System.out.println("Starting active scan...");
            String scanId = api.ascan.scan(targetUrl, "true", "false", null, null, null);

            // Monitor the scan progress
            int progress;
            do {
                Thread.sleep(5000);
                progress = Integer.parseInt(api.ascan.status(scanId));
                System.out.println("Scan progress: " + progress + "%");
            } while (progress < 100);

            System.out.println("Scan complete!");

            // Retrieve and print the results
            String report = new String(api.core.htmlreport());
            System.out.println(report);
        } catch (ClientApiException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
