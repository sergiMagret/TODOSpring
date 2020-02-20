package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.repository.GroupRepository;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    public GroupRepository crud() { return groupRepository; }

    public IdObject addGroup(Long ownerId, String name, String description){
        try{
            User user = userService.getUser(ownerId);
            Group group = new Group(name, description);

            group.setOwner(user);

            user.addGroup(group);

            groupRepository.save(group);
            return new IdObject(group.getId());
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }
}
