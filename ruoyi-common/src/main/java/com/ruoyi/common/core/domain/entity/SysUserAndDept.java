package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
public class SysUserAndDept implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 部门名称
     */
    private String deptName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public SysUserAndDept(Long userId, String userName, String nickName, String deptName) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.deptName = deptName;
    }

    public SysUserAndDept() {
    }

    @Override
    public String toString() {
        return "SysUserAndDept{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", deptName=" + deptName +
                '}';
    }
}
