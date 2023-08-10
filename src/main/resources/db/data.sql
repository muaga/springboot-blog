insert into user_tb(username, password, email) values('ssar', '1234', 'ssar@nate.com');
insert into user_tb(username, password, email) values('cos', '1234', 'cos@nate.com');
insert into board_tb(title, content, user_id, created_at) values('제목 1', '내용 1' , 1, now());
insert into board_tb(title, content, user_id, created_at) values('2', '내용 2' , 1, now());
insert into board_tb(title, content, user_id, created_at) values('22', '내용 3' , 1, now());
insert into board_tb(title, content, user_id, created_at) values('2222', '내용 4' , 2, now());
insert into board_tb(title, content, user_id, created_at) values('2 2 2 2', '내용 5' , 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목 6', '내용 6' , 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목 7', '내용 7' , 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목 8', '내용 7' , 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목 9', '내용 7' , 2, now());
INSERT INTO REPLY_TB (comment, board_id, user_id) VALUES ('잘했어요', 9, 1);
INSERT INTO REPLY_TB (comment, board_id, user_id) VALUES ('귯', 9, 1);
insert into user_tb(username, password, email) values('hong', '$2a$10$vFhXoCRQAwdXIsjdCge8n.CZv.CKmHGpAsdhPzMoDMhzr6TdOW7Le', 'hong@nate.com');


