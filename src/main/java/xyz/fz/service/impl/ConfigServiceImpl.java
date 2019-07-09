package xyz.fz.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fz.dao.CommonDao;
import xyz.fz.entity.ConfigEntity;
import xyz.fz.service.ConfigService;

import javax.annotation.Resource;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private CommonDao db;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setting(String keyword, String value) {
        ConfigEntity configEntity = new ConfigEntity();
        configEntity.setKeyword(keyword);
        configEntity.setValue(value);
        db.save(configEntity);
    }
}
