package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			String res = restTemplate.postForObject("http://localhost:7050/registrar", new LoginData("jim", "6avZQLwcUe9b"), String.class);
			log.info("Login: " + res);
			
			DeployResult deployRes = restTemplate.postForObject("http://localhost:7050/chaincode", 
					new ChaincodeData("deploy", new ChaincodeParams(new ChaincodeID("github.com/hyperledger/fabric/examples/chaincode/go/chaincode_example02"), new ConstructorMsg(new String[]{"init", "a", "100", "b", "200"}), "jim"), "1"), DeployResult.class);
			String chaincodeID = deployRes.getResult().getMessage();
			log.info("Deploy: " + chaincodeID);
			
			String queryRes = restTemplate.postForObject("http://localhost:7050/chaincode",
					new ChaincodeData("query", new ChaincodeParams(new ChaincodeName(chaincodeID), new ConstructorMsg(new String[]{"query", "a"}), "jim"), "2"), String.class);
			log.info("Query: " + queryRes);
			
			String invokeRes = restTemplate.postForObject("http://localhost:7050/chaincode", new ChaincodeData("invoke",
					new ChaincodeParams(new ChaincodeName(chaincodeID), new ConstructorMsg(new String[]{"invoke", "a", "b", "10"}), "jim"), "3"), String.class);
			log.info("Invoke: " + invokeRes);
		};
	}
}