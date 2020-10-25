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
public class ApiCaptchaInfo  implements Serializable {
    private static final long serialVersionUID = 8495744221073941965L;

    private String username;
    private String captcha;
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
