CREATE TABLE address
(
    inetaddress VARCHAR(15) NOT NULL
        PRIMARY KEY,
    country     VARCHAR(2) NULL
);

CREATE TABLE user
(
    id        INT UNSIGNED AUTO_INCREMENT
        PRIMARY KEY,
    uuid      BINARY(16) NOT NULL,
    username  VARCHAR(16) NOT NULL,
    lastlogin TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NULL
);

CREATE TABLE name_history
(
    user_id   INT UNSIGNED NOT NULL,
    name      VARCHAR(16)                           NOT NULL,
    last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP (),
    PRIMARY KEY (user_id, name),
    CONSTRAINT fk_name_history_user1
        FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE
    INDEX fk_name_history_user1_idx
    ON name_history (user_id);

CREATE TABLE punishment
(
    id             INT UNSIGNED NOT NULL,
    user           INT UNSIGNED NOT NULL,
    operator       INT UNSIGNED NOT NULL,
    start          TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP (),
    end            TIMESTAMP NULL,
    punishmentType ENUM ('BAN', 'MUTE', 'WARN', 'KICK', 'NOTE') NOT NULL,
    ip             TINYINT                               NOT NULL,
    reason         TEXT                                  NOT NULL,
    layout         VARCHAR(45) NULL,
    expired        TINYINT   DEFAULT 0                   NOT NULL,
    PRIMARY KEY (id, user, operator),
    CONSTRAINT fk_punishment_operator
        FOREIGN KEY (operator) REFERENCES user (id),
    CONSTRAINT fk_punishment_user
        FOREIGN KEY (user) REFERENCES user (id)
);

CREATE
    INDEX fk_punishment_op_idx
    ON punishment (operator);

CREATE
    INDEX fk_punishment_user_idx
    ON punishment (user);

CREATE
    INDEX status
    ON punishment (expired);

CREATE
    INDEX type
    ON punishment (ip);

CREATE TABLE revoked
(
    operator      INT UNSIGNED NOT NULL,
    punishment_id INT UNSIGNED NOT NULL,
    timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL ON UPDATE CURRENT_TIMESTAMP (),
    PRIMARY KEY (operator, punishment_id),
    CONSTRAINT fk_revoked_operator
        FOREIGN KEY (operator) REFERENCES user (id),
    CONSTRAINT fk_revoked_punishment
        FOREIGN KEY (punishment_id) REFERENCES punishment (id)
);

CREATE
    INDEX fk_revoked_punishment_idx
    ON revoked (punishment_id);

CREATE
    INDEX fk_revoked_user_idx
    ON revoked (operator);

CREATE TABLE user_address
(
    user_id             INT UNSIGNED NOT NULL,
    address_inetaddress VARCHAR(15) NOT NULL,
    PRIMARY KEY (user_id, address_inetaddress),
    CONSTRAINT fk_user_address_address
        FOREIGN KEY (address_inetaddress) REFERENCES address (inetaddress),
    CONSTRAINT fk_user_address_user
        FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE
    INDEX fk_address_idx
    ON user_address (address_inetaddress);

CREATE
    INDEX fk_user_idx
    ON user_address (user_id);

CREATE VIEW punishment_history AS
SELECT `advancedban`.`punishment`.`id`             AS `id`,
       `advancedban`.`punishment`.`user`           AS `user`,
       `advancedban`.`punishment`.`operator`       AS `operator`,
       `advancedban`.`punishment`.`start`          AS `start`,
       `advancedban`.`punishment`.`end`            AS `end`,
       `advancedban`.`punishment`.`punishmentType` AS `punishmentType`,
       `advancedban`.`punishment`.`ip`             AS `ip`,
       `advancedban`.`punishment`.`reason`         AS `reason`,
       `advancedban`.`punishment`.`calculation`    AS `calculation`
FROM `advancedban`.`punishment`
WHERE `advancedban`.`punishment`.`expired` = 1;

