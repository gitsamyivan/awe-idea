package awe.idea.com.api.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken implements Serializable {
    private static final long serialVersionUID = 917327462689406281L;
    private String platform;
    private String client;
    private String version;
    private String username;
    private String token;
}
