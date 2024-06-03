package bitget;

import io.github.cdimascio.dotenv.Dotenv;
import com.bitget.openapi.common.client.BitgetRestClient;
import com.bitget.openapi.common.domain.ClientParameter;
import com.bitget.openapi.common.enums.SupportedLocaleEnum;

public class App {

    private OrderService orderService;

    public void run() throws Exception {
        Dotenv dotenv = Dotenv.configure().directory(System.getProperty("user.dir")).load();
        BitgetRestClient bitgetRestClient = createBitgetRestClient(dotenv);

        // Initialize OrderService with the manually created BitgetRestClient
        orderService = new OrderService(bitgetRestClient);
        System.out.println("cronBitGet Buying futures ...");

        orderService.stopBuyDemoFutures();
    }

    private BitgetRestClient createBitgetRestClient(Dotenv dotenv) throws Exception {
        String apiKey = dotenv.get("APIKEY");
        String secretKey = dotenv.get("SECRETKEY");
        String passphrase = dotenv.get("PASSPHRASE");

        ClientParameter parameter = ClientParameter.builder()
            .apiKey(apiKey)
            .secretKey(secretKey)
            .passphrase(passphrase)
            .baseUrl("https://api.bitget.com")
            .locale(SupportedLocaleEnum.EN_US.getName()).build();
        return BitgetRestClient.builder().configuration(parameter).build();
    }
}
