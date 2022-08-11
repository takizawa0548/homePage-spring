package com.example.takisahp.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository repository;

    @Override
    public Iterable<Member> selectAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Member> selectOneById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void insertMember(Member member) {
        repository.save(member);
    }

    @Override
    public void updateMember(Member member) {
        repository.save(member);
    }

    @Override
    public void deleteMember(Integer id) {
        repository.deleteById(id);
    }
}
