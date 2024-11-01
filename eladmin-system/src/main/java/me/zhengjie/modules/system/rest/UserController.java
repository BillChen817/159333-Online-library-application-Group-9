/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.rest;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import me.zhengjie.annotation.rest.AnonymousPostMapping;
import me.zhengjie.config.RsaProperties;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.*;
import me.zhengjie.modules.system.domain.vo.JobQueryCriteria;
import me.zhengjie.modules.system.domain.vo.RegisterVo;
import me.zhengjie.modules.system.domain.vo.UserPassVo;
import me.zhengjie.modules.system.domain.vo.UserQueryCriteria;
import me.zhengjie.modules.system.mapper.JobMapper;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.utils.*;
import me.zhengjie.utils.enums.CodeEnum;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final DataService dataService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final VerifyService verificationCodeService;
    private final JobMapper jobMapper;
    private final RecommendService recommendService;
    private final RedisUtils redisUtils;
    private final String HOT_BOOKS = "hot-books";

    @ApiOperation("导出用户数据")
    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('user:list')")
    public void exportUser(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        userService.download(userService.queryAll(criteria), response);
    }

    @ApiOperation("查询用户")
    @GetMapping
//    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<PageResult<User>> queryUser(UserQueryCriteria criteria, Page<Object> page){
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            criteria.getDeptIds().add(criteria.getDeptId());
            // 先查找是否存在子节点
            List<Dept> data = deptService.findByPid(criteria.getDeptId());
            // 然后把子节点的ID都加入到集合中
            criteria.getDeptIds().addAll(deptService.getDeptChildren(data));
        }
        // 数据权限
        List<Long> dataScopes = dataService.getDeptIds(userService.findByName(SecurityUtils.getCurrentUsername()));
        // criteria.getDeptIds() 不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(criteria.getDeptIds()) && !CollectionUtils.isEmpty(dataScopes)){
            // 取交集
            criteria.getDeptIds().retainAll(dataScopes);
            if(!CollectionUtil.isEmpty(criteria.getDeptIds())){
                return new ResponseEntity<>(userService.queryAll(criteria,page),HttpStatus.OK);
            }
        } else {
            // 否则取并集
            criteria.getDeptIds().addAll(dataScopes);
            return new ResponseEntity<>(userService.queryAll(criteria,page),HttpStatus.OK);
        }
        return new ResponseEntity<>(PageUtil.noData(),HttpStatus.OK);
    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping
