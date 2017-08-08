package tlb.mall.entity.sys;



import tlb.mall.common.util.enums.FromSystemEnum;
import tlb.mall.common.util.enums.UserStatus;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {

    private static final long serialVersionUID = 8417035316301812019L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 自增id

    private String userName;// 用户名
    private String password;// 密码
    private String realName;// 真实姓名
    private UserStatus status;// 账户状态
    private String openId;// 微信openid
    private FromSystemEnum fromSystem;// 来源哪个系统。(后期会进行与第三方系统做用户数据对接，默认为当前系统用户中心)

    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public FromSystemEnum getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(FromSystemEnum fromSystem) {
        this.fromSystem = fromSystem;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}