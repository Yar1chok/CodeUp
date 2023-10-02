--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0 (Debian 16.0-1.pgdg120+1)
-- Dumped by pg_dump version 16.0 (Debian 16.0-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: gamer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gamer (
    id_user integer NOT NULL,
    login character varying(25) NOT NULL,
    password character varying(25) NOT NULL,
    cur_lvl integer NOT NULL
);


ALTER TABLE public.gamer OWNER TO postgres;

--
-- Name: level_1; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.level_1 (
    id_ques integer NOT NULL,
    text_ques character varying(600) NOT NULL,
    right_answer character varying(300) NOT NULL,
    wrong_answer_1 character varying(300) NOT NULL,
    wrong_answer_2 character varying(300) NOT NULL,
    wrong_answer_3 character varying(300) NOT NULL
);


ALTER TABLE public.level_1 OWNER TO postgres;

--
-- Data for Name: gamer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gamer (id_user, login, password, cur_lvl) FROM stdin;
\.


--
-- Data for Name: level_1; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.level_1 (id_ques, text_ques, right_answer, wrong_answer_1, wrong_answer_2, wrong_answer_3) FROM stdin;
\.


--
-- Name: gamer gamer_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gamer
    ADD CONSTRAINT gamer_login_key UNIQUE (login);


--
-- Name: gamer gamer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gamer
    ADD CONSTRAINT gamer_pkey PRIMARY KEY (id_user);


--
-- Name: level_1 level_1_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.level_1
    ADD CONSTRAINT level_1_pkey PRIMARY KEY (id_ques);


--
-- PostgreSQL database dump complete
--

