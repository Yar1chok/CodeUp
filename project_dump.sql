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
    id_user bigint NOT NULL,
    email character varying(25) NOT NULL,
    password character varying(25) NOT NULL,
    cur_lvl_java integer NOT NULL,
    user_name character varying(25) NOT NULL
);


ALTER TABLE public.gamer OWNER TO postgres;

--
-- Name: java_tower; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.java_tower (
    id_ques bigint NOT NULL,
    text_ques character varying(600) NOT NULL,
    right_answer character varying(300) NOT NULL,
    wrong_answer_1 character varying(300) NOT NULL,
    wrong_answer_2 character varying(300) NOT NULL,
    wrong_answer_3 character varying(300) NOT NULL,
    level character varying(255) NOT NULL
);


ALTER TABLE public.java_tower OWNER TO postgres;

--
-- Data for Name: gamer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gamer (id_user, email, password, cur_lvl_java, user_name) FROM stdin;
\.


--
-- Data for Name: java_tower; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.java_tower (id_ques, text_ques, right_answer, wrong_answer_1, wrong_answer_2, wrong_answer_3, level) FROM stdin;
\.


--
-- Name: gamer gamer_login_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gamer
    ADD CONSTRAINT gamer_login_key UNIQUE (email);


--
-- Name: gamer gamer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gamer
    ADD CONSTRAINT gamer_pkey PRIMARY KEY (id_user);


--
-- Name: gamer gamer_user_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gamer
    ADD CONSTRAINT gamer_user_name_key UNIQUE (user_name);


--
-- Name: java_tower level_1_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.java_tower
    ADD CONSTRAINT level_1_pkey PRIMARY KEY (id_ques);


--
-- PostgreSQL database dump complete
--

