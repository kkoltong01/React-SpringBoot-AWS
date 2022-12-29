package com.example.todolist.dto;

import com.example.todolist.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    /*
    * 클라이언트 요청 -> DTO -> Entity로 변환해서 저장 -> 서비스로직 돌고 반환하는 Entity DTO로 변환해서 리턴 -> 클라이언트
    * */


    // DTO를 Entity로 변환
    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
