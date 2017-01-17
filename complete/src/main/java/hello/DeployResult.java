package hello;

import lombok.Data;

@Data
public class DeployResult {
	@Data
	class DeployStatus {
		private String status;
		private String message;
	}
	
	private String jsonrpc;
	private DeployStatus result;
}
