package com.jit.imhi.api;

import com.jit.imhi.model.Logininfo;
import com.jit.imhi.service.LogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logininfo")
public class LogininfoApi {
    private LogininfoService logininfoService;

    @Autowired
    public LogininfoApi(LogininfoService logininfoService) {
        this.logininfoService = logininfoService;
    }


    @GetMapping("register")
    public int insert(@RequestBody Logininfo logininfo) {
        return logininfoService.insert(logininfo);
    }

    @GetMapping("update")
    public int update(@RequestBody Logininfo logininfo) {
        return logininfoService.update(logininfo);
    }

    @GetMapping("insert&update")
    public boolean insertorupdate(@RequestBody Logininfo logininfo) {
        System.out.println("调用insertorupdate");
        if (logininfoService.find(logininfo) != null) {
            update(logininfo);
        } else
            insert(logininfo);
        return true;
    }
}
