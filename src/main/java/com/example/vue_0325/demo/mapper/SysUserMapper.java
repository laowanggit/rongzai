package com.example.vue_0325.demo.mapper;

import com.example.vue_0325.demo.entity.SysUser;
import com.example.vue_0325.demo.entity.SysUserExample;
import java.util.List;
import java.util.Map;

import com.example.vue_0325.demo.utils.R;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int countByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int deleteByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int insert(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int insertSelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    List<SysUser> selectByExample(SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    SysUser selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int updateByExampleSelective(@Param("record") SysUser record, @Param("example") SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int updateByExample(@Param("record") SysUser record, @Param("example") SysUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Sat Mar 30 11:51:15 CST 2019
     */
    int updateByPrimaryKey(SysUser record);


    SysUser findByUserPwd(String userName);//根据用户名查询用户密码

    int updatePwd(SysUser sysUser);//修改密码

    //查询账号被封的账号
    List<SysUser> findLockAccount();

    //修改状态
    int unLockAccount(SysUser user);


    //POI图查询每个部门的人数
    List<Map<String,Object>> findPieData();

    //统计每个部门男女员工的数量
    List<Map<String,Object>> findBarData();

    //导出excel
    List<Map<String,Object>>  findUserForExport();

}