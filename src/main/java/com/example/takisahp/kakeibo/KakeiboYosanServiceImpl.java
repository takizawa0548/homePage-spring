package com.example.takisahp.kakeibo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@Transactional
public class KakeiboYosanServiceImpl implements KakeiboYosanService{
    @Autowired
    private KakeiboYosanRepository repository;
    @Override
    public void saveKakeiboYosan(KakeiboYosan kakeiboYosan) {
        repository.save(kakeiboYosan);
    }
}
