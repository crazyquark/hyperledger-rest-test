package hello;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeployParams implements PostParams {
	private int type = 1;
	
	@NonNull
	private ChaincodeID chaincodeID;
	
	@NonNull
	private ConstructorMsg ctorMsg;
	
	@NonNull
	private String secureContext;
}
