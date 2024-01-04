package com.sparta.teamnews.aop;

import com.sparta.teamnews.entity.Like;
import com.sparta.teamnews.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.concurrent.RejectedExecutionException;

public class LikeOwnerCheckAspect {
    @Pointcut("execution(* com.sparta.teamnews.service.LikeService.deletePostLike(..))")
    public void deletePostLike() {
    }

    @Before("deletePostLike()")
    public Object executePostOwnerCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        Like like = (Like) arguments[0];
        User user = (User) arguments[1];

        if (!like.getUser().equals(user)) {
            throw new RejectedExecutionException("자신의 좋아요만 취소 가능합니다.");
        }

        return joinPoint.proceed();
    }
}
