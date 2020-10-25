package awe.idea.com.service.constants;

/**
 * 静态数据写在service，可单独模块使用。
 */
public class Constants {
    /** 超级管理员ID */
    public static final int SUPER_ADMIN = 1;

    public static final Integer NORMAL_USE = 1; 	//正常使用
    public static final Integer LIMIT_USE = 2;		//限制使用

    //1客户充值  2账户消耗  3站长赚取 4站长结算
    public static final Integer CUSTOMER_EDPOST = 1;
    public static final Integer CUSTOMER_COST = 2;
    public static final Integer DOMAIN_MASTER_OWN = 3;
    public static final Integer DOMAIN_MASTER_SUB = 4;

    //3站长 4客户
    public static final Integer DOMAIN_MASTER = 3;
    public static final Integer CUSTOMER = 4;

}
