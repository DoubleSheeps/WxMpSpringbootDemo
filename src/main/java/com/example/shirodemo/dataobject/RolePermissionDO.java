package com.example.shirodemo.dataobject;

public class RolePermissionDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..role_permission.permission_id
     *
     * @mbg.generated Wed Jun 28 14:07:04 CST 2023
     */
    private Integer permissionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column test..role_permission.role_id
     *
     * @mbg.generated Wed Jun 28 14:07:04 CST 2023
     */
    private Integer roleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..role_permission.permission_id
     *
     * @return the value of test..role_permission.permission_id
     *
     * @mbg.generated Wed Jun 28 14:07:04 CST 2023
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..role_permission.permission_id
     *
     * @param permissionId the value for test..role_permission.permission_id
     *
     * @mbg.generated Wed Jun 28 14:07:04 CST 2023
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column test..role_permission.role_id
     *
     * @return the value of test..role_permission.role_id
     *
     * @mbg.generated Wed Jun 28 14:07:04 CST 2023
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column test..role_permission.role_id
     *
     * @param roleId the value for test..role_permission.role_id
     *
     * @mbg.generated Wed Jun 28 14:07:04 CST 2023
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}