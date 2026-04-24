package com.ecommerce.user.services;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

//    private List<User> userList  = new ArrayList<>();
//    private int nextId = 1;

    public List<UserResponse> fetchUsers() {
//         return userList;
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());

    }

    public void addUsers(UserRequest userRequest) {

//        user.setId(nextId++);
//
//        userList.add(user);
//        return userList;



        User user = new User();

        updateUserFromRequest(user, userRequest);

        userRepository.save(user);


    }


    public Optional<UserResponse> fetchUser(String id) {

//        for(User user : userList){
//            if(user.getId()==id){
//                return user;
//            }
//
//        } return null;

//        return userList.stream()
//                .filter(user->user.getId()==id)
//                .findFirst();
        return userRepository.findById(String.valueOf(id)).map(this::mapToUserResponse);

    }

    public boolean updateUser(String id, UserRequest updatedUserRequest) {

//        return userList.stream()
//                .filter(user-> user.getId()==id)
//                .findFirst()
//                .map(existingUser->{
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                    return true;
//                }).orElse(false);

        return userRepository.findById(String.valueOf(id))
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }


    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());

            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user) {

        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {

            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);


        }

        return response;
    }


}
