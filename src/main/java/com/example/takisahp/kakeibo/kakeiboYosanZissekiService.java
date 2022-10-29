package com.example.takisahp.kakeibo;

import com.example.takisahp.entry.Member;

import java.util.Collection;

public interface kakeiboYosanZissekiService {

    void saveKakeiboResult(KakeiboResult KakeiboResult);
    Collection<KakeiboYosanZisseki> selectKakeiboId(String kakeiboId,String fromDate,String toDate);
}
