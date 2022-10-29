package com.example.takisahp.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository repository;

    @Autowired
    EntityManager manager;

    @Override
    public Iterable<Member> selectAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Iterable<Member> selectName(String name) {
        String sql = "SELECT * FROM member where name like :name ORDER BY id ASC";
        List<Member> results = manager.createNativeQuery(sql, Member.class)
                .setParameter("name","%"+name+"%").getResultList();
        return results;
    }
    @Override
    public Collection<MenberName> selectDistinctOnlyNameTest(){
        return  repository.selectDistinctOnlyName();
    };
    @Override
    public Optional<Member> selectOneById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void saveMember(Member member) {
        repository.save(member);
    }

    @Override
    public void deleteMember(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public long count(){ return repository.count();};

    @Override
    public boolean existsById(Integer id){return repository.existsById(id);};
}
