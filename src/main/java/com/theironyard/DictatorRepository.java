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
                        resultSet.getString("overview_color"),
                        resultSet.getString("overview_content_type"),
                        resultSet.getBytes("overview_image"),
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
                        resultSet.getString("overview_color"),
                        resultSet.getString("overview_content_type"),
                        resultSet.getBytes("overview_image"),
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

    // get a particular dictator by id
    public Dictator getDictatorById(Integer id){
        return jdbcTemplate.queryForObject("SELECT d.*,u.username FROM dictators as d " +
                "JOIN users as u ON d.id_dictators = u.userid " +
                "WHERE id_dictators = ?",
                new Object[]{id},
                (resultSet, i) -> new Dictator(
                        new User(resultSet.getInt("id_dictators"),
                                resultSet.getString("username")),
                        resultSet.getString("overview_blurb"),
                        resultSet.getString("overview_dictatorship_name"),
                        resultSet.getString("overview_mascot"),
                        resultSet.getString("overview_color"),
                        resultSet.getString("overview_content_type"),
                        resultSet.getBytes("overview_image"),
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
                ));
    }

    // saves the user into the database
    public void save(User user) {
        jdbcTemplate.update("INSERT INTO users(username,password,email) VALUES(?,?,?)",
                new Object[]{user.getUsername(),user.getPassword(),user.getEmail()});
    }

    // SAVE/UPDATE DICTATOR
    public void updateDictator(Dictator dictator){
        jdbcTemplate.update("UPDATE dictators SET overview_blurb=?, " +
                        "overview_dictatorship_name=?, overview_mascot=?, overview_color=?, " +
                        "overview_content_type=?, overview_image=?, econ_labor=?, econ_tax=?, econ_trade=?, " +
                        "econ_infrastructure=?, econ_military=?, social_healthcare=?, social_retirement=?, " +
                        "social_education=?, social_environment=?, social_welfare=?, legal_punishment=?, " +
                        "legal_immigration=?, legal_voting_rights=?, legal_privacy_laws=?, legal_weapons=?, " +
                        "pledge=?, revolt=? WHERE id_dictators=?",
                new Object[]{dictator.getOverviewBlurb(),
                        dictator.getOverviewDictatorshipName(),dictator.getOverviewMascot(),
                        dictator.getOverviewColor(),dictator.getOverviewContentType(),
                        dictator.getOverviewImage(),dictator.getEconLabor(),
                        dictator.getEconTax(),dictator.getEconTrade(),dictator.getEconInfrastructure(),
                        dictator.getEconMilitary(),dictator.getSocialHealthcare(),
                        dictator.getSocialRetirement(),dictator.getSocialEducation(),
                        dictator.getSocialEnvironment(),dictator.getSocialWelfare(),
                        dictator.getLegalPunishment(),dictator.getLegalImmigration(),
                        dictator.getLegalVotingRights(),dictator.getLegalPrivacyLaws(),
                        dictator.getLegalWeapons(),dictator.getPledge(),dictator.getRevolt(),
                        dictator.getUser().getId()});
    }

    public void saveDictator(Dictator dictator){
        jdbcTemplate.update("INSERT INTO dictators(id_dictators, overview_blurb, " +
                "overview_dictatorship_name, overview_mascot, overview_color, " +
                "overview_content_type, overview_image, econ_labor, econ_tax, econ_trade, " +
                "econ_infrastructure, econ_military, social_healthcare, social_retirement, " +
                "social_education, social_environment, social_welfare, legal_punishment, " +
                "legal_immigration, legal_voting_rights, legal_privacy_laws, legal_weapons, " +
                "pledge, revolt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{dictator.getUser().getId(),dictator.getOverviewBlurb(),
                dictator.getOverviewDictatorshipName(),dictator.getOverviewMascot(),
                dictator.getOverviewColor(),dictator.getOverviewContentType(),
                dictator.getOverviewImage(),dictator.getEconLabor(),
                dictator.getEconTax(),dictator.getEconTrade(),dictator.getEconInfrastructure(),
                dictator.getEconMilitary(),dictator.getSocialHealthcare(),
                dictator.getSocialRetirement(),dictator.getSocialEducation(),
                dictator.getSocialEnvironment(),dictator.getSocialWelfare(),
                dictator.getLegalPunishment(),dictator.getLegalImmigration(),
                dictator.getLegalVotingRights(),dictator.getLegalPrivacyLaws(),
                dictator.getLegalWeapons(),dictator.getPledge(),dictator.getRevolt()});
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
