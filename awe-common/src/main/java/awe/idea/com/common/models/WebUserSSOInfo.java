package awe.idea.com.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *公共redis数据类型，反序列化通用
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebUserSSOInfo implements Serializable {
    private static final long serialVersionUID = 7776502101140282634L;

    private String client;
    private String platform;
    private String version;
    private String sessionId;
    private String mobile;
    private String username;
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
