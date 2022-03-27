package com.fzj.pms.service.impl;

import com.fzj.pms.dao.RoleRepository;
import com.fzj.pms.dao.UserRepository;
import com.fzj.pms.entity.dto.RoleDto;
import com.fzj.pms.entity.mapper.RoleMapper;
import com.fzj.pms.entity.security.Menu;
import com.fzj.pms.entity.security.Role;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.exception.SystemErrorException;
import com.fzj.pms.service.MenuService;
import com.fzj.pms.service.RoleService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.SetUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuService menuService;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Role create(Role role) {
        if(roleRepository.findRoleByName(role.getName()).isPresent()){
            throw new SystemErrorException("角色名称已存在");
        }
        return roleRepository.save(role);
    }

    @Override
    public void update(Role role) {
        roleRepository.findRoleByName(role.getName()).ifPresent(dbRole ->{
           if(!dbRole.getId().equals(role.getId())){
               throw new SystemErrorException("角色名称已存在");
           }
        });
        roleRepository.findById(role.getId()).ifPresent(roles ->{
            roles.setName(role.getName());
            roles.setRemark(role.getRemark());
            roles.setMenus(role.getMenus());
            roleRepository.save(roles);
        });
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<RoleDto> findAll() {
        Sort sort=Sort.by(Sort.Direction.DESC, "id");
        List<RoleDto> list = roleMapper.toDto(roleRepository.findAll(sort));
        Map<Long, Integer> map = getCiteNum(list);
        list.forEach(role->{
            role.setCiteNum(Objects.isNull(map.get(role.getId()))?0:map.get(role.getId()));
        });
        return list;
    }

    @Override
    public int batchDelete(Set<Long> ids) {
        if(SetUtils.isEmpty(ids)){
            throw new SystemErrorException("ids 不能为空");
        }
        return  roleRepository.deleteRoleByIdIn(ids)==ids.size()?ids.size():0;
    }

    @Override
    public Page<Role> searchByName(String name, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        //return  roleRepository.findAllByName(name,pageable);
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(name)) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + name));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        return roleRepository.findAll(specification,pageable);
    }

    //    @Override
//    public List<RoleDto> search(String name) {
//        //Sort sort=new Sort(Sort.Direction.DESC, Collections.singletonList("id"));
//        Sort sort=Sort.by(Sort.Direction.DESC,"id");
//        List<RoleDto> list = roleMapper.toDto(roleRepository.findRoleByNameLike("%" + name + "%",sort));
//        Map<Long, Integer> map = getCiteNum(list);
//        list.forEach(roles->{
//            roles.setCiteNum(Objects.isNull(map.get(roles.getId()))?0:map.get(roles.getId()));
//        });
//        return list;
//    }

    /**
     * 获取当前角色的用户应用数
     * @return
     */
    private Map<Long, Integer> getCiteNum(List<RoleDto> list){
        List<User> userList = userRepository.findAll();
        Map<Long,Integer> map= Maps.newHashMapWithExpectedSize(list.size());
        userList.forEach(user->{
            Long id=user.getRole().getId();
            Integer num = map.get(id);
            map.put(id, num == null ? 1 : num + 1);
        });
        return map;
    }

    @Override
    public Role findOne(Long id) {
        if(Objects.isNull(id)){
            throw new SystemErrorException("id 不能为空");
        }
        Role role = roleRepository.findById(id).orElseThrow(() ->new SystemErrorException("用户不存在"));
        if(!SetUtils.isEmpty(role.getMenus())){
            Set<Menu> menus = role.getMenus().stream().filter(item -> item.getPid() != 0L).collect(Collectors.toSet());
            role.setMenus(menus);
        }
        return role;
    }

}
