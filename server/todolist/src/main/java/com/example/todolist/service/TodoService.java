package com.example.todolist.service;

import com.example.todolist.model.TodoEntity;
import com.example.todolist.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {

    @Autowired
    private TodoRepository repository;
    public String testService() {
        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        //TodoEntity 저장
        repository.save(entity);
        //TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        //검증
        validate(entity);

        repository.save(entity);

        log.info("Entity id : {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        //넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            // 변환된 todoentity가 존재하면 새 entity 값으로 덮자
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            // 엔티티 삭제
            repository.delete(entity);
        } catch(Exception e) {
            log.error("error deleting entity", entity.getId(),e);

            //컨트롤러로 exception 날리고 db 내부 로직을 캡슐화하기 위해 e를 리턴하지 않고 새 exception 오브젝트 리턴
            throw new RuntimeException("error deleting entity"+entity.getId());
        }

        // 새 todo 리스트를 가져와 리턴
        return retrieve(entity.getUserId());
    }
    //리팩토링한 메서드
    public void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("entity는 null이 될 수 없습니다.");
            throw new RuntimeException("entity는 null이 될 수 없습니다.");
        }

        if(entity.getUserId() == null) {
            log.warn("알 수 없는 유저");
            throw new RuntimeException("알 수 없는 유저");
        }
    }


}
