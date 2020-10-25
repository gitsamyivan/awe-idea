package awe.idea.com.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserSSOInfo implements Serializable {
    private static final long serialVersionUID = 3340717704644375508L;

    private String client;
    private String username;
    private String mobile;
    private String token;
    private String platform;
    private String version;
    private Date createTime;
    private Date expireTime;

    public boolean isExpired(){
        if(this.expireTime == null){
            return true;
        }
        Date now = new Date();
        if(now.after(expireTime)){
            return  true;
        }
        return false;
    }
}
