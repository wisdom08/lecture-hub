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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@Sql(scripts = "classpath:script.sql")
@SpringBootTest
@DisplayName("[통합 테스트] 특강 신청 테스트(동시성/비관적 잠금)")
class LectureFacadeIntegrationTest {

    @Autowired
    private LectureFacade facade;

    @Test
    void 특강_정원이_30명인데_40명이_동시에_특강신청시_30명만_성공_10명은_Exception_발생() throws ExecutionException, InterruptedException {

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

    @Test
    void 동일한_유저가_같은_특강을_5번_신청시_첫번째는_성공_나머지는_예외_발생() {
        int userId = 1;
        FacadeDto.request request = FacadeDto.request.builder().userId(userId).lectureDetailId(1).build();

        // userId-1의 첫 번째 특강 신청은 에러가 발생하지 않음
        assertThatNoException().isThrownBy(() -> facade.apply(request));

        // userId-1의 두 번째 특강 신청부터는 모두 예외가 발생해야 함
        for (int i = 0; i < 4; i++) {
            assertThatThrownBy(() -> facade.apply(request))
                .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("이미 신청한 특강입니다");
        }
    }
}