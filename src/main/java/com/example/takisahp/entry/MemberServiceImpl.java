package com.example.takisahp.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.takisahp.entry.MemberRepository;

import java.util.Optional;

@Service
@Primary
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository repository;

    @Override
    public Iterable<Member> selectAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

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
