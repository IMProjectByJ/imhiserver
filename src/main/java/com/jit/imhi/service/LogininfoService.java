package com.jit.imhi.service;

import com.jit.imhi.mapper.LogininfoMapper;
import com.jit.imhi.model.Logininfo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogininfoService {
     LogininfoMapper logininfoMapper ;

    public LogininfoService(){}

    public LogininfoService(LogininfoMapper logininfoMapper){
        this.logininfoMapper = logininfoMapper;
    }
    public int insert(Logininfo logininfo){
        return logininfoMapper.insert(logininfo);
    }
    public int update(Logininfo logininfo){
        return logininfoMapper.updateByPrimaryKey(logininfo);
    }

    public Logininfo find(Logininfo logininfo){
        return logininfoMapper.selectByPrimaryKey(logininfo.getUserId());
    }
}
