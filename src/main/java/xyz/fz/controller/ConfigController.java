package xyz.fz.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fz.service.ConfigService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/config")
public class ConfigController {
    @Resource
    private ConfigService configService;

    @RequestMapping("/setting/{keyword}/{value}")
    public String setting(@PathVariable("keyword") String keyword, @PathVariable("value") String value) {
        configService.setting(keyword, value);
        return "setting ok";
    }
}
