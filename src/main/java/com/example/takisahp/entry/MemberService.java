package com.example.takisahp.entry;

import java.util.Optional;

public interface MemberService {

    Iterable<Member> selectAll();

    Optional<Member> selectOneById(Integer id);

    void saveMember(Member member);

    void deleteMember(Integer id);

    long count();

    boolean existsById(Integer id);
}
