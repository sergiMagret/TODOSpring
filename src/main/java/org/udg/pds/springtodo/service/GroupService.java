package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.repository.GroupRepository;

import java.util.Collection;
import java.util.Optional;

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

    public void addMemberToGroup(Long userId, Long groupId, Long ownerId) {
        try{
            User user = userService.getUser(userId);
            Optional<Group> g = groupRepository.findById(groupId);
            if(!g.isPresent()) throw new ServiceException("Group does not exist");
            if(g.get().getOwner().getId() != ownerId) throw new ServiceException("User does not own this group");

            g.get().addMember(user);

        }catch(Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    public Collection<User> getMembers(Long userId, Long groupId) {
        try{
            User user = userService.getUser(userId);
            Optional<Group> g = groupRepository.findById(groupId);
            if(!g.isPresent()) throw new ServiceException("Group does not exist");
            if(!g.get().getMembers().contains(user))
                throw new ServiceException("User is not a member of Group");

            return g.get().getMembers();
        }catch(Exception e){
            throw new ServiceException(e.getMessage());
        }
    }
}
