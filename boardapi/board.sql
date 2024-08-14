DROP TABLE IF EXISTS tbl_board;

CREATE TABLE tbl_board(
    no INTEGER AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    writer VARCHAR(50) NOT NULL,
    reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP);

INSERT INTO tbl_board (title, content, writer)
VALUES
    ('테스트 제목 1', '테스트 내용 1', 'user00'),
    ('테스트 제목 2', '테스트 내용 2', 'user00'),
    ('테스트 제목 3', '테스트 내용 3', 'user00'),
    ('테스트 제목 4', '테스트 내용 4', 'user00'),
    ('테스트 제목 5', '테스트 내용 5', 'user00');
SELECT * FROM tbl_board;

DROP TABLE IF EXISTS tbl_board_attachment;

CREATE TABLE tbl_board_attachment(
    no INTEGER AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(256) NOT NULL,
    path VARCHAR(256) NOT NULL,
    content_type VARCHAR(56),
    size INTEGER,
    bno INTEGER NOT NULL,
    reg_date DATETIME DEFAULT now(),
    CONSTRAINT FOREIGN KEY(bno) REFERENCES tbl_board (no)
    );