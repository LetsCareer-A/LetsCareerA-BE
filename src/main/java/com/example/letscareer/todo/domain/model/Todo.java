package com.example.letscareer.todo.domain.model;

import com.example.letscareer.todo.domain.dto.request.TodoRequest;
import com.example.letscareer.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Setter //필요
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @NotNull
    private User user;

    private String content;
    private boolean isChecked; //boolean null 허용 안함

    public static Todo toEntity(User user, TodoRequest request) {
        return Todo.builder()
                .content(request.todo())
                .isChecked(false)  // Defaulting isChecked to false for new Todos
                .user(user)
                .build();
    }
    public void toggleChecked() {
        this.isChecked = !this.isChecked;
    }

}
