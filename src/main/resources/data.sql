insert into user values(90001, sysdate(), 'User1', 'test1111', '880311-0000000');
insert into user values(90002, sysdate(), 'User2', 'test1111', '880311-1111111');
insert into user values(90003, sysdate(), 'User3', 'test1111', '880311-2222222');

insert into post values(100001, 'My first Post', 90001);
insert into post values(100002, 'My second Post', 90001);
