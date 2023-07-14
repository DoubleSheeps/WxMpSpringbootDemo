package com.example.shirodemo.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shirodemo.Utils.*;
import com.example.shirodemo.config.shiro.UserRealm;
import com.example.shirodemo.modules.sys.controller.VO.Permission;
import com.example.shirodemo.modules.sys.controller.VO.UserRoleParam;
import com.example.shirodemo.error.BusinessException;
import com.example.shirodemo.error.EmBusinessError;
import com.example.shirodemo.model.RoleModel;
import com.example.shirodemo.model.UserModel;
import com.example.shirodemo.model.common.CommonReturnType;
import com.example.shirodemo.model.common.Constant;
import com.example.shirodemo.modules.sys.dataobject.UserDO;
import com.example.shirodemo.modules.sys.service.UserService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Api(tags = "员工系统模块")
@RestController
@RequestMapping("/api/user")
@PropertySource("classpath:config1.properties")
public class UserController {

    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    private final UserUtil userUtil;

    private final UserService userService;
    private final UserRealm userRealm;

    @Autowired
    public UserController(UserUtil userUtil, UserService userService, UserRealm userRealm) {
        this.userUtil = userUtil;
        this.userService = userService;
        this.userRealm = userRealm;
    }

    /**
     * 获取用户列表
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:41
     */
    @GetMapping
    @RequiresPermissions(logical = Logical.AND, value = {"user:view"})
    public CommonReturnType user() {
        List<UserDO> userModelList = userService.list();
        if (userModelList == null || userModelList.size() < 1) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return CommonReturnType.create(userModelList);
    }

    @GetMapping("/teacher")
    @RequiresPermissions(logical = Logical.AND, value = {"class:add"})
    public CommonReturnType teacher() {
        return CommonReturnType.create(userService.teacherList());
    }

