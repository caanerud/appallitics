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


    public User getByUserName(String userName) {
        return jdbcTemplate;
    }

    public void save(User user) {

    }
}
