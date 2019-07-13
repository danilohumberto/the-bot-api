CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

/**
 * CREATE TABLE BOTS
 */
CREATE TABLE public.bots (
	ID UUID NOT NULL DEFAULT uuid_generate_v1(),
	NAME varchar(250) NOT NULL,
	CONSTRAINT bots_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX bots_id_idx ON public.bots (id);

/**
 * CREATE TABLE MESSAGES
 */
CREATE TABLE public.messages (
	ID UUID NOT NULL DEFAULT uuid_generate_v1(),
	CONVERSATION_ID UUID NOT NULL,
	TIME_STAMP timestamp NOT NULL,
	FROM_ UUID NOT NULL,
	TO_ UUID NOT NULL,
	TEXT varchar(250) NOT NULL,
	CONSTRAINT messages_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX messages_id_idx ON public.messages (id);
