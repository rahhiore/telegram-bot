-- liquibase formatted sql


create TABLE notification_tasks (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    chat_id BIGSERIAL NOT NULL,
    message TEXT NOT NULL,
    notification_date_time TIMESTAMP NOT NULL
);