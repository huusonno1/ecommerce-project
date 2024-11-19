package ecom.demoecom.service.impl;

import ecom.demoecom.entity.User;
import ecom.demoecom.repo.UserRepository;
import ecom.demoecom.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User getUserByAccountId(Long accountId) {
        return userRepository.getUserByAccountId(accountId);
    }
}
