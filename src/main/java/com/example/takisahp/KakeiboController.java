package com.example.takisahp;

import com.example.takisahp.entry.Member;
import com.example.takisahp.entry.MemberForm;
import com.example.takisahp.kakeibo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("kakeibo")
public class KakeiboController {

    @Autowired
    KakeiboService service;
    @Autowired
    kakeiboYosanZissekiService serviceResult;
    @Autowired
    KakeiboYosanService serviceYosan;
    @ModelAttribute
    public KakeiboForm setUpKakeiboForm(){
        return new KakeiboForm();
    }
    @ModelAttribute
    public KakeiboResultForm setUpKakeiboResultForm(){
        return new KakeiboResultForm();
    }
    @ModelAttribute
    public KakeiboYosanForm setUpKakeiboYosanForm(){
        return new KakeiboYosanForm();
    }
    @GetMapping
    public String showKakeiboIdList(Model model) {
        Iterable<Kakeibo> list = service.selectAll();
        model.addAttribute("list", list);
        return "kakeibo/kakeiboTop";
    }

    @PostMapping("save")
    public String saveOne(@Validated KakeiboForm kakeiboForm, BindingResult bindingResult,
                          Model model, RedirectAttributes redirectAttributes) {

        String idCheck = kakeiboForm.getKakeibo_id();
        boolean idExists = false;
        if (idCheck != null) {
            idExists = service.existsById(idCheck);
        }
        if(100<=service.count() && !idExists){
            //使い方が・・・？
            ObjectError countErr = new ObjectError("countErr","登録数が上限数に達しています。（１００件）");
            model.addAttribute("countErr", "登録数が上限数に達しています。〈１００件）");
            bindingResult.addError(countErr);
        }
        if(idExists){
            //使い方が・・・？
            ObjectError existsErr = new ObjectError("existsErr","既に登録されているIDです。");
            model.addAttribute("existsErr", "既に登録されているIDです。");
            bindingResult.addError(existsErr);
        }
        if(bindingResult.hasErrors()){
            Iterable<Kakeibo> list = service.selectAll();
            model.addAttribute("list", list);
            return "kakeibo/kakeiboTop";
        }
        Kakeibo kakeibo = new Kakeibo();
        kakeibo.setKakeibo_id(kakeiboForm.getKakeibo_id());
        kakeibo.setKakeibo_date(new Date());
        service.saveKakeibo(kakeibo);
        redirectAttributes.addFlashAttribute("registerMsg", "登録");
        redirectAttributes.addFlashAttribute("registerName", kakeiboForm.getKakeibo_id());
        return "redirect:/kakeibo";
    }
    @PostMapping("delete")
    public String deleteOne(@RequestParam("kakeibo_id") String kakeibo_id, RedirectAttributes redirectAttributes) {

        service.deleteKakeibo(kakeibo_id);
        redirectAttributes.addFlashAttribute("registerMsg", "削除");
        redirectAttributes.addFlashAttribute("registerName", kakeibo_id);
        return "redirect:/kakeibo";
    }
    @PostMapping("Result")
    public String showKakeiboResult(@RequestParam("kakeibo_id") String kakeibo_id,Model model) throws ParseException {

        //当月の月初と月末を取得
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date a = new Date();
        String d = df.format(a);
        int y = Integer.parseInt(d.substring(0,4));
        int m = Integer.parseInt(d.substring(5,7));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m);
        String strYM = y + "-" + m + "-";
        String fromDate = strYM+ c.getActualMinimum(Calendar.DAY_OF_MONTH);
        String toDate = strYM + c.getActualMaximum(Calendar.DAY_OF_MONTH);

