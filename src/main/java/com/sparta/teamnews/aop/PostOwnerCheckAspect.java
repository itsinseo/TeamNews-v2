package com.sparta.teamnews.aop;

import com.sparta.teamnews.entity.Post;
import com.sparta.teamnews.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

@Aspect
@Component
public class PostOwnerCheckAspect {

    @Pointcut("execution(* com.sparta.teamnews.service.PostService.updatePost(..))")
    public void updatePost() {
    }

    @Pointcut("execution(* com.sparta.teamnews.service.PostService.deletePost(..))")
    public void deletePost() {
    }

    @Around("updatePost()||deletePost()")
    public Object executePostOwnerCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        Post post = (Post) arguments[0];
        User user = (User) arguments[1];

        if (!post.getUser().equals(user)) {
            throw new RejectedExecutionException("자신의 게시글만 수정/삭제 가능합니다.");
        }

        return joinPoint.proceed();
    }
}