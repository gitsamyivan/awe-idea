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
public class AdminUserSSOInfo implements Serializable {
    private static final long serialVersionUID = -4376551181121763540L;

    private String client;
    private String sessionId;
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
