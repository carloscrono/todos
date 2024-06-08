package com.api.todos.dao;

import com.api.todos.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TodoDAO {

    //Injecting the jdbcTemplate to create SQL Queries
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Retrieves all todos
     * @return all todos in the database
     */
    public List<Todo> findAll() {
        return jdbcTemplate.query("SELECT * FROM todo", new TodoRowMapper());
    }


    /**
     * Retrieve an specific todo
     * @param id [The id of the todo]
     * @return The specific todo
     */
    public Todo findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM todo WHERE id = ?", new Object[]{id}, new TodoRowMapper());
    }


    /**
     * Saves a todo entity
     * @param todo [The entity of the todo to store]
     * @return the new todo saved id
     */
    public int save(Todo todo) {
        return jdbcTemplate.update("INSERT INTO todo (description, completed) VALUES (?, ?)",
                todo.getDescription(), todo.isCompleted());
    }

    /**
     * Updates a todo
     * @param todo the todo entity to update
     * @return the updated todo id
    */
    public int update(Todo todo) {
        return jdbcTemplate.update("UPDATE todo SET description = ?, completed = ? WHERE id = ?",
                todo.getDescription(), todo.isCompleted(), todo.getId());
    }

    /**
     * Deletes a todo by id
     * @param id
     * @return the deleted todo id
     */
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM todo WHERE id = ?", id);
    }

    private static class TodoRowMapper implements RowMapper<Todo> {
        @Override
        public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Todo todo = new Todo();
            todo.setId(rs.getLong("id"));
            todo.setDescription(rs.getString("description"));
            todo.setCompleted(rs.getBoolean("completed"));
            return todo;
        }
    }
}
