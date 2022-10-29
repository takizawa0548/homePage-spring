package com.example.takisahp.kakeibo;

import com.example.takisahp.entry.Member;

import java.util.Optional;

public interface KakeiboService {

    Iterable<Kakeibo> selectAll();
    void saveKakeibo(Kakeibo kakeibo);
    public long count();
    public boolean existsById(String kakeibo_id);
    void deleteKakeibo(String kakeibo_id);
}
