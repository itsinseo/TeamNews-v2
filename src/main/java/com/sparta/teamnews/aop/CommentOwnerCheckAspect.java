package com.sparta.teamnews.aop;

import com.sparta.teamnews.entity.Comment;
import com.sparta.teamnews.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

@Aspect
@Component
public class CommentOwnerCheckAspect {
    @Pointcut("execution(* com.sparta.teamnews.service.CommentService.updateComment(..))")
    public void updateComment() {
    }

    @Pointcut("execution(* com.sparta.teamnews.service.CommentService.deleteComment(..))")
    public void deleteComment() {
    }

    @Around("updateComment()||deleteComment()")
    public Object executePostOwnerCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        Comment comment = (Comment) arguments[0];
        User user = (User) arguments[1];

        if (!comment.getUser().equals(user)) {
            throw new RejectedExecutionException("자신의 댓글만 수정/삭제 가능합니다.");
        }

        return joinPoint.proceed();
    }
}
