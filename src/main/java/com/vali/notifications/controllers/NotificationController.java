package com.vali.notifications.controllers;

import com.vali.notifications.dtos.NotificationDTO;
import com.vali.notifications.entities.Notification;
import com.vali.notifications.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    /**
     * @param notificationDTO The body of the notification to be persisted in the database
     * @return Returns the persisted object, along with it's auto-generated id (by the database vendor, H2 here)
     */
    @PostMapping
    @Operation(summary = "Insert a notification", description = "Provided a correct notification, it is persisted in the database")
    public ResponseEntity<Notification> insertNotification(@RequestBody NotificationDTO notificationDTO) {
        return new ResponseEntity<>(notificationService.putNotificationInDatabase(notificationDTO), HttpStatus.CREATED);
    }

    /**
     * Not on the requirements but just as an easier means to develop the app
     *
     * @return Returns a List object with all the available notifications in the database
     */
    @GetMapping
    @Operation(summary = "Extra method", description = "For development purposes, retrieving all rows from DB")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return new ResponseEntity<>(notificationService.getAllNotifications(), HttpStatus.OK);
    }

    /**
     * This endpoint returns a List with all the last notifications, ordered descending with an imposed limit
     *
     * @param userId                 The userID that requests the list
     * @param limitLastNotifications An Integer parameter that limits how many results are retrieved. Max = 100.
     * @return Returns a List of Notification objects.
     */
    @GetMapping("/{userId}/{limitLastNotifications}")
    @Operation(summary = "Get user's notifications", description = "Returns a list of notifications ordered descending, limited by the value provided")
    public ResponseEntity<List<Notification>> getLastNotificationsOfUserWithLimit(
            @PathVariable Integer userId,
            @PathVariable Integer limitLastNotifications
    ) {
        if ((userId == null)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(notificationService.getLastNotifications(userId, limitLastNotifications), HttpStatus.OK);
    }


    /**
     * In theory, Authentication & Authorization are necessary for such operations.
     * Here, we can just assume the user (if notification exists) owns that notification
     *
     * @param userId          The ID of the user who is doing the operation
     * @param notificationId  The ID of the notification to be modified
     * @param notificationDTO The data transfer object of the notification itself, containing the fields to work with
     * @return A boolean with a little message. True if successful, false otherwise.
     */
    @PutMapping("/{userId}/{notificationId}")
    @Operation(summary = "Update a notification", description = "Update a notification, automatically setting false to hasBeenRead")
    public ResponseEntity<Map<Boolean, String>> updateNotificationByUsedAndNotificationId(
            @PathVariable Integer userId,
            @PathVariable Integer notificationId,
            @RequestBody NotificationDTO notificationDTO
    ) {
        Map<Boolean, String> response = new HashMap<>();
        if ((userId == null) | (notificationId == null)) {
            response.put(false, "Update failed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        boolean operationResult = notificationService.updateNotificationOfUser(userId, notificationId, notificationDTO);
        String responseMessage = operationResult ? "Operation successful" : "Operation unsuccessful";
        response.put(operationResult, responseMessage); //TODO: this can be replaced with bad request
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
