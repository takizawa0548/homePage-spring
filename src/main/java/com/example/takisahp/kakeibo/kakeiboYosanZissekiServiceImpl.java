package com.example.takisahp.kakeibo;

import com.example.takisahp.entry.Member;
import com.example.takisahp.entry.MenberName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional
public class kakeiboYosanZissekiServiceImpl implements kakeiboYosanZissekiService{
    @Autowired
    private kakeiboYosanZissekiRepository  repository;
    @Autowired
    EntityManager manager;

    @Override
    public void saveKakeiboResult(KakeiboResult kakeiboResult) {
        repository.save(kakeiboResult);
    }

    @Override
    public Collection<KakeiboYosanZisseki> selectKakeiboId(String kakeiboId,String fromDate,String toDate){
        //予算項目があれば表示するが、当月以外の実績金額については合算しない
        String sql = "select " +
                " yosan.kakeibo_id as kakeibo_id," +
                " yosan.kakeibo_koumoku as kakeibo_koumoku," +
                " yosan.kakeibo_yosan_kingaku as kakeibo_yosan_kingaku," +
                " sum(case when zisseki.kakeibo_zisseki_kingaku is not null" +
                " and kakeibo_date >= :fromDate and kakeibo_date <= :toDate" +
                " then zisseki.kakeibo_zisseki_kingaku else 0 end) as kakeibo_zisseki_kingaku," +
                " 0 as kakeibo_sagaku_kingaku" +
                " from kakeibo_yosan AS yosan Left outer join kakeibo_zisseki as zisseki" +
                " on yosan.kakeibo_id = zisseki.kakeibo_id and yosan.kakeibo_koumoku =" +
                " zisseki.kakeibo_koumoku where" +
                " yosan.kakeibo_id = :kakeiboId " +
                " group by yosan.kakeibo_id,yosan.kakeibo_koumoku,yosan.kakeibo_yosan_kingaku";
        List<KakeiboYosanZisseki> results = manager.createNativeQuery(sql, KakeiboYosanZisseki.class)
                .setParameter("kakeiboId",kakeiboId)
                .setParameter("fromDate", Date.valueOf(fromDate)).setParameter("toDate",Date.valueOf(toDate))
                .getResultList();
        return results;
    }
}
