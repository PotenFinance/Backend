--db연결 h2 테스트용
--DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
    user_id VARCHAR(255) PRIMARY KEY, -- INT 대신 VARCHAR 사용
    user_name VARCHAR(255),
    email VARCHAR(255),
    user_cd INT,
    budget DECIMAL(10, 2),
    audit_id VARCHAR(255),
    audit_dtm TIMESTAMP
);