        //予算項目があれば表示するが、当月以外の実績金額については合算しない
        Collection<KakeiboYosanZisseki> list = serviceResult.selectKakeiboId(kakeibo_id,fromDate,toDate);
        //予算と実績の差額、予算と実績と差額の合計を追加
        int yosanGoukei = 0;
        int zissekiGoukei = 0;
        int sagakugoukei = 0;
        for(KakeiboYosanZisseki obj:list){
            int yosanKingaku = obj.getKakeibo_yosan_kingaku();
            int zissekiKingaku = obj.getKakeibo_zisseki_kingaku();
            int sagakuKingaku = yosanKingaku - zissekiKingaku;
            obj.setKakeibo_sagaku_kingaku(sagakuKingaku);
            yosanGoukei += yosanKingaku;
            zissekiGoukei += zissekiKingaku;
            sagakugoukei += sagakuKingaku;
        }
        model.addAttribute("kakeibo_id", kakeibo_id);
        model.addAttribute("list", list);
        model.addAttribute("yosanGoukei", yosanGoukei);
        model.addAttribute("zissekiGoukei", zissekiGoukei);
        model.addAttribute("sagakugoukei", sagakugoukei);
        return "kakeibo/kakeiboResult";
    }
    @PostMapping("saveResult")
    public String saveResult(@Validated KakeiboResultForm kakeiboResultForm, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes) {
        if(!bindingResult.hasErrors()){
            KakeiboResult kakeiboResult = new KakeiboResult();
            kakeiboResult.setKakeibo_id(kakeiboResultForm.getKakeibo_id());
            kakeiboResult.setKakeibo_koumoku(kakeiboResultForm.getKakeibo_koumoku());
            kakeiboResult.setKakeibo_zisseki_kingaku(kakeiboResultForm.getKakeibo_zisseki_kingaku());
            kakeiboResult.setKakeibo_date(kakeiboResultForm.getKakeibo_date());
            serviceResult.saveKakeiboResult(kakeiboResult);
            model.addAttribute("registerMsg", "登録");
            model.addAttribute("registerName", kakeiboResultForm.getKakeibo_id());
        }
        //当月の月初と月末を取得
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date a = new Date();
        String d = df.format(a);
        int y = Integer.parseInt(d.substring(0,4));
        int m = Integer.parseInt(d.substring(5,7));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m);
        String strYM = y + "-" + m + "-";
        String fromDate = strYM+ c.getActualMinimum(Calendar.DAY_OF_MONTH);
        String toDate = strYM + c.getActualMaximum(Calendar.DAY_OF_MONTH);

        //予算項目があれば表示するが、当月以外の実績金額については合算しない
        Collection<KakeiboYosanZisseki> list = serviceResult.selectKakeiboId(kakeiboResultForm.getKakeibo_id(),fromDate,toDate);
        //予算と実績の差額、予算と実績と差額の合計を追加
        int yosanGoukei = 0;
        int zissekiGoukei = 0;
        int sagakugoukei = 0;
        for(KakeiboYosanZisseki obj:list){
            int yosanKingaku = obj.getKakeibo_yosan_kingaku();
            int zissekiKingaku = obj.getKakeibo_zisseki_kingaku();
            int sagakuKingaku = yosanKingaku - zissekiKingaku;
            obj.setKakeibo_sagaku_kingaku(sagakuKingaku);
            yosanGoukei += yosanKingaku;
            zissekiGoukei += zissekiKingaku;
            sagakugoukei += sagakuKingaku;
        }
        model.addAttribute("kakeibo_id", kakeiboResultForm.getKakeibo_id());
        model.addAttribute("list", list);
        model.addAttribute("yosanGoukei", yosanGoukei);
        model.addAttribute("zissekiGoukei", zissekiGoukei);
        model.addAttribute("sagakugoukei", sagakugoukei);
        return "kakeibo/kakeiboResult";

    }
    @PostMapping("saveYosan")
    public String saveYosan(@Validated KakeiboYosanForm kakeiboYosanForm, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes) {
        if(!bindingResult.hasErrors()){
            KakeiboYosan kakeiboYosan = new KakeiboYosan();
            kakeiboYosan.setKakeibo_id(kakeiboYosanForm.getKakeibo_id());
            kakeiboYosan.setKakeibo_koumoku(kakeiboYosanForm.getKakeibo_koumoku());
            kakeiboYosan.setKakeibo_yosan_kingaku(kakeiboYosanForm.getKakeibo_yosan_kingaku());
            serviceYosan.saveKakeiboYosan(kakeiboYosan);
            model.addAttribute("registerMsg", "登録");
            model.addAttribute("registerName", kakeiboYosanForm.getKakeibo_id());
        }
        //当月の月初と月末を取得
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date a = new Date();
        String d = df.format(a);
        int y = Integer.parseInt(d.substring(0,4));
        int m = Integer.parseInt(d.substring(5,7));
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m);
        String strYM = y + "-" + m + "-";
        String fromDate = strYM+ c.getActualMinimum(Calendar.DAY_OF_MONTH);
        String toDate = strYM + c.getActualMaximum(Calendar.DAY_OF_MONTH);

        //予算項目があれば表示するが、当月以外の実績金額については合算しない
        Collection<KakeiboYosanZisseki> list = serviceResult.selectKakeiboId(kakeiboYosanForm.getKakeibo_id(),fromDate,toDate);
        //予算と実績の差額、予算と実績と差額の合計を追加
        int yosanGoukei = 0;
        int zissekiGoukei = 0;
        int sagakugoukei = 0;
        for(KakeiboYosanZisseki obj:list){
            int yosanKingaku = obj.getKakeibo_yosan_kingaku();
            int zissekiKingaku = obj.getKakeibo_zisseki_kingaku();
            int sagakuKingaku = yosanKingaku - zissekiKingaku;
            obj.setKakeibo_sagaku_kingaku(sagakuKingaku);
            yosanGoukei += yosanKingaku;
            zissekiGoukei += zissekiKingaku;
            sagakugoukei += sagakuKingaku;
        }
        model.addAttribute("kakeibo_id", kakeiboYosanForm.getKakeibo_id());
        model.addAttribute("list", list);
        model.addAttribute("yosanGoukei", yosanGoukei);
        model.addAttribute("zissekiGoukei", zissekiGoukei);
        model.addAttribute("sagakugoukei", sagakugoukei);
        return "kakeibo/kakeiboResult";

    }
}