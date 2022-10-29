package com.example.takisahp.kakeibo;

import com.example.takisahp.entry.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
@Primary
@Transactional
public class KakeiboServiceImpl implements KakeiboService {

    @Autowired
    private KakeiboRepository repository;

    @Autowired
    EntityManager manager;

    @Override
    public Iterable<Kakeibo> selectAll() {
        return repository.findAll();
    }

    @Override
    public void saveKakeibo(Kakeibo kakeibo) {
        repository.save(kakeibo);
    }
    @Override
    public long count(){ return repository.count();};

    @Override
    public boolean existsById(String kakeibo_id){return repository.existsById(kakeibo_id);};


    @Override
    public void deleteKakeibo(String kakeibo_id) {
        repository.deleteById(kakeibo_id);
    }
}
