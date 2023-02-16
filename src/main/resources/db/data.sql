INSERT INTO user_tb(username, password, email, role, created_at) values('ADMIN', '4321', 'ADMIN@nate.com', 'ADMIN', now());
INSERT INTO user_tb(username, password, email, role, created_at) values('ssar', '1234', 'ssar@nate.com', 'USER', now());
INSERT INTO user_tb(username, password, email, role, created_at) values('love', '1234', 'love@nate.com', 'manager', now());
INSERT INTO user_tb(username, password, email, role, created_at) values('alice', '1234', 'alice@nate.com', 'USER', now());
INSERT INTO user_tb(username, password, email, role, created_at) values('aozp73', '1234', 'aozp73@naver.com', 'USER', now());
INSERT INTO user_tb(username, password, email, role, created_at) values('bishop', '1234', 'bishop@nate.com', 'USER', now());

INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('1번째 제목', '첫번째 내용', '/images/dora.png', 2, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('11번째 제목', '열한번째 내용', '/images/dora.png', 2, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('2번째 제목', '두번째 내용', '/images/dora.png', 2, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('22번째 제목', '스물두번째 내용', '/images/dora.png', 3, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('3번째 제목', '세번째 내용', '/images/dora.png', 3, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('4번째 제목', '네번째 내용', '/images/dora.png', 2, now());
INSERT INTO board_tb(title, content, thumbnail, user_id, created_at) values('5번째 제목', '다섯번째 내용', '/images/dora.png', 2, now());

INSERT INTO reply_tb(comment, user_id, board_id, created_at) values('댓글1', '2', '2', now());
INSERT INTO reply_tb(comment, user_id, board_id, created_at) values('댓글2', '2', '3', now());
INSERT INTO reply_tb(comment, user_id, board_id, created_at) values('댓글3', '3', '2', now());
INSERT INTO reply_tb(comment, user_id, board_id, created_at) values('댓글4', '3', '3', now());

commit;