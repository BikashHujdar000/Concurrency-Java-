package com.example.bikash.SpringTest.ActivityLogs;

import com.example.bikash.SpringTest.Model.User;
import com.example.bikash.SpringTest.Repos.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Aspect
@Component
public class UserActivityLogAspects {
    private  final UserRepository userRepository;

    private  final UserActivityLogService userActivityLogService;


    public UserActivityLogAspects(UserRepository userRepository, UserActivityLogService userActivityLogService) {
        this.userRepository = userRepository;

        this.userActivityLogService = userActivityLogService;
    }

    @AfterReturning("execution(* com.example.bikash..Controller..*(..))")
    public void logSuccess(JoinPoint joinPoint) {
        logActivity(joinPoint,true,null);
    }


    private  HttpServletRequest getRequest()
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       return  attributes!=null?attributes.getRequest():null;
    }


    @AfterThrowing(pointcut = "execution(* com.example.bikash..Controller..*(..))", throwing = "exception")
    public void logFailure(JoinPoint joinPoint, Exception exception) {
        logActivity(joinPoint,false,exception.getMessage());
    }


    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return "Anonymous";
    }

    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            // Replace with actual method to get user ID
            String username = ((UserDetails) principal).getUsername();
           Optional<User> user =   userRepository.findByEmail(username);
            return user.map(User::getId).orElseThrow(()-> new RuntimeException("User not found"));

        }
        return null;
    }



    private  void logActivity(JoinPoint joinPoint, boolean success, String errorMesssage) {

        HttpServletRequest request = getRequest();
        String username = getCurrentUsername();
        Long userId = getCurrentUserId();
        String methodName = joinPoint.getSignature().getName();
//        String classNmae = joinPoint.getTarget().getClass().getName();
        String apiUrl = request != null ? request.getRequestURI() : "UNKNOWN";
        String httpMethod = request != null ? request.getMethod() : "UNKNOWN";
        String ipAddress = request != null ? request.getRemoteAddr() : "UNKNOWN";
        String description = "UserId " +userId + " performed " +methodName;
        if(errorMesssage != null) {
            description = description+ "| Error: " + errorMesssage;
        }
        boolean status = success;
        userActivityLogService.logActivity(userId,username,methodName.toUpperCase(),description,status,apiUrl,httpMethod,ipAddress);

    }


}


