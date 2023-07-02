package com.example.shirodemo.modules.sys.controller.VO;

import com.example.shirodemo.Utils.TreeNode;

import java.util.List;
public class Permission implements TreeNode {
    private Integer id;
    private Integer parentId;
    private String permission;
    private String label;
    private Boolean disabled;
    private List<Permission> childPermission;

    @Override
    public Object id() {
        return id;
    }

    @Override
    public Object parentId() {
        return parentId;
    }

    @Override
    public Boolean root() {
        return id==0;
    }

    @Override
    public List<? extends TreeNode> getChildren() {
        return childPermission;
    }

    @Override
    public void setChildren(List children) {
        this.childPermission = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setChildPermission(List<Permission> childPermission) {
        this.childPermission = childPermission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", permission='" + permission + '\'' +
                ", childPermission=" + childPermission +
                '}';
    }
}
