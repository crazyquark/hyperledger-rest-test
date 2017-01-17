package hello;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChaincodeData {
	private String jsonrpc = "2.0";
	private String method = "deploy";
	
	@NonNull 
	private PostParams params;
	
	@NonNull
	private String id;
}
