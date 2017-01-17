package hello;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class LoginData {
	private String enrollId;
	private String enrollSecret;	
}
