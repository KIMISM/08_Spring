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