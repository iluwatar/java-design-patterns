package entity;

import java.util.Date;

/**
 * [Project]:moy-gradle-project  <br/>
 * [Email]:moy25@foxmail.com  <br/>
 * [Date]:2018/2/20  <br/>
 * [Description]:  <br/>
 *
 * @author YeXiangYang
 */
public class TestEntity {
    private Integer id;
    private Date createTime;
    private Date modifyTime;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", content='" + content + '\'' +
                '}';
    }
}
