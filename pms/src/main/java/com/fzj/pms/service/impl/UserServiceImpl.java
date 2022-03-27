package com.fzj.pms.service.impl;

import com.fzj.pms.dao.RoleRepository;
import com.fzj.pms.dao.UserRepository;
import com.fzj.pms.entity.dto.UserDto;
import com.fzj.pms.entity.enums.UseStatus;
import com.fzj.pms.entity.mapper.UserMapper;
import com.fzj.pms.entity.security.User;
import com.fzj.pms.entity.vo.UserVo;
import com.fzj.pms.exception.SystemErrorException;
import com.fzj.pms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserMapper userMapper;

//    @Value("${sys.initPassword}")
//    private String INIT_PASSWORD;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username).orElseGet(()-> {
//            throw new UsernameNotFoundException("用户名不存在");
//        });
//    }

//    @Override
//    public Optional<UserDto> getCurrUserInfo(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(!Objects.isNull(authentication)){
//            return Optional.ofNullable(userMapper.toDto((User) authentication.getPrincipal()));
//        }
//        return Optional.empty();
//    }


    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUserNameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User registerUser(User user){
        //密码加密
//       user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       if (userRepository.findByUsername(user.getUsername()).isPresent()){
           throw new SystemErrorException("用户名已存在");
       }

       //md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
//
//       if(Objects.isNull(user.getRole())){
//           throw new SystemErrorException("角色不能为空");
//       }
//       roleRepository.findById(user.getRole().getId())
//               .orElseThrow(()->new SystemErrorException("角色不存在"));
        return userRepository.save(user);
    }

    @Override
    public boolean lockUser(Long id){
//        userRepository.findById(id).ifPresent(user->{
//            switch (user.getUseStatus()){
//                case ENABLED:
//                    user.setUseStatus(UseStatus.DISABLED);
//                    break;
//                case DISABLED:
//                    user.setUseStatus(UseStatus.ENABLED);
//                    break;
//            }
//            userRepository.save(user);
//        });
        return false;
    }

    @Override
    public boolean deleteUser(long id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            return false;
        }else{
            user.ifPresent(value -> userRepository.delete(value));
            return true;
        }
    }

    @Override
    public Optional<User> getUserByUsername(String Username){
        if (StringUtils.isNotBlank(Username)){
           return userRepository.findByUsername(Username);
        }
      return Optional.empty();
    }

    @Override
    public Boolean updateUserInfo(User user) {
        //查询用户名是否存在
        Optional<User> dbUser = userRepository.findByUsername(user.getUsername());
        if(dbUser.isPresent()){
            if(!dbUser.get().getId().equals(user.getId())){
                return false;
            }
        }
        userRepository.findById(user.getId()).ifPresent(detail ->{
            detail.setUsername(user.getUsername());
            detail.setRealName(user.getRealName());
            detail.setPhone(user.getPhone());
            detail.setEmail(user.getEmail());
            detail.setRole(user.getRole());
            detail.setSex(user.getSex());
            detail.setUseStatus(user.getUseStatus());
            userRepository.save(detail);
        });
        return true;
    }

    @Override
    public List<UserDto> listSearch(User user,int pageSize,int currentPage){
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        List<User> result=null;
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        // 构造自定义查询条件
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(user.getRealName())) {
                   predicates.add(criteriaBuilder.like(root.get("realName"), "%" + user.getRealName() + "%"));
                }

                if (StringUtils.isNotBlank(user.getUsername())){
                   predicates.add(criteriaBuilder.like(root.get("username"),"%"+ user.getUsername() + "%"));
                }

                if (StringUtils.isNotBlank((user.getPhone()))){
                   predicates.add(criteriaBuilder.like(root.get("phone"), user.getPhone() + "%"));
                }

                if (StringUtils.isNotBlank((user.getEmail()))){
                   predicates.add(criteriaBuilder.like(root.get("email"), user.getEmail() + "%"));
                }

//                if(!Objects.isNull(user.getRole().getId())){
//                   predicates.add((criteriaBuilder.equal(root.get("role"), user.getRole())));
//                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

        result = userRepository.findAll(specification,pageable).getContent();

        return userMapper.toDto(result);
    }

    @Override
    public Page<User> pageSearch(User user, int pageSize, int currentPage) {
        if(pageSize == 0) {
            pageSize = 10 ;
        }
        if(currentPage < 1){
            currentPage = 1 ;
        }
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(currentPage-1,pageSize,sort);
        // 构造自定义查询条件
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(user.getRealName())) {
                    predicates.add(criteriaBuilder.like(root.get("realName"), "%" + user.getRealName() + "%"));
                }

                if (StringUtils.isNotBlank(user.getUsername())){
                    predicates.add(criteriaBuilder.like(root.get("username"),user.getUsername() + "%"));
                }

                if (StringUtils.isNotBlank((user.getPhone()))){
                    predicates.add(criteriaBuilder.like(root.get("phone"), user.getPhone() + "%"));
                }

                if (StringUtils.isNotBlank((user.getEmail()))){
                    predicates.add(criteriaBuilder.like(root.get("email"), user.getEmail() + "%"));
                }

//                if(!Objects.isNull(user.getRole().getId())){
//                   predicates.add((criteriaBuilder.equal(root.get("role"), user.getRole())));
//                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

        return userRepository.findAll(specification,pageable);
    }

    //    @Override
//    public List<UserDto> search(User user) {
//        List list = userRepository.findAll((Specification<User>) (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            if (StringUtils.isNotBlank(user.getRealName())) {
//                predicates.add(criteriaBuilder.like(root.get("realName"), "%" + user.getRealName() + "%"));
//            }
//
//            if (StringUtils.isNotBlank(user.getUsername())){
//                predicates.add(criteriaBuilder.like(root.get("username"),user.getUsername() + "%"));
//            }
//
//            if (StringUtils.isNotBlank((user.getPhone()))){
//                predicates.add(criteriaBuilder.like(root.get("phone"), user.getPhone() + "%"));
//            }
//
//            if (StringUtils.isNotBlank((user.getEmail()))){
//                predicates.add(criteriaBuilder.like(root.get("email"), user.getEmail() + "%"));
//            }
//
//            if(!Objects.isNull(user.getRole().getId())){
//                predicates.add((criteriaBuilder.equal(root.get("role"), user.getRole())));
//            }
//
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        }, Sort.by(Sort.Direction.DESC, "createTime"));
//        return userMapper.toDto(list);
//    }


//    @Override
//    public int updatePassword(UserVo userVo) {
//        User user = userRepository.findById(userVo.getId()).orElseThrow(()-> new SystemErrorException("用户不存在"));
//        if(bCryptPasswordEncoder.matches(userVo.oldPassword,user.getPassword())){
//            //密码加密
//            user.setPassword(bCryptPasswordEncoder.encode(userVo.getNewPassword()));
//            userRepository.save(user);
//            return 0;
//        }else {
//            return -1;
//        }
//    }

//    @Override
//    public void resetPassword(Long userId) {
//        userRepository.findById(userId).ifPresent(user ->{
//            user.setPassword(bCryptPasswordEncoder.encode(INIT_PASSWORD));
//            userRepository.save(user);
//        });
//    }

//
//    @Override
//    public Page<User> findUserListByPage(int pageSize,int curentPage) {
//        if(curentPage < 1){
//            curentPage=1;
//        }
//        Sort sort = Sort.by(Sort.Direction.DESC,"id");
//        Pageable pageable = PageRequest.of(curentPage-1,pageSize,sort);
//        return userRepository.findAll(pageable);
//    }
}
