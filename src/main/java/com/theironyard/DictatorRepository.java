package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by chrisaanerud on 4/28/17.
 */
@Component
public class DictatorRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // ** LEADERBOARD RELATED **
    // list of the best dictators
    public List<Dictator> listBestDictators (String search){
        return jdbcTemplate.query(
                "SELECT users.username, dictators.* FROM dictators JOIN users " +
                        "ON dictators.id_dictators = users.userid WHERE lower(overview_dictatorship_name) " +
                        "LIKE lower(?) ORDER BY pledge DESC",
                new Object[]{"%" + search + "%"},
                ((resultSet, i) -> new Dictator(new User(
                        resultSet.getInt("id_dictators"),
                        resultSet.getString("username")),
                        resultSet.getString("overview_blurb"),
                        resultSet.getString("overview_dictatorship_name"),
                        resultSet.getString("overview_mascot"),
                        resultSet.getString("overview_picture"),
                        resultSet.getString("econ_labor"),
                        resultSet.getString("econ_tax"),
                        resultSet.getString("econ_trade"),
                        resultSet.getString("econ_infrastructure"),
                        resultSet.getString("econ_military"),
                        resultSet.getString("social_healthcare"),
                        resultSet.getString("social_retirement"),
                        resultSet.getString("social_education"),
                        resultSet.getString("social_environment"),
                        resultSet.getString("social_welfare"),
                        resultSet.getString("legal_punishment"),
                        resultSet.getString("legal_immigration"),
                        resultSet.getString("legal_voting_rights"),
                        resultSet.getString("legal_privacy_laws"),
                        resultSet.getString("legal_weapons"),
                        resultSet.getInt("revolt"),
                        resultSet.getInt("pledge")
                ))
        );
    }

    // list of the worse dictators
    public List<Dictator> listWorstDictators (String search){
        return jdbcTemplate.query(
                "SELECT users.username, dictators.* FROM dictators JOIN users " +
                        "ON dictators.id_dictators = users.userid WHERE lower(overview_dictatorship_name) " +
                        "LIKE lower(?) ORDER BY revolt ASC",
                new Object[]{"%" + search + "%"},
                ((resultSet, i) -> new Dictator(new User(
                        resultSet.getInt("id_dictators"),
                        resultSet.getString("username")),
                        resultSet.getString("overview_blurb"),
                        resultSet.getString("overview_dictatorship_name"),
                        resultSet.getString("overview_mascot"),
                        resultSet.getString("overview_picture"),
                        resultSet.getString("econ_labor"),
                        resultSet.getString("econ_tax"),
                        resultSet.getString("econ_trade"),
                        resultSet.getString("econ_infrastructure"),
                        resultSet.getString("econ_military"),
                        resultSet.getString("social_healthcare"),
                        resultSet.getString("social_retirement"),
                        resultSet.getString("social_education"),
                        resultSet.getString("social_environment"),
                        resultSet.getString("social_welfare"),
                        resultSet.getString("legal_punishment"),
                        resultSet.getString("legal_immigration"),
                        resultSet.getString("legal_voting_rights"),
                        resultSet.getString("legal_privacy_laws"),
                        resultSet.getString("legal_weapons"),
                        resultSet.getInt("revolt"),
                        resultSet.getInt("pledge")
                ))
        );
    }


    // ** LOGIN RELATED **
    // get a particular user by their username
    public User getByUserName(String userName) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE lower(username) = lower(?)",
                new Object[]{userName},
                (resultSet, i) -> new User(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
    }

    // saves the user into the database
    public void save(User user) {
        jdbcTemplate.update("INSERT INTO users(username,password,email) VALUES(?,?,?)",
                new Object[]{user.getUsername(),user.getPassword(),user.getEmail()});
    }

    // list of users
    public List<User> listUsers(){
        return jdbcTemplate.query("SELECT * FROM users",
                (resultSet, i) -> new User(
                        resultSet.getInt("userid"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                ));
    }
}
