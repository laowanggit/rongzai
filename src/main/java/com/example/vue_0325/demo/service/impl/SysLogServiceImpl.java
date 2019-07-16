package com.example.vue_0325.demo.service.impl;


import com.example.vue_0325.demo.entity.SysLog;
import com.example.vue_0325.demo.entity.SysLogExample;
import com.example.vue_0325.demo.entity.SysRoleExample;
import com.example.vue_0325.demo.mapper.SysLogMapper;
import com.example.vue_0325.demo.service.SysLogService;
import com.example.vue_0325.demo.utils.Pager;
import com.example.vue_0325.demo.utils.ResultData;
import com.example.vue_0325.demo.utils.Sorter;
import com.example.vue_0325.demo.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("SysLogServiceImpl")
public class SysLogServiceImpl implements SysLogService {

    //注入mapper
    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public int saveLog(SysLog sysLog) {
         return sysLogMapper.insert(sysLog);
    }

    @Override
    public ResultData findByPageLog(Pager pager, String search, Sorter sorter) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysLogExample example = new SysLogExample();
        if (sorter!=null&& StringUtils.isNotEmpty(sorter.getSort())){
            example.setOrderByClause("id "+sorter.getOrder());
        }
        SysLogExample.Criteria criteria = example.createCriteria();
        if(search!=null&&!"".equals(search)){//条件查询
            criteria.andUsernameLike("%"+search+"%");
        }
        List<SysLog> sysLogs = sysLogMapper.selectByExample(example);
        PageInfo info = new PageInfo(sysLogs);
        ResultData resultData = new ResultData(info.getTotal(),info.getList());
        return resultData;
    }
}