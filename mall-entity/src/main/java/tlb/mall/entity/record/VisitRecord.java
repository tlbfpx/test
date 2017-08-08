package tlb.mall.entity.record;



import tlb.mall.common.util.enums.FromSystemEnum;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class VisitRecord implements Serializable {

    private static final long serialVersionUID = -2300573958701067777L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 自增id

    private String cliIp;// 客户端ip
    private String cliSysVersion;// 客户端系统版本
    private String cliSysArch;// 客户端操作系统位数
    private String cliSysName;// 客户端系统名称
    private String cliSysAgent;// 浏览器基本信息
    private String cliReqUrl;// 客户端发出请求时的完整URL
    private String cliReqUri;// 请求行中的资源名部分
    private String cliReqMethod;// 客户机请求方式
    private String localAddr;// WEB服务器的IP地址
    private String localName;// WEB服务器的主机名
    private String reqSessionId;// 当前会话请求的sessionId，用于判断当前会话是否是同一个会话，避免重复记录
    private String loginUserName;// 登录用户名
    private FromSystemEnum userFromSystem;// 用户来自哪个系统
    private Date createTime;// 来访时间
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliIp() {
        return cliIp;
    }

    public void setCliIp(String cliIp) {
        this.cliIp = cliIp;
    }

    public String getCliSysVersion() {
        return cliSysVersion;
    }

    public void setCliSysVersion(String cliSysVersion) {
        this.cliSysVersion = cliSysVersion;
    }

    public String getCliSysArch() {
        return cliSysArch;
    }

    public void setCliSysArch(String cliSysArch) {
        this.cliSysArch = cliSysArch;
    }

    public String getCliSysName() {
        return cliSysName;
    }

    public void setCliSysName(String cliSysName) {
        this.cliSysName = cliSysName;
    }

    public String getCliSysAgent() {
        return cliSysAgent;
    }

    public void setCliSysAgent(String cliSysAgent) {
        this.cliSysAgent = cliSysAgent;
    }

    public String getCliReqUrl() {
        return cliReqUrl;
    }

    public void setCliReqUrl(String cliReqUrl) {
        this.cliReqUrl = cliReqUrl;
    }

    public String getCliReqUri() {
        return cliReqUri;
    }

    public void setCliReqUri(String cliReqUri) {
        this.cliReqUri = cliReqUri;
    }

    public String getCliReqMethod() {
        return cliReqMethod;
    }

    public void setCliReqMethod(String cliReqMethod) {
        this.cliReqMethod = cliReqMethod;
    }

    public String getLocalAddr() {
        return localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getReqSessionId() {
        return reqSessionId;
    }

    public void setReqSessionId(String reqSessionId) {
        this.reqSessionId = reqSessionId;
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

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public FromSystemEnum getUserFromSystem() {
        return userFromSystem;
    }

    public void setUserFromSystem(FromSystemEnum userFromSystem) {
        this.userFromSystem = userFromSystem;
    }

}
