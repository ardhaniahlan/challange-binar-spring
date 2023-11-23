package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.Config;
import com.challangebinarspring.binarfud.entity.User;
import com.challangebinarspring.binarfud.entity.oauth.Role;
import com.challangebinarspring.binarfud.repository.UserRepository;
import com.challangebinarspring.binarfud.repository.oauth.RoleRepository;
import com.challangebinarspring.binarfud.request.LoginModel;
import com.challangebinarspring.binarfud.request.RegisterModel;
import com.challangebinarspring.binarfud.service.UserService;
import com.challangebinarspring.binarfud.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;

@Service
@Slf4j
public class UserImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserImpl.class);

    @Autowired
    RoleRepository repoRole;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${BASEURL}")
    private String baseUrl;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public Response response;

    @Override
    public Map save(User user) {
        try{
            log.info("Save User");

            if (user.getUsername().isEmpty()){
                return response.errorResponse(Config.NAME_REQUIRED);
            }

            return response.successResponse(userRepository.save(user));
        } catch (Exception e){
            log.error("Save User Error: " + e.getMessage());
            return response.errorResponse("Save User Error: " + e.getMessage());
        }
    }

    @Override
    public Map update(User user) {
        try{
            log.info("Update User");

            if (user.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<User> checkData = userRepository.findById(user.getId());
            if (checkData.isEmpty()){
                return response.errorResponse("Merchant Not Found");
            }

            checkData.get().setUsername(user.username);
            checkData.get().setEmail(user.email);
            checkData.get().setPassword(user.password);

            return response.successResponse(userRepository.save(checkData.get()));
        } catch (Exception e){
            log.error("Update User Error: " + e.getMessage());
            return response.errorResponse("Update User Error: " + e.getMessage());
        }
    }

    @Override
    public Map delete(User user) {
        try{
            log.info("Delete User");

            if (user.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<User> checkData = userRepository.findById(user.getId());
            if (checkData.isEmpty()){
                return response.errorResponse("Merchant Not Found");
            }

            checkData.get().setDeleted_date(new Date());
            return response.successResponse(Config.DELETE_SUCCESS);
        }catch (Exception e){
            log.error("Delete User Error: " + e.getMessage());
            return response.errorResponse("Delete User Error: " + e.getMessage());
        }
    }

    @Override
    public Map getById(Long user) {
        Map map = new HashMap<>();
        Optional<User> getBaseOptional = userRepository.findById(user);
        if(getBaseOptional.isEmpty()){
            map.put("message", "Data not Found");
            return map;
        }

        map.put("Data", getBaseOptional.get());
        return map;
    }

    @Override
    public Map registerManual(RegisterModel objModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"}; // admin
            User user = new User();
            user.setUsername(objModel.getUsername().toLowerCase());
            user.setFullname(objModel.getFullname());

            //step 1 :
//            user.setEnabled(false); // matikan user

            String password = encoder.encode(objModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = repoRole.findByNameIn(roleNames);

            user.setRoles(r);
            user.setPassword(password);
            User obj = userRepository.save(user);

            return response.templateSukses(obj);

        } catch (Exception e) {
            logger.error("Eror registerManual=", e);
            return response.templateEror("eror:" + e);
        }

    }

    @Override
    public Map registerByGoogle(RegisterModel objModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"}; // ROLE DEFAULE
            User user = new User();
            user.setUsername(objModel.getUsername().toLowerCase());
            user.setFullname(objModel.getFullname());
            //step 1 :
            user.setEnabled(false); // matikan user
            String password = encoder.encode(objModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = repoRole.findByNameIn(roleNames);
            user.setRoles(r);
            user.setPassword(password);
            User obj = userRepository.save(user);
            return response.successResponse(obj);

        } catch (Exception e) {
            logger.error("Eror registerManual=", e);
            return response.errorResponse("eror:"+e);
        }
    }

    @Override
    public Map login(LoginModel loginModel) {
        /**
         * bussines logic for login here
         * **/
        try {
            Map<String, Object> map = new HashMap<>();

            User checkUser = userRepository.findOneByUsername(loginModel.getUsername());

            if ((checkUser != null) && (encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                if (!checkUser.isEnabled()) {
                    map.put("is_enabled", checkUser.isEnabled());
                    return response.templateEror(map);
                }
            }
            if (checkUser == null) {
                return response.notFound("user not found");
            }
            if (!(encoder.matches(loginModel.getPassword(), checkUser.getPassword()))) {
                return response.templateEror("wrong password");
            }
            String url = baseUrl + "/oauth/token?username=" + loginModel.getUsername() +
                    "&password=" + loginModel.getPassword() +
                    "&grant_type=password" +
                    "&client_id=my-client-web" +
                    "&client_secret=password";
            ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new
                    ParameterizedTypeReference<Map>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK) {
                User user = userRepository.findOneByUsername(loginModel.getUsername());
                List<String> roles = new ArrayList<>();

                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                }
                //save token
//                checkUser.setAccessToken(response.getBody().get("access_token").toString());
//                checkUser.setRefreshToken(response.getBody().get("refresh_token").toString());
//                userRepository.save(checkUser);

                map.put("access_token", response.getBody().get("access_token"));
                map.put("token_type", response.getBody().get("token_type"));
                map.put("refresh_token", response.getBody().get("refresh_token"));
                map.put("expires_in", response.getBody().get("expires_in"));
                map.put("scope", response.getBody().get("scope"));
                map.put("jti", response.getBody().get("jti"));

                return map;
            } else {
                return UserImpl.this.response.notFound("user not found");
            }
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return response.templateEror("invalid login:"+e);
            }
            return response.templateEror(e);
        } catch (Exception e) {
            e.printStackTrace();

            return response.templateEror(e);
        }
    }
}
