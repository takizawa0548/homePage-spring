package com.example.takisahp;

import com.example.takisahp.entry.Member;
import com.example.takisahp.entry.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("member")
public class MemberListController {

    @Autowired
    MemberService service;

    @GetMapping
    public String showList(Member member, Model model){
        Iterable<Member> list = service.selectAll();
        model.addAttribute("list",list);
        return "memberlist/membertop";
    }

}
