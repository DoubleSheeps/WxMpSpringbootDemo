package com.example.shirodemo.Utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 生成树形结构的工具类
 *
 * @author wanglin
 * @version 1.0
 * @date 2022-03-11 周五
 */
public class TreeUtils {

    /**
     * 根据所有树节点列表，生成含有所有树形结构的列表
     *
     * @param nodes
     * @param <T>
     * @return
     */
    public static <T extends TreeNode<?>> List<T> generateTrees(List<T> nodes) {
        List<T> roots = new ArrayList<>();
        for (Iterator<T> item = nodes.iterator(); item.hasNext(); ) {
            T node = item.next();
            //如果是根节点
            if (node.root()) {
                roots.add(node);
                //从所有节点列表中删除该节点，以免后续重复遍历该节点
                item.remove();
            }
        }
        roots.forEach(e -> setChildren(e, nodes));
        return roots;
    }

    /**
     * 从所有节点列表中查找并设置parent的所有子节点
     *
     * @param parent 父节点
     * @param nodes  所有树节点列表
     * @param <T>
     */
    public static <T extends TreeNode> void setChildren(T parent, List<T> nodes) {
        List<T> children = new ArrayList<>();
        Object parentId = parent.id();

        for (Iterator<T> iterator = nodes.iterator(); iterator.hasNext(); ) {
            T node = iterator.next();
            if (Objects.equals(node.parentId(), parentId)) {
                children.add(node);
                //从所有节点列表中删除该节点，以免后续重复遍历该节点
                iterator.remove();
            }
        }
        //如果孩子为空，则直接返回，否则继续遍历递归设置孩子的孩子
        if (children.isEmpty()) {
            return;
        }
        parent.setChildren(children);
        children.forEach(e -> setChildren(e, nodes));

    }

    /**
     * 获取指定树节点下的所有叶子结点
     *
     * @param parent
     * @param <T>
     * @return
     */
    public static <T extends TreeNode<?>> List<T> getLeaves(T parent) {
        List<T> leaves = new ArrayList<>();

        fillLeaf(parent, leaves);
        return leaves;
    }

    /**
     * 将parent 的所有叶子结点填充到leaves列表中
     *
     * @param parent 父节点
     * @param leaves 叶子结点列表
     * @param <T>    实际 节点类型
     */
    private static <T extends TreeNode> void fillLeaf(T parent, List<T> leaves) {
    }
}
