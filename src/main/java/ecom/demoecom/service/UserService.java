package ecom.demoecom.service;

import ecom.demoecom.entity.User;

public interface UserService {
    User getUserByAccountId(Long accountId);
}
