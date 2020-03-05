package com.dj.ssm.web.page;

import com.dj.ssm.pojo.Resource;
import com.dj.ssm.pojo.ResultModel;
import com.dj.ssm.pojo.User;
import com.dj.ssm.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/index/")
public class IndexPageController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("toIndex")
    public String toIndex() {
        return "/index/index";
    }

    @RequestMapping("toLeft")
    public String toLeft() {
        return "/index/left";
    }

    @RequestMapping("toRight")
    public String toRight() {
        return "/index/right";
    }

    @RequestMapping("toTop")
    public String toTop() {
        return "/index/top";
    }

    @RequestMapping("toMenu")
    public String toMenu(HttpSession session, Model model){
        try {
/*        User user = (User) session.getAttribute("user");
//      List<Resource> resourceList = resourceService.findUserResource(user.getId());
        List<Resource> resourceList = (List<Resource>) session.getAttribute("resourceList");
        model.addAttribute("resourceList",resourceList);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index/menu";
    }

    /**
     * ztree显示左侧菜单
     * @return
     */
    @RequestMapping("getMenu")
    @ResponseBody
    public ResultModel<Object> getMenu(HttpSession session){
        try {
  /*          User user = (User) session.getAttribute("user");
            List<Resource> resourceList = resourceService.findUserResource(user.getId());*/
            //当前登录用户的全部权限
            List<Resource> resourceList = (List<Resource>) session.getAttribute("resourceList");
            //左侧按钮显示(如果不这样配置,按钮链接也会在作业显示)
            List<Resource> menuList = new ArrayList<>();
            for (Resource resource: resourceList){
                if(resource.getResourceType().equals(1)){
                    menuList.add(resource);
                }
            }
            return new ResultModel<>().success(menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<>().error("有异常"+e.getMessage());
        }
    }

}
