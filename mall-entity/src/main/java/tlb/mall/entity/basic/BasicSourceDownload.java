package tlb.mall.entity.basic;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 资源下载
 * 
 * 操作人: zhoubang 日期：2016年10月19日 上午11:58:13
 *
 */
public class BasicSourceDownload implements Serializable {

    private static final long serialVersionUID = -3311932572648905556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 自增id

    private String name;// 资源名称
    private String downUrl;// 下载地址
    private String iconName;// 图标
    private int sortNum;// 排序
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
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