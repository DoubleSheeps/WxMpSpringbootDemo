package com.example.shirodemo.modules.sys.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.shirodemo.Utils.TreeNode;
import lombok.Data;

import java.util.List;

@Data
@TableName("type_tree")
public class CourseType implements TreeNode {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer parentId;

    @TableField(exist = false)
    private List<CourseType> children;

    @Override
    public Object id() {
        return this.id;
    }

    @Override
    public Object parentId() {
        return this.parentId;
    }

    @Override
    public Boolean root() {
        return this.id==0;
    }

    @Override
    public List<? extends TreeNode> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }
}
