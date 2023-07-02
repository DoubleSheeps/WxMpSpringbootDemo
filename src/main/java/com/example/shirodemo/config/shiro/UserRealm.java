package com.example.shirodemo.config.shiro;

import com.example.shirodemo.Utils.JedisUtil;
import com.example.shirodemo.Utils.JwtUtil;
import com.example.shirodemo.Utils.StringUtil;
import com.example.shirodemo.config.shiro.jwt.JwtToken;
import com.example.shirodemo.modules.sys.dao.PermissionDOMapper;
import com.example.shirodemo.modules.sys.dao.RoleDOMapper;
import com.example.shirodemo.modules.sys.dao.UserDOMapper;
import com.example.shirodemo.modules.sys.dataobject.PermissionDO;
import com.example.shirodemo.modules.sys.dataobject.RoleDO;
import com.example.shirodemo.modules.sys.dataobject.UserDO;
import com.example.shirodemo.model.common.Constant;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义Realm
 * @author dolyw.com
 * @date 2018/8/30 14:10
 */
@Service
public class UserRealm extends AuthorizingRealm {

    private final UserDOMapper userMapper;
    private final RoleDOMapper roleMapper;
    private final PermissionDOMapper permissionMapper;

    @Autowired
    public UserRealm(UserDOMapper userMapper, RoleDOMapper roleMapper, PermissionDOMapper permissionMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
    }

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = JwtUtil.getClaim(principalCollection.toString(), Constant.ACCOUNT);
        // 查询用户角色
        List<RoleDO> roleDOList = roleMapper.selectByUserAccount(account);
        for (RoleDO roleDO : roleDOList) {
            if (roleDO != null) {
                // 添加角色
                simpleAuthorizationInfo.addRole(roleDO.getRole());
                // 根据用户角色查询权限
                List<PermissionDO> permissionDOList = permissionMapper.selectByRoleId(roleDO.getId());
                for (PermissionDO permissionDO : permissionDOList) {
                    if (permissionDO != null) {
                        // 添加权限
                        simpleAuthorizationInfo.addStringPermission(permissionDO.getPermission());
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        // 帐号为空
        if (StringUtil.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        // 查询用户是否存在
        UserDO userDO = userMapper.selectByAccount(account);
        if (userDO == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }

    /**
     * 根据用户id清除缓存中的信息
     * 主要在修改用户权限时，需要清除用户之前的缓存信息
     * @param id 用户id
     */

    public void clearAuthCacheByUserId(Integer id){
        UserDO userDO = userMapper.selectByPrimaryKey(id);
        if(userDO!=null){
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDO.getAccount())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDO.getAccount());
            }
        }
    }
}
