package com.fzj.pms.service.impl;

import com.fzj.pms.dao.MenuRepository;
import com.fzj.pms.entity.dto.MenuDto;
import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.mapper.MenuMapper;
import com.fzj.pms.entity.security.Menu;
import com.fzj.pms.exception.SystemErrorException;
import com.fzj.pms.service.MenuService;
import com.fzj.pms.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor ={RuntimeException.class,Exception.class})
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Resource
    private MenuMapper menuMapper;

    @Autowired
    private UserService userService;

    @Override
    public List<Menu> findAllByMenuTree() {
        Sort sort = Sort.by(Sort.Direction.ASC,"name");
        List<Menu> menus = menuRepository.findAll(sort);
        return createTree(menus,0L);
    }

    @Override
    public List<Menu> getCurrMenuTree() {
        String[] types = {"0","1"};
        Sort sort = Sort.by(Sort.Direction.ASC,"orderNum");
        List<Menu> menus = menuRepository.findByTypeIn(types,sort);
        Menu menu = new Menu();
        menu.setId(0L);
        menu.setPid(-1L);
        menu.setName("顶级菜单");
        menus.add(menu);
        //构造菜单树
        List<Menu> menulist = createTree(menus, -1L);
        return menulist;
    }

    @Override
    public Menu create(Menu menu) {
        //return menuMapper.toDto(menuRepository.save(menu));
        return menuRepository.save(menu);
    }

    @Override
    public void update(Menu menu) {
        Optional<Menu> optional = menuRepository.findById(menu.getId());
        optional.ifPresent(e->{
            e.setComponent(menu.getComponent());
            e.setIcon(menu.getIcon());
            e.setName(menu.getName());
            e.setPath(menu.getPath());
            e.setPid(menu.getPid());
            e.setOrderNum(menu.getOrderNum());
            e.setMenuCode(menu.getMenuCode());
            e.setParentName(menu.getParentName());
            e.setPathName(menu.getPathName());
            e.setRemark(menu.getRemark());
            e.setType(menu.getType());
            menuRepository.save(e);
        });
    }

    @Override
    public void delete(Long id) {
       //1.查询全部子菜单
        List<Menu> menuList = menuRepository.findByPid(id);
        //2.删除子菜单
        menuList.forEach(e->{
            menuRepository.delete(e);
        });
        //3.删除父菜单
        menuRepository.deleteById(id);
    }

    /**
     * 构建菜单树
     * @param parentId
     * @param menus
     * @return
     */
    public List<Menu> createTree(List<Menu> menuList, Long pid) {
        List<Menu> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getPid().equals(pid))
                .forEach(dom -> {
                    Menu menu = new Menu();
                    BeanUtils.copyProperties(dom, menu);
                    List<Menu> menus = createTree(menuList, dom.getId());
                    menu.setChildren(menus);
                    list.add(menu);
                });
        return list;
    }
}
