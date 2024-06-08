package com.api.todos.dao;

import com.api.todos.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "SELECT * FROM todo WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new TodoRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    /**
     * Saves a todo entity
     * @param todo [The entity of the todo to store]
     * @return the new todo saved
     */
    public Todo save(Todo todo) {
        String sql = "INSERT INTO todo (description, completed) VALUES (?, ?)";
        jdbcTemplate.update(sql, todo.getDescription(), todo.isCompleted());
        // Assuming the todo id is auto-generated and we fetch it after insertion
        Long id = jdbcTemplate.queryForObject("SELECT last_insert_rowid()", Long.class);
        todo.setId(id);
        return todo;
    }

    /**
     * Updates a todo
     *
     * @param todo the todo entity to update
     */
    public void update(Todo todo) {
        jdbcTemplate.update("UPDATE todo SET description = ?, completed = ? WHERE id = ?",
                todo.getDescription(), todo.isCompleted(), todo.getId());
    }

    /**
     * Deletes a todo by id
     *
     * @param id
     */
    public Long deleteById(Long id) {
        Long i=id;
        jdbcTemplate.update("DELETE FROM todo WHERE id = ?", id);
        return i;
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
