package org.wisdom.lecturehub.lecture.application;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("[통합 테스트] 특강 신청 테스트(동시성/비관적 잠금)")
@Sql(scripts = "classpath:script.sql")
class LectureFacadeIntegrationTest {

    @Autowired
    private LectureFacade facade;

    @Test
    @DisplayName("특강 정원이 30명인데 40명이 동시에 특강 신청 시 30명만 성공, 10명은 Exception 발생")
    void test() throws ExecutionException, InterruptedException {

        List<CompletableFuture<Boolean>> tasks = new ArrayList<>();
        List<Integer> exceptionCount = new ArrayList<>();

        for (int i = 1; i <= 40; i++) {
            int userId = i;
            tasks.add(CompletableFuture.supplyAsync(() -> {
                FacadeDto.request request = FacadeDto.request.builder().userId(userId).lectureDetailId(1).build();
                facade.apply(request);
                return true;
            }).exceptionally(ex -> {
                exceptionCount.add(userId);
                return false;
            }));
        }

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        allTasks.join();

        long successCount = 0;
        long failureCount = 0;

        for (CompletableFuture<Boolean> task : tasks) {
            if (task.get()) {
                successCount++;
            } else {
                failureCount++;
            }
        }

        assertThat(successCount).isEqualTo(30);
        assertThat(failureCount).isEqualTo(10);
        assertThat(exceptionCount).hasSize(10);
    }
}