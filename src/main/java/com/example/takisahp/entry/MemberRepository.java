package com.example.takisahp.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface MemberRepository extends JpaRepository<Member,Integer> {

    @Query(nativeQuery = true, value = "select distinct name from member")
    Collection<MenberName> selectDistinctOnlyName();

}
