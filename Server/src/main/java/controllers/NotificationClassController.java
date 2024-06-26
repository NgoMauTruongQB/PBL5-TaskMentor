package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.response.NotificationClassResponse;
import dto.response.NotificationMessageResponse;
import dto.response.NotificationTeacherSendToClassResponse;
import dto.response.NotificationUserResponse;
import service.NotificationService;
import util.AuthorizationUtil;
import util.RequestProcessor;
import util.ResponseUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@WebServlet("/api/notification-class")
public class NotificationClassController extends HttpServlet {
    private final RequestProcessor requestProcessor = new RequestProcessor();
    private static final NotificationService notificationService = new NotificationService();
    private final SendNotificationController sendNotificationController = new SendNotificationController();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
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
                NotificationTeacherSendToClassResponse notification = mapper.readValue(jsonRequest.toString(), NotificationTeacherSendToClassResponse.class);

                Boolean isSave = notificationService.saveNotificationClass(notification.getClassCode(), notification.getContent(), userId);

                if (isSave) {
                    List<Integer> list = notificationService.getMemberUserIdsOfClass(notification.getClassCode());
                    NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.builder()
                            .classCode(notification.getClassCode())
                            .content(notification.getContent())
                            .build();
                    sendNotificationController.sendNotificationToListUsers(notificationMessageResponse, list);
                }


                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_CREATED, "Class notification successfully.", isSave);
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestProcessor.processRequest(() -> {
            try{
                int userId = AuthorizationUtil.getUserId(request);

                String code = request.getParameter("code");

                List<Integer> requiredRoles = Arrays.asList(2, 1, 0);
                if (!AuthorizationUtil.checkListUserRole(request, response, requiredRoles)) {
                    return;
                }

                List<NotificationClassResponse> notifications = notificationService.getListNotificationByClass(code);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "List notification.", notifications);

            }  catch (IllegalArgumentException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
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
                if(!AuthorizationUtil.checkUserRole(request,response, 2)) {
                    return;
                }
                int userId = AuthorizationUtil.getUserId(request);
                String id = request.getParameter("id");

                Boolean isDelete = notificationService.deleteNotification(Integer.parseInt(id), userId);

                ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Delete successfully!", isDelete);

            }  catch (IllegalArgumentException e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                try {
                    ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
    }


}
