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
-- Name: dictators; Type: TABLE; Schema: public; Owner: JamesHartanto
--

CREATE TABLE dictators (
    id integer NOT NULL,
    id_dictators integer NOT NULL,
    overview_blurb character varying(255) NOT NULL,
    overview_mascot character varying(255) NOT NULL,
    overview_content_type character varying(255) NOT NULL,
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
    overview_dictatorship_name character varying(255) NOT NULL,
    overview_image bytea NOT NULL
);


ALTER TABLE dictators OWNER TO "JamesHartanto";

--
-- Name: dictators_id_seq; Type: SEQUENCE; Schema: public; Owner: JamesHartanto
--

CREATE SEQUENCE dictators_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dictators_id_seq OWNER TO "JamesHartanto";

--
-- Name: dictators_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: JamesHartanto
--

ALTER SEQUENCE dictators_id_seq OWNED BY dictators.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: JamesHartanto
--

CREATE TABLE users (
    userid integer NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    email character varying(255) NOT NULL
);


ALTER TABLE users OWNER TO "JamesHartanto";

--
-- Name: users_userid_seq; Type: SEQUENCE; Schema: public; Owner: JamesHartanto
--

CREATE SEQUENCE users_userid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_userid_seq OWNER TO "JamesHartanto";

--
-- Name: users_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: JamesHartanto
--

ALTER SEQUENCE users_userid_seq OWNED BY users.userid;


--
-- Name: dictators id; Type: DEFAULT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY dictators ALTER COLUMN id SET DEFAULT nextval('dictators_id_seq'::regclass);


--
-- Name: users userid; Type: DEFAULT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY users ALTER COLUMN userid SET DEFAULT nextval('users_userid_seq'::regclass);


--
-- Data for Name: dictators; Type: TABLE DATA; Schema: public; Owner: JamesHartanto
--

COPY dictators (id, id_dictators, overview_blurb, overview_mascot, overview_content_type, econ_labor, econ_tax, econ_trade, econ_infrastructure, econ_military, social_healthcare, social_retirement, social_education, social_environment, social_welfare, legal_punishment, legal_immigration, legal_voting_rights, legal_privacy_laws, legal_weapons, pledge, revolt, overview_dictatorship_name, overview_image) FROM stdin;
\.


--
-- Name: dictators_id_seq; Type: SEQUENCE SET; Schema: public; Owner: JamesHartanto
--

SELECT pg_catalog.setval('dictators_id_seq', 1, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: JamesHartanto
--

COPY users (userid, password, username, email) FROM stdin;
\.


--
-- Name: users_userid_seq; Type: SEQUENCE SET; Schema: public; Owner: JamesHartanto
--

SELECT pg_catalog.setval('users_userid_seq', 2, true);


--
-- Name: dictators dictators_pkey; Type: CONSTRAINT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY dictators
    ADD CONSTRAINT dictators_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userid);


--
-- Name: dictators_id_uindex; Type: INDEX; Schema: public; Owner: JamesHartanto
--

CREATE UNIQUE INDEX dictators_id_uindex ON dictators USING btree (id);


--
-- Name: users_email_uindex; Type: INDEX; Schema: public; Owner: JamesHartanto
--

CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);


--
-- Name: users_userid_uindex; Type: INDEX; Schema: public; Owner: JamesHartanto
--

CREATE UNIQUE INDEX users_userid_uindex ON users USING btree (userid);


--
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: JamesHartanto
--

CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);


--
-- Name: dictators dictators_users_userid_fk; Type: FK CONSTRAINT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY dictators
    ADD CONSTRAINT dictators_users_userid_fk FOREIGN KEY (id_dictators) REFERENCES users(userid);


--
-- PostgreSQL database dump complete
--