    /**
     * 获取在线用户(查询Redis中的RefreshToken)
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2018/9/6 9:58
     */
    @GetMapping("/online")
    @RequiresPermissions(logical = Logical.AND, value = {"user:view"})
    public CommonReturnType online() {
        List<Object> userModelList = new ArrayList<Object>();
        // 查询所有Redis键
        Set<String> keys = JedisUtil.keysS(Constant.PREFIX_SHIRO_REFRESH_TOKEN + "*");
        for (String key : keys) {
            if (JedisUtil.exists(key)) {
                // 根据:分割key，获取最后一个字符(帐号)
                String[] strArray = key.split(":");
                UserModel userModel = userService.findByAccount(strArray[strArray.length - 1]);
                // 设置登录时间
                //userDto.setLoginTime(new Date(Long.parseLong(JedisUtil.getObject(key).toString())));
                userModelList.add(userModel);
            }
        }
        if (userModelList == null || userModelList.size() < 0) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR.setErrMsg("查询失败(Query Failure)"));
        }
        return CommonReturnType.create(userModelList);
    }

    /**
     * 登录授权
     * @param userModel
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2018/8/30 16:21
     */
    @PostMapping("/login")
    public CommonReturnType login(@RequestBody UserModel userModel, HttpServletResponse httpServletResponse) {
        // 查询数据库中的帐号信息
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getAccount,userModel.getAccount());
        List<UserDO> userDOList = userService.list(queryWrapper);
        if (userDOList == null||userDOList.size()!=1) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserDO userModelTemp = userDOList.get(0);
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(userModelTemp.getPassword());
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(userModel.getAccount() + userModel.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + userModel.getAccount())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + userModel.getAccount());
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userModel.getAccount(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(userModel.getAccount(), currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            return CommonReturnType.create("登录成功(Login Success.)");
        } else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"帐号或密码错误(Account or Password Error.)");
        }
    }

    /**
     * 测试登录
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2018/8/30 16:18
     */
    @GetMapping("/article")
    public CommonReturnType article() {
        Subject subject = SecurityUtils.getSubject();
        // 登录了返回true
        if (subject.isAuthenticated()) {
            return CommonReturnType.create("您已经登录了(You are already logged in)");
        } else {
            return CommonReturnType.create("你是游客(You are guest)");
        }
    }

    @GetMapping("/check/perm/{perm}")
    @RequiresAuthentication
    public CommonReturnType checkRole(@PathVariable(value = "perm") String perm) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted(perm)) {
            return CommonReturnType.create("已授权");
        } else {
            return CommonReturnType.create(CommonReturnType.STATUS_FAIL, "未授权");
        }
    }

    @GetMapping("/perm")
    @RequiresAuthentication
    public CommonReturnType getPerm() {
        UserModel userModel = userUtil.getUser();
        List<Permission> permissionList = userService.permissionListByAccount(userModel.getAccount());
        return CommonReturnType.create(permissionList);
    }

    /**
     * 测试登录注解(@RequiresAuthentication和subject.isAuthenticated()返回true一个性质)
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2018/8/30 16:18
     */
    @GetMapping("/article2")
    @RequiresAuthentication
    public CommonReturnType requireAuth() {
        return CommonReturnType.create("您已经登录了(You are already logged in)");
    }

    /**
     * 获取当前登录用户信息
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2019/3/15 11:51
     */
    @GetMapping("/info")
    @RequiresAuthentication
    public CommonReturnType info() {
        // 获取当前登录用户
        UserModel userModel = userUtil.getUser();
        // 获取当前登录用户Id
        Integer id = userUtil.getUserId();
        // 获取当前登录用户Token
        String token = userUtil.getToken();
        // 获取当前登录用户Account
        String account = userUtil.getAccount();
        return CommonReturnType.create(userModel);
    }

    /**
     * 获取指定用户
     * @param id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:42
     */
    @GetMapping("/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"user:view"})
    public CommonReturnType findById(@PathVariable("id") Integer id) {
        UserModel userModel = userService.findByUID(id);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"查询失败(Query Failure)");
        }
        return CommonReturnType.create(userModel);
    }

    /**
     * 新增用户
     * @param userModel
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:42
     */
    @PostMapping("/add")
    @RequiresPermissions(logical = Logical.AND, value = {"user:add"})
    public CommonReturnType add( @RequestBody UserModel userModel) {
        // 判断当前帐号是否存在
        UserModel userModelTemp = userService.findByAccount(userModel.getAccount());
        if (userModelTemp != null && StringUtil.isNotBlank(userModelTemp.getPassword())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"该帐号已存在(Account exist.)");
        }
        // 密码以帐号+密码的形式进行AES加密
        if (userModel.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"密码最多8位(Password up to 8 bits.)");
        }
        String key = AesCipherUtil.enCrypto(userModel.getAccount() + userModel.getPassword());
        userModel.setPassword(key);
        int count = userService.add(userModel);
        if (count <= 0) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"新增失败(Insert Failure)");
        }
        return CommonReturnType.create("新增成功(Insert Success)");
    }

    /**
     * 更新用户
     * @param userModel
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:42
     */
    @PutMapping("/update")
    @RequiresPermissions(logical = Logical.AND, value = {"user:update"})
    public CommonReturnType update(@RequestBody UserModel userModel) {
        // 查询数据库密码
        UserModel userDtoTemp = userService.findByAccount(userModel.getAccount());
        if (userDtoTemp == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        } else {
            userModel.setUid(userDtoTemp.getUid());
        }
        // FIXME: 如果不一样就说明用户修改了密码，重新加密密码(这个处理不太好，但是没有想到好的处理方式)
        if (userModel.getPassword()!=null) {
            // 密码以帐号+密码的形式进行AES加密
            if (userModel.getPassword().length() > Constant.PASSWORD_MAX_LEN) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"密码最多8位(Password up to 8 bits.)");
            }
            String key = AesCipherUtil.enCrypto(userModel.getAccount() + userModel.getPassword());
            userModel.setPassword(key);
        }
        int count = userService.update(userModel);
        if (count <= 0) {
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"更新失败(Update Failure)");
        }
        return CommonReturnType.create("更新成功(Update Success)");
    }

    /**
     * 删除用户
     * @param id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author dolyw.com
     * @date 2018/8/30 10:43
     */
    @DeleteMapping("/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"user:del"})
    public CommonReturnType delete(@PathVariable("id") Integer id) {
        int count = userService.delete(id);
        if (count <= 0) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"删除失败，ID不存在(Deletion Failed. ID does not exist.)");
        }
        return CommonReturnType.create("删除成功(Delete Success)");
    }

    /**
     * 剔除在线用户
     * @param id
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2018/9/6 10:20
     */
    @DeleteMapping("/online/{id}")
    @RequiresPermissions(logical = Logical.AND, value = {"user:off"})
    public CommonReturnType deleteOnline(@PathVariable("id") Integer id) {
        UserModel userDto = userService.findByUID(id);
        if(userDto==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        if (JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getAccount())) {
            if (JedisUtil.delKey(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getAccount()) > 0) {
                return CommonReturnType.create("剔除成功(Delete Success)");
            }
        }
        throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"剔除失败，Account不存在(Deletion Failed. Account does not exist.)");
    }

    @GetMapping("/role/{id}")
    @RequiresPermissions(logical = Logical.AND,value = {"user:role"})
    public CommonReturnType getRole(@PathVariable("id") Integer id){
        List<RoleModel> roleModelList = userService.roleListByUserId(id);
        return CommonReturnType.create(roleModelList);
    }

    @GetMapping("/role/all/{id}")
    @RequiresPermissions(logical = Logical.AND,value = {"user:role"})
    public CommonReturnType roleList(@PathVariable("id") Integer id){
        List<RoleModel> roleModelList = userService.roleListByUserId2(id);
        return CommonReturnType.create(roleModelList);
    }

    @PostMapping("/role/add")
    @RequiresPermissions(logical = Logical.AND,value = {"user:role"})
    public CommonReturnType roleAdd(@RequestBody UserRoleParam userRoleParam){
        userService.roleAdd(userRoleParam.getUserId(),userRoleParam.getRoleIds());
        return CommonReturnType.create("添加成功");
    }

    @GetMapping("/logout")
    @RequiresAuthentication
    public CommonReturnType logout(){
        Integer id = userUtil.getUserId();
        userRealm.clearAuthCacheByUserId(id);
        return CommonReturnType.create("退出登录成功");
    }

}