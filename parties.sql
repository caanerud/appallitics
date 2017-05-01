--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: dictators; Type: TABLE; Schema: public; Owner: chrisaanerud
--

CREATE TABLE dictators (
    id integer NOT NULL,
    id_dictators integer NOT NULL,
    overview_blurb character varying(255) NOT NULL,
    overview_mascot character varying(255) NOT NULL,
    overview_picture character varying(255) NOT NULL,
    econ_labor character varying(255) NOT NULL,
    econ_tax character varying(255) NOT NULL,
    econ_trade character varying(255) NOT NULL,
    econ_infrastructure character varying(255) NOT NULL,
    econ_military character varying(255) NOT NULL,
    social_healthcare character varying(255) NOT NULL,
    social_retirement character varying(255) NOT NULL,
    social_education character varying(255) NOT NULL,
    social_environment character varying(255) NOT NULL,
    social_welfare character varying(255) NOT NULL,
    legal_punishment character varying(255) NOT NULL,
    legal_immigration character varying(255) NOT NULL,
    legal_voting_rights character varying(255) NOT NULL,
    legal_privacy_laws character varying(255) NOT NULL,
    legal_weapons character varying(255) NOT NULL,
    pledge integer NOT NULL,
    revolt integer NOT NULL,
    overview_dictatorship_name character varying(255) NOT NULL
);


ALTER TABLE dictators OWNER TO chrisaanerud;

--
-- Name: dictators_id_seq; Type: SEQUENCE; Schema: public; Owner: chrisaanerud
--

CREATE SEQUENCE dictators_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dictators_id_seq OWNER TO chrisaanerud;

--
-- Name: dictators_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chrisaanerud
--

ALTER SEQUENCE dictators_id_seq OWNED BY dictators.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: chrisaanerud
--

CREATE TABLE users (
    userid integer NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    email character varying(255) NOT NULL
);


ALTER TABLE users OWNER TO chrisaanerud;

--
-- Name: users_userid_seq; Type: SEQUENCE; Schema: public; Owner: chrisaanerud
--

CREATE SEQUENCE users_userid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_userid_seq OWNER TO chrisaanerud;

--
-- Name: users_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: chrisaanerud
--

ALTER SEQUENCE users_userid_seq OWNED BY users.userid;


--
-- Name: dictators id; Type: DEFAULT; Schema: public; Owner: chrisaanerud
--

ALTER TABLE ONLY dictators ALTER COLUMN id SET DEFAULT nextval('dictators_id_seq'::regclass);


--
-- Name: users userid; Type: DEFAULT; Schema: public; Owner: chrisaanerud
--

ALTER TABLE ONLY users ALTER COLUMN userid SET DEFAULT nextval('users_userid_seq'::regclass);


--
-- Data for Name: dictators; Type: TABLE DATA; Schema: public; Owner: chrisaanerud
--

COPY dictators (id, id_dictators, overview_blurb, overview_mascot, overview_picture, econ_labor, econ_tax, econ_trade, econ_infrastructure, econ_military, social_healthcare, social_retirement, social_education, social_environment, social_welfare, legal_punishment, legal_immigration, legal_voting_rights, legal_privacy_laws, legal_weapons, pledge, revolt, overview_dictatorship_name) FROM stdin;
1	1	Crushing all who oppose me	Wild Boar	folder/images/bobsmith	I wish to provide gainful employment to all of my subjects.	I don't plan to pay my subjects, so taxing them on top of that wouldn't make sense	I will take what I need	Infra-what?	We will create the mightiest army the world has ever seen.	Universal healthcare will be the cornerstone of my platform 	You retire when you die	Learning how to be properly demeaned in my presence will be taught at an early age	We will utilize trees to build long, pointy spears	You will fare well as my minion, I promise you that	Torture will be common and painful	I will conquer many lands and bring many new subjects into my fold	I will easily win every election at any cost	I will gladly let you live your lives until something you do affects me in an unpleasant way.	I will keep the best weapons for myself and my subjects can have the scraps from my table.	1	-1	WreckFaceOcrats
\.


--
-- Name: dictators_id_seq; Type: SEQUENCE SET; Schema: public; Owner: chrisaanerud
--

SELECT pg_catalog.setval('dictators_id_seq', 1, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: chrisaanerud
--

COPY users (userid, password, username, email) FROM stdin;
1	12345	bobsmith	bobsmith7654@yahoo.com
\.


--
-- Name: users_userid_seq; Type: SEQUENCE SET; Schema: public; Owner: chrisaanerud
--

SELECT pg_catalog.setval('users_userid_seq', 1, true);


--
-- Name: dictators dictators_pkey; Type: CONSTRAINT; Schema: public; Owner: chrisaanerud
--

ALTER TABLE ONLY dictators
    ADD CONSTRAINT dictators_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: chrisaanerud
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userid);


--
-- Name: dictators_id_uindex; Type: INDEX; Schema: public; Owner: chrisaanerud
--

CREATE UNIQUE INDEX dictators_id_uindex ON dictators USING btree (id);


--
-- Name: users_email_uindex; Type: INDEX; Schema: public; Owner: chrisaanerud
--

CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);


--
-- Name: users_userid_uindex; Type: INDEX; Schema: public; Owner: chrisaanerud
--

CREATE UNIQUE INDEX users_userid_uindex ON users USING btree (userid);


--
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: chrisaanerud
--

CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);


--
-- Name: dictators dictators_users_userid_fk; Type: FK CONSTRAINT; Schema: public; Owner: chrisaanerud
--

ALTER TABLE ONLY dictators
    ADD CONSTRAINT dictators_users_userid_fk FOREIGN KEY (id_dictators) REFERENCES users(userid);


--
-- PostgreSQL database dump complete
--

