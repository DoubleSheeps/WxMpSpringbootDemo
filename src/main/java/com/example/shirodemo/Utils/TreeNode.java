package com.example.shirodemo.Utils;

import java.util.List;
/**
 * 树节点接口，所欲需要使用{@linkplain TreeUtils}工具类生成树形结构等操作的节点都需要实现该接口
 *
 * @author wanglin
 * @version 1.0
 * @date 2022-03-11 周五
 */
public interface TreeNode<T> {

    /**
     * 树节点id
     *
     * @return
     */
    T id();

    /**
     * 获取该节点的父节点id
     *
     * @return
     */
    T parentId();

    /**
     * 是否是根节点
     *
     * @return
     */
    Boolean root();

    /**
     * 设置节点的子节点列表
     *
     * @param children
     */
    void setChildren(List<? extends TreeNode<T>> children);

    /**
     * 获取所有子节点
     *
     * @return
     */
    List<? extends TreeNode<T>> getChildren();
}