package com.example.takisahp;

import com.example.takisahp.entry.Member;
import com.example.takisahp.entry.MemberForm;
import com.example.takisahp.entry.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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
    public String showList(Member member, Model model) {
        Iterable<Member> list = service.selectAll();
        model.addAttribute("list", list);
        return "memberList/memberTop";
    }

    @PostMapping("delete")
    public String deleteOne(@RequestParam("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        service.deleteMember(id);
        return "redirect:/member";
    }

    @PostMapping("edit")
    public String editOne(MemberForm memberForm, Model model) {

        Integer idCheck = memberForm.getId();
        if (idCheck == null) {
            model.addAttribute("title", "登録");
            model.addAttribute("buttonName", "登録");
            return "memberList/memberEdit";
        }

        Optional<Member> memberOpt = service.selectOneById(memberForm.getId());
        Optional<MemberForm> memberFormOpt = memberOpt.map(t -> makeMemberForm(t));

        if (memberFormOpt.isPresent()) {
            memberForm = memberFormOpt.get();
        }

        model.addAttribute("title", "更新");
        model.addAttribute("buttonName", "更新");
        model.addAttribute("memberForm", memberForm);
        return "memberList/memberEdit";
    }

    private MemberForm makeMemberForm(Member member) {
        MemberForm form = new MemberForm();
        form.setId(member.getId());
        form.setName(member.getName());
        form.setBirthdate(member.getBirthdate());
        form.setEmail(member.getEmail());
        form.setGender(member.getGender());
        form.setNationality(member.getNationality());
        form.setPhonenumber(member.getPhonenumber());
        return form;
    }

    @PostMapping("save")
    public String saveOne(@Validated MemberForm memberForm, BindingResult bindingResult,
                            Model model, RedirectAttributes redirectAttributes) {

        Integer idCheck = memberForm.getId();
        boolean idExists = false;
        if (idCheck != null) {
            idExists = service.existsById(idCheck);
        }
        if(100<=service.count() && !idExists){
            //使い方が・・・？
            ObjectError countErr = new ObjectError("countErr","登録数が上限数に達しています。〈１００件）");
            model.addAttribute("countErr", "登録数が上限数に達しています。〈１００件）");
            bindingResult.addError(countErr);
        }

        if(!bindingResult.hasErrors()){

            Member member = new Member();
            if (idCheck != null) {
                member.setId(memberForm.getId());
            }
            member.setName(memberForm.getName());
            member.setGender(memberForm.getGender());
            member.setBirthdate(memberForm.getBirthdate());
            member.setPhonenumber(memberForm.getPhonenumber());
            member.setEmail(memberForm.getEmail());
            member.setNationality(memberForm.getNationality());
            service.saveMember(member);
            return "redirect:/member";
        }
        if (idCheck == null) {
                model.addAttribute("title", "登録");
                model.addAttribute("buttonName", "登録");
        }else{
                model.addAttribute("title", "更新");
                model.addAttribute("buttonName", "更新");
        }
        return "memberList/memberEdit";
    }
}