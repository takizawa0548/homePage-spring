package com.example.takisahp;

import com.example.takisahp.entry.Member;
import com.example.takisahp.entry.MemberForm;
import com.example.takisahp.entry.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("member")
public class MemberListController {

    @Autowired
    MemberService service;

    @GetMapping
    public String showList(Member member, Model model){
        Iterable<Member> list = service.selectAll();
        model.addAttribute("list",list);
        return "memberList/memberTop";
    }

    @PostMapping("delete")
    public String deleteOne(@RequestParam("id") Integer id, Model model, RedirectAttributes redirectAttributes){

        service.deleteMember(id);
        return "redirect:/member";
    }

    @PostMapping("edit")
    public String editOne(MemberForm memberForm,@RequestParam("id") Integer id, Model model){

        if(id.equals(0)){
            model.addAttribute("id","-");
            model.addAttribute("title","登録");
            model.addAttribute("buttonName","登録");
            return "memberList/memberEdit";
        }

        Optional<Member> memberOpt = service.selectOneById(id);
        Optional<MemberForm> memberFormOpt = memberOpt.map(t->makeMemberForm(t));

        if(memberFormOpt.isPresent()){
            memberForm = memberFormOpt.get();
        }
        makeUpdateModel(memberForm,model);
        return "memberList/memberEdit";
    }

    private MemberForm makeMemberForm(Member member){
        MemberForm form = new MemberForm();
        form.setId(member.getId());
        form.setName(member.getName());
        form.setBirthdate(member.getBirthdate());
        form.setEmail(member.getEmail());
        form.setSex(member.getSex());
        form.setNationality(member.getNationality());
        form.setPhonenumber(member.getPhonenumber());
        return form;
    }

    private void makeUpdateModel(MemberForm memberForm, Model model){
        model.addAttribute("id",memberForm.getId());
        model.addAttribute("title","更新");
        model.addAttribute("buttonName","更新");
        model.addAttribute("memberForm",memberForm);
    }

}