//    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity<Object> createUser(@Validated @RequestBody User resources){
        checkLevel(resources);
        // 默认密码 123456
        resources.setPassword(passwordEncoder.encode("123456"));
        userService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AnonymousPostMapping(value = "/appRegister")
    public ResponseEntity<Object> createReader(@Validated @RequestBody RegisterVo account) throws Exception {
        User resources = new User();
        resources.setEmail(account.getEmail());
        resources.setPhone(account.getPhone());
        resources.setUsername(account.getUsername());
        resources.setNickName(account.getNickName());
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, account.getPassword());
        resources.setPassword(passwordEncoder.encode(password));
        // reader随意设置一个岗位
        Set<Job> jobs = new HashSet<>();
        // 从数据库查一个岗位赋值即可
        JobQueryCriteria jobQueryCriteria = new JobQueryCriteria();
        jobQueryCriteria.setEnabled(true);
        List<Job> allEnabledJobs = jobMapper.findAll(jobQueryCriteria);
        if (!CollectionUtils.isEmpty(allEnabledJobs)) {
            jobs.add(allEnabledJobs.get(0));
        } else {
            jobs.add(new Job());
        }
        resources.setJobs(jobs);
        // reader设置角色
        Role reader = roleService.findById(Long.parseLong("7"));
        Set<Role> roles = new HashSet<>();
        roles.add(reader);
        resources.setRoles(roles);
        Dept dept = deptService.findById(Long.parseLong("7"));
        resources.setDept(dept);
        resources.setEnabled(true);
        resources.setGender("female");
        resources.setAvatarName("");
        resources.setAvatarPath("");
        userService.create(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping
//    @PreAuthorize("@el.check('user:edit')")
    public ResponseEntity<Object> updateUser(@Validated(User.Update.class) @RequestBody User resources) throws Exception {
        checkLevel(resources);
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public ResponseEntity<Object> centerUser(@Validated(User.Update.class) @RequestBody User resources){
        if(!resources.getId().equals(SecurityUtils.getCurrentUserId())){
            throw new BadRequestException("不能修改他人资料");
        }
        userService.updateCenter(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping
//    @PreAuthorize("@el.check('user:del')")
    public ResponseEntity<Object> deleteUser(@RequestBody Set<Long> ids){
        for (Long id : ids) {
            // 不允许删除admin（超级管理员）
            if (id == 1)
                continue;
            Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(Role::getLevel).collect(Collectors.toList()));
            Integer optLevel =  Collections.min(roleService.findByUsersId(id).stream().map(Role::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException("角色权限不足，不能删除：" + userService.findById(id).getUsername());
            }
        }
        userService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public ResponseEntity<Object> updateUserPass(@RequestBody UserPassVo passVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey,passVo.getOldPass());
        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey,passVo.getNewPass());
        String username = StringUtils.isBlank(passVo.getUsername()) ? SecurityUtils.getCurrentUsername() : passVo.getUsername();
        User user = userService.findByName(username);
        if(!passwordEncoder.matches(oldPass, user.getPassword())){
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if(passwordEncoder.matches(newPass, user.getPassword())){
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePass(user.getUsername(),passwordEncoder.encode(newPass));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("重置密码")
    @PutMapping(value = "/resetPwd")
    public ResponseEntity<Object> resetPwd(@RequestBody Set<Long> ids) {
        String pwd = passwordEncoder.encode("123456");
        userService.resetPwd(ids, pwd);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateUserAvatar(@RequestParam MultipartFile avatar){
        return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
    }

    @Log("修改邮箱")
    @ApiOperation("修改邮箱")
    @PostMapping(value = "/updateEmail/{code}")
    public ResponseEntity<Object> updateUserEmail(@PathVariable String code, @RequestBody User resources) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey,resources.getPassword());
        User user = userService.findByName(SecurityUtils.getCurrentUsername());
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadRequestException("密码错误");
        }
        verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + resources.getEmail(), code);
        userService.updateEmail(user.getUsername(),resources.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/favorite/list")
    public ResponseEntity<List<String>> queryFavorite(String userId) {
        return new ResponseEntity<>(userService.listFavorite(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/favorite/add")
    public ResponseEntity<List<String>> addFavorite(String userId, String value) {
        userService.addFavorite(userId, value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/favorite/remove")
    public ResponseEntity<List<String>> removeFavorite(String userId, String value) {
        userService.removeFavorite(userId, value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AnonymousGetMapping(value = "/recommend/list")
    public ResponseEntity<List<Book>> listRecommend(String userId) {
        List<Book> result = (List<Book>) redisUtils.get(HOT_BOOKS);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            if (Strings.isNotBlank(userId)) {
                result = recommendService.listBooks(userId);
            } else {
                result = recommendService.randomBooks();
            }
            redisUtils.set(HOT_BOOKS, result, 600);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @AnonymousGetMapping(value = "/recommend/refresh")
    public ResponseEntity<List<Book>> refreshRecommend(String userId) {
        List<Book> result;
        if (Strings.isNotBlank(userId)) {
            recommendService.recommend(userId);
            result = recommendService.listBooks(userId);
        } else {
            result = recommendService.randomBooks();
        }
        redisUtils.del(HOT_BOOKS);
        redisUtils.set(HOT_BOOKS,result, 600);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/recommend/generate")
    public ResponseEntity<List<Book>> generateRecommend(String userId) {
        recommendService.recommend(userId);
        redisUtils.del(HOT_BOOKS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     * @param resources /
     */
    private void checkLevel(User resources) {
        Integer currentLevel =  Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(Role::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }
}
