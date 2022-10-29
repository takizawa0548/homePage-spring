package com.example.takisahp;

import com.example.takisahp.entry.*;
import com.example.takisahp.kakeibo.KakeiboForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("member")
public class MemberListController {

    @Autowired
    MemberService service;

    @ModelAttribute
    public MemberForm setUpMemberForm(){
        return new MemberForm();
    }
    @ModelAttribute
    public MemberTopForm setUpMemberTopForm(){
        return new MemberTopForm();
    }

    @GetMapping
    public String showList(Model model) {
        Iterable<Member> list = service.selectAll();
        model.addAttribute("list", list);
        Collection<MenberName> distinctNameList = service.selectDistinctOnlyNameTest();
        model.addAttribute("distinctNameList", distinctNameList);
        return "memberList/memberTop";
    }
    @PostMapping("search")
    public String search(@Validated MemberTopForm memberTopForm, BindingResult bindingResult, Model model) {
        Iterable<Member> list = null;
        if(bindingResult.hasErrors()) {
            list = service.selectAll();
        }else{
            list = service.selectName(memberTopForm.getSearchName());
        }
        model.addAttribute("list", list);
        Collection<MenberName> distinctNameList = service.selectDistinctOnlyNameTest();
        model.addAttribute("distinctNameList", distinctNameList);
        return "memberList/memberTop";
    }
    @PostMapping("delete")
    public String deleteOne(@RequestParam("id") Integer id, @RequestParam("name") String name,RedirectAttributes redirectAttributes) {

        service.deleteMember(id);
        redirectAttributes.addFlashAttribute("registerMsg", "削除");
        redirectAttributes.addFlashAttribute("registerName", name);
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
            ObjectError countErr = new ObjectError("countErr","登録数が上限数に達しています。（１００件）");
            model.addAttribute("countErr", "登録数が上限数に達しています。（１００件）");
            bindingResult.addError(countErr);
        }

        if(bindingResult.hasErrors()){
            if (idCheck == null) {
                model.addAttribute("title", "登録");
                model.addAttribute("buttonName", "登録");
            }else{
                model.addAttribute("title", "更新");
                model.addAttribute("buttonName", "更新");
            }
            return "memberList/memberEdit";
        }

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
        if (idCheck != null) {
            redirectAttributes.addFlashAttribute("registerMsg", "更新");
        }else{
            redirectAttributes.addFlashAttribute("registerMsg", "登録");
        }
        redirectAttributes.addFlashAttribute("registerName", memberForm.getName());
        return "redirect:/member";

    }
}