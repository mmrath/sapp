package com.mmrath.sapp.api.security;

import com.mmrath.sapp.ResourceNotFoundException;
import com.mmrath.sapp.domain.security.User;
import com.mmrath.sapp.domain.security.UserSearchFilter;
import com.mmrath.sapp.service.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public User getUser(@PathVariable("id") Long id) {
    User user = userService.findUser(id);
    if(user==null){
      throw new ResourceNotFoundException("User not found");
    }
    return user;
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public Page<User> getUsers(UserSearchFilter userSearchCriteria, Pageable pageRequest) {
    logger.debug("Page request:{}", pageRequest);
    logger.debug("User search criteria:{}", userSearchCriteria);
    return null;
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseBody
  public User createUser(@Valid @RequestBody User user) {
    logger.debug("User to create:{}", user);
    userService.createUser(user);
    return user;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
    logger.debug("User to update:{}", user);
    user = userService.updateUser(user);
    return user;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.OK)
  public Long deleteUser(@PathVariable("id") Long id) {
    logger.debug("User to delete:{}", id);
    return userService.deleteUser(id);
  }
}
