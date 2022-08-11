package com.example.takisahp.entry;

import java.util.Optional;

public interface MemberService {

    Iterable<Member> selectAll();

    Optional<Member> selectOneById(Integer id);

    void insertMember(Member member);

    void updateMember(Member member);

    void deleteMember(Integer id);
}
