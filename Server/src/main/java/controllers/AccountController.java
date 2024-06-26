package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.request.UserUpdateRequest;
import dto.response.UserResponse;
import model.User;
import service.UserService;
import util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@WebServlet("/api/user")
public class AccountController extends HttpServlet {

    private final UserService userService = new UserService();
    private final RequestProcessor requestProcessor = new RequestProcessor();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestProcessor.processRequest(() -> {
            try {
                if(!AuthorizationUtil.checkUserRole(req,resp, 3)) {
                    return;
                }

                String id = req.getParameter("id");

                if (id != null && !id.isEmpty()) {
                    getUserById(req, resp, id);
                } else {
                    getAllUsers(req, resp);
                }
            } catch (NumberFormatException e) {
                try {
                    ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid page number or page size format.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                try {
                    ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
//list user
    private void getAllUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!AuthorizationUtil.checkUserRole(req, resp, 3)) {
            return;
        }

        List<UserResponse> userList = userService.getAllUsers();

        ResponseUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, "User list retrieved successfully.", userList);
    }

    private void getUserById(HttpServletRequest req, HttpServletResponse resp, String id) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(id);

            UserResponse user = userService.getUserById(userId);

            if (user != null) {
                ResponseUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, "User information retrieved successfully.", user);
            } else {
                throw new IllegalArgumentException("User with id " + userId + " not found.");
            }
        } catch (NumberFormatException e) {
            ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid user id format.");
        } catch (IllegalArgumentException e) {
            ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            System.out.println("e = " + e);
            ResponseUtil.sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while retrieving user information.");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                if(!AuthorizationUtil.checkUserRole(request, response, 3)) {
                    return;
                }
                int userId = AuthorizationUtil.getUserId(request);
                // read data from JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder jsonRequest = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonRequest.append(line);
                }

                // parse JSON to User object
                ObjectMapper mapper = new ObjectMapper();
                User user = mapper.readValue(jsonRequest.toString(), User.class);

                // teacher account
                User teacherAccount = userService.createTeacherAccount(user.getEmail(), user.getUsername(), user.getName(), user.getPhone(), userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Account created successfully.", teacherAccount);
            } catch (IllegalArgumentException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (IOException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try {
                int userId = AuthorizationUtil.getUserId(request);

                if(!AuthorizationUtil.checkUserRole(request, response, 3)) {
                    return;
                }

                int id = Integer.parseInt(request.getParameter("id"));

                Boolean isDelete = userService.deleteUser(id, userId);

                // Trả về thông báo thành công
                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Deleted successfully.", isDelete);
            } catch (IllegalArgumentException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (IOException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)  {
        requestProcessor.processRequest(() -> {
            try {
                int userId = AuthorizationUtil.getUserId(request);
                // read data from JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder jsonRequest = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonRequest.append(line);
                }

                // parse JSON to User object
                ObjectMapper mapper = new ObjectMapper();
                UserUpdateRequest user = mapper.readValue(jsonRequest.toString(), UserUpdateRequest.class);
                System.out.println("user = " + user);

                // teacher account
                User userResponse = userService.updateUser(user, userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Account update successfully.", userResponse);
            } catch (IllegalArgumentException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (IOException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
