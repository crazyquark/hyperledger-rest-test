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
					new ChaincodeData(new DeployParams(new ChaincodeID("github.com/hyperledger/fabric/examples/chaincode/go/chaincode_example02"), new ConstructorMsg(new String[]{"init", "a", "100", "b", "200"}), "jim"), "1"), DeployResult.class);
			log.info("Deploy: " + deployRes.getResult().getMessage());
		};
	}
}