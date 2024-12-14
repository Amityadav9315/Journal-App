package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user) {
        // Validate the username is not null or empty
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw new RuntimeException("Username cannot be null or empty");
        }

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Handle the case of duplicate username error due to MongoDB's unique constraint
            throw new RuntimeException("Username already exists: " + user.getUserName(), e);
        } catch (Exception e) {
            // Catch all other exceptions
            throw new RuntimeException("An unexpected error occurred while saving the user", e);
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
