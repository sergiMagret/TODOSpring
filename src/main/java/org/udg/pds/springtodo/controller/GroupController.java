package org.udg.pds.springtodo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.service.GroupService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RequestMapping(path="/groups")
@RestController
public class GroupController extends BaseController {
    @Autowired
    GroupService groupService;

    @PostMapping(consumes = "application/json")
    public IdObject addGroup(HttpSession session, @Valid @RequestBody R_Group group){
        Long userId = getLoggedUser(session);

        return groupService.addGroup(userId, group.name, group.description);
    }

    @PostMapping(path="/{gid}/members/{uid}")
    public String addMemberToGroup(HttpSession session, @PathVariable("gid") Long groupId, @PathVariable("uid") Long userId){
        Long ownerId = getLoggedUser(session);
        groupService.addMemberToGroup(userId, groupId, ownerId);

        return BaseController.OK_MESSAGE;
    }

    @GetMapping(path="/{gid}/members")
    public Collection<User> getMembers(HttpSession session, @PathVariable("gid") Long groupId){
        Long userId = getLoggedUser(session);

        return groupService.getMembers(userId, groupId);
    }

    static class R_Group {
        @NotNull
        public String name;

        @NotNull
        public String description;
    }
}
