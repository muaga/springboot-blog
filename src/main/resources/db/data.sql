insert into user_tb(username, password, email) values('ssar', '1234', 'ssar@nate.com');
insert into user_tb(username, password, email) values('cos', '1234', 'cos@nate.com');
insert into board_tb(title, content, user_id, created_at) values('제목 1', '내용 1' , 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목 2', '내용 2' , 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목 3', '내용 3' , 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목 4', '내용 4' , 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목 5', '내용 5' , 2, now());
INSERT INTO REPLY_TB (comment, board_id, user_id) VALUES ('내용이 좋아요', 5, 1);
INSERT INTO REPLY_TB (comment, board_id, user_id) VALUES ('좋은 글 감사해요', 5, 2);
insert into user_tb(username, password, email) values('hong', '$2a$10$vFhXoCRQAwdXIsjdCge8n.CZv.CKmHGpAsdhPzMoDMhzr6TdOW7Le', 'hong@nate.com');





